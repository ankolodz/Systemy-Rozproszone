package sr.grpc.server;

import io.grpc.stub.StreamObserver;
import sr.grpc.gen.District;
import sr.grpc.gen.Report;
import sr.grpc.gen.State;
import sr.grpc.gen.StreamTesterGrpc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static java.lang.Math.abs;

public class StreamDeadImpl extends  StreamTesterGrpc.StreamTesterImplBase{

    private ArrayList<Set<io.grpc.stub.StreamObserver<sr.grpc.gen.Report>>> observers = new ArrayList<>();

    StreamDeadImpl (){
        for(int i=0;i<=17;i++)
            observers.add(new HashSet<>());
        //Simply value generator
        Thread thread = new Thread(()->{
            Random generator = new Random();
            while(true){
                int district = abs(generator.nextInt()%17);
                int val = abs(generator.nextInt()+1)%1000;
                if(district != 0)
                    update(District.forNumber(district),val);
                update(District.All,val);
                try { Thread.sleep(1000); } catch (InterruptedException e) { }
            }
        });
        thread.start();
    }

    //new message
    public void update(District district, int val){
        for (StreamObserver<Report> i: observers.get(district.getNumber())) {
            Report report = Report.newBuilder().setValue(val)
                    .setDistrict(district)
                    .setState(State.New_dead)
                    .build();
            i.onNext(report);
        }
    }

    //add stream
    @Override
    public void getNewDead(sr.grpc.gen.Task request,
                           io.grpc.stub.StreamObserver<sr.grpc.gen.Report> responseObserver) {
        this.observers.get(request.getDistrictValue()).add(responseObserver);
    }




}
