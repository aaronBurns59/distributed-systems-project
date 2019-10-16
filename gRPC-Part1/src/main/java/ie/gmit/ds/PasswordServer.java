package ie.gmit.ds;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.logging.Logger;


public class PasswordServer 
{
	private Server server;
	private static final Logger logger = Logger.getLogger(Server.class.getName());
	
	public static void main(String [] args) throws IOException, InterruptedException{
		final PasswordServer server = new PasswordServer();
		server.start();
		server.blockUntilShutdown();
		
	}
	
	private void start() throws IOException
	{
		int portNo = 8081;
		server = ServerBuilder.forPort(portNo)
				.build()
				.start();
		logger.info("Server started, Listening at port: " + portNo);
	}
	
	private void stop()
	{
		if(server != null)
			server.shutdown();
	}
	
	private void blockUntilShutdown() throws InterruptedException
	{
		if(server != null)
			server.awaitTermination();
	}

}
