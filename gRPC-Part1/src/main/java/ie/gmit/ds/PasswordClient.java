package ie.gmit.ds;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class PasswordClient 
{
	// used for contacting the server???
	private final ManagedChannel channel;
	// using grpc methods for synchronized communication between the client and server
	private final PasswordServiceGrpc.PasswordServiceBlockingStub passwordService;
	// Constant for client listening time limit
	private final int TIME_OUT_LIMIT = 5;
	// used for displaying that the client is listening
	private static final Logger logger = Logger.getLogger(PasswordClient.class.getName());
	
	
	// creates the communication capability for the client and server
	public PasswordClient(String host, int port)
	{
        channel = ManagedChannelBuilder
                .forAddress(host, port)// listens for the server 
                .usePlaintext()
                .build();
        passwordService = PasswordServiceGrpc.newBlockingStub(channel);
    }// PasswordClient
	
	// Cancels the client after the allocated listening time is up
    public void shutdown() throws InterruptedException
    {
        channel.shutdown().awaitTermination(TIME_OUT_LIMIT, TimeUnit.SECONDS);
    }// shutdown
    
    public void saySomething()
    {
    	System.out.println("Say something from the client");
    }
    
    public static void main(String[] args)
    {
    	PasswordClient client = new PasswordClient("Me", 50551);
    	client.saySomething();
    }// main
}// PasswordClient
