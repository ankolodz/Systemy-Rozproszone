package sr.grpc.server;

import sr.grpc.gen.Ping_msg;
import sr.grpc.gen.StreamTesterGrpc;

public class PingImpl extends StreamTesterGrpc.StreamTesterImplBase {

    @Override
    public void ping(Ping_msg msg,
                    io.grpc.stub.StreamObserver<sr.grpc.gen.Ping_msg> responseObserver){
        System.out.println("Ping -> pong");
        responseObserver.onNext(msg);
        responseObserver.onCompleted();
    }
}
