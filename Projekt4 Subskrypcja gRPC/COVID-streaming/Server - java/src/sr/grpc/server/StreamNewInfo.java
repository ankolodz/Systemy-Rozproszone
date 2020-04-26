package sr.grpc.server;

import io.grpc.stub.StreamObserver;
import sr.grpc.gen.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static java.lang.Math.abs;

public class StreamNewInfo extends  StreamTesterGrpc.StreamTesterImplBase{

    private ArrayList<Set<io.grpc.stub.StreamObserver<sr.grpc.gen.Report>>> observers = new ArrayList<>();

    StreamNewInfo(){
        for(int i=0;i<=17;i++)
            observers.add(new HashSet<>());
        //Simply value generator
        Thread thread = new Thread(()->{
            Random generator = new Random();
            while(true){
                int district = abs(generator.nextInt()%18);
                int dead = abs(generator.nextInt()+1)%1000;
                int ill = abs(generator.nextInt()+1)%1000;
                int quarantine = abs(generator.nextInt()+1)%1000;
                if(district != 17)
                    update(District.forNumber(district),dead,ill,quarantine);
                update(District.POLAND,dead,ill,quarantine);
                try { Thread.sleep(500); } catch (InterruptedException e) { }
            }
        });
        thread.start();
    }

    //new message
    public void update(District district, int dead,int ill, int quarantine){
        for (StreamObserver<Report> i: observers.get(district.getNumber())) {
            try {
                Report report = Report.newBuilder()
                        .setDied(dead)
                        .setIll(ill)
                        .setQuarantine(quarantine)
                        .setDistrict(district)
                        .build();
                i.onNext(report);
            }
            catch(Exception e){
                try {
                    System.out.println("Message send error");
                    i.onCompleted();
                }catch(Exception f){}
                finally {
                    System.out.println("Remove folowers");
                    observers.get(district.getNumber()).remove(i);
                }
            }
        }
    }

    //add stream
    @Override
    public void getNewInfo(Task request,StreamObserver<Report> responseObserver) {
        System.out.println("New streamer");
        this.observers.get(request.getDistrictValue()).add(responseObserver);
    }
    @Override
    public void ping(Ping_msg msg,
                     io.grpc.stub.StreamObserver<sr.grpc.gen.Ping_msg> responseObserver){
        System.out.println("Ping -> pong");
        responseObserver.onNext(msg);
        responseObserver.onCompleted();
    }




}
