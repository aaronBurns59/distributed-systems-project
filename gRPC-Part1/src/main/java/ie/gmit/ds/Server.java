package ie.gmit.ds;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.logging.Logger;


public class Server 
{
	private Server server;
	private static final Logger logger = Logger.getLogger(Server.class.getName());
	
	public static void main(String [] args) throws IOException, InterruptedException{
		final Server server = new Server();
		server.start();
		server.blockUntilShutdown();
		
	}
	
	private void start() throws IOException
	{
		
	}
	
	private void stop()
	{
	}
	
	private void blockUntilShutdown() throws InterruptedException
	{
		
	}

}
