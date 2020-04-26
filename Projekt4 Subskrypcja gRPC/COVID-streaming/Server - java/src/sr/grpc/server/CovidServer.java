package sr.grpc.server;

import java.io.IOException;
import java.util.logging.Logger;

import io.grpc.Server;
import io.grpc.ServerBuilder;


public class CovidServer
{
	private static final Logger logger = Logger.getLogger(CovidServer.class.getName());

	private int port = 11011;
	private Server server;

	private void start() throws IOException 
	{
		server = ServerBuilder.forPort(port)
				.addService(new StreamNewInfo())
//				.addService(new PingImpl())

				.build()
				.start();
		logger.info("Server started, listening on " + port);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				CovidServer.this.stop();
				System.err.println("*** server shut down");
			}
		});
	}

	private void stop() {
		if (server != null) {
			server.shutdown();
		}
	}

	/**
	 * Await termination on the main thread since the grpc library uses daemon threads.
	 */
	private void blockUntilShutdown() throws InterruptedException {
		if (server != null) {
			server.awaitTermination();
		}
	}

	/**
	 * Main launches the server from the command line.
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		final CovidServer server = new CovidServer();
		server.start();
		server.blockUntilShutdown();
	}

}
