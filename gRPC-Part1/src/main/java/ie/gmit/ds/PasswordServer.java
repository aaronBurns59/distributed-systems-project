package ie.gmit.ds;

import java.io.IOException;
import java.util.logging.Logger;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class PasswordServer
{
	// Constant for assigning the server to the port number below
	private static final int PORT_NUMBER = 9999;// 50551
	// used for running the server and communicating to client
	private Server passwordServer;
	// Logger is used to print the port number to the console 
	// which lets the user know the server is running
	private static final Logger logger = Logger.getLogger(PasswordServer.class.getName());
	
	// creates the server and a PasswordServiceImpl to access its methods for the client
	private void start() throws IOException
	{
		passwordServer = ServerBuilder.forPort(PORT_NUMBER)
				.addService(new PasswordServiceImpl())
				.build()
				.start();// this is the server classes start method not the local one
		
		logger.info(String.format("Server is running \nListening at port: %d", PORT_NUMBER));
	}// start
	
	// allows the password to be shutdown and not just run for ever
    private void blockUntilShutdown() throws InterruptedException 
    {
        if (passwordServer != null) 
        	passwordServer.awaitTermination();
    }// blockUntilShutdown
    
    // stops the server
    private void stop()
    {
        if (passwordServer != null)
        	passwordServer.shutdown();
    }// stop
    
    // main method for starting and maintaining until shudown the server
    public static void main(String[] args) throws IOException, InterruptedException 
    {
    	// creating an 
        final PasswordServer server = new PasswordServer();
        server.start();
        server.blockUntilShutdown();    
    }// main
}// PasswordServer