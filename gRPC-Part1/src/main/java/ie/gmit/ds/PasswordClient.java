package ie.gmit.ds;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

public class PasswordClient 
{
	private final ManagedChannel channel;
	// using grpc methods for synchronized communication between the client and server
	private final PasswordServiceGrpc.PasswordServiceBlockingStub stub;
	// Constant for client listening time limit
	private final int TIME_OUT_LIMIT = 5;
	// used for displaying that the client is listening
	private static final Logger logger = Logger.getLogger(PasswordClient.class.getName());
	
	private static final String LOCAL_HOST = "localhost";
	private static final int PORT_NUMBER = 50551;

	Scanner console = new Scanner(System.in);

	// creates the communication capability for the client and server
	public PasswordClient(String host, int port)
	{
        channel = ManagedChannelBuilder
                .forAddress(host, port)// listens for the server 
                .usePlaintext()
                .build();
        stub = PasswordServiceGrpc.newBlockingStub(channel);
    }// PasswordClient
	
	// Cancels the client after the allocated listening time is up
    public void shutdown() throws InterruptedException
    {
        channel.shutdown().awaitTermination(TIME_OUT_LIMIT, TimeUnit.SECONDS);
    }// shutdown
    
    public void hashPassword(int id, String pass)
    {
    	// Log the given id and password so I can verify them later
    	logger.info("User ID: " + id + " Password: " + pass);
    	
    	// Creating a RPC message for hashing to the auto generated gRPC method hash()
    	UserLoginRequest hashReq = UserLoginRequest.newBuilder()
    			.setUserId(id)
    			.setPassword(pass)
    			.build();
    	// Instantiating a response message parameter for the gRPC method hash()
    	// Have to instantiate this here because its needed in both the try and catch 
    	// depending on circumstances 
    	UserLoginResponse hashRes = null;
    	
    	try
    	{
    		// using the stub from the PasswordServiceGrpc.PasswordServiceBlockingStub
    		// for synchronised communication between the server and client
    	 	 hashRes = stub.hash(hashReq);
    	}
    	catch (StatusRuntimeException ex)
    	{
    		// returns if there is a problem at runtimes
    		logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
			return;
    	}
    	// logs the successful UserLoginResponse
		logger.info(String.format("Hash Response from the server: \n%s ", hashRes));
    	
    //generate the response   	
    }// generateUserLoginRequest
    


    public static void main(String[] args) throws InterruptedException
    {
    	PasswordClient client = new PasswordClient(LOCAL_HOST, PORT_NUMBER);
    	// this is the layout for creating requests in gRPC use this model for all grpc msgs
    	// put this is a separate method when finished
    	// Generating a request for the user login 	
    	try
    	{
        	client.hashPassword(1, "Yurt");
    	}
    	finally
    	{
    		Thread.currentThread().join();
    	}
    	
    	// Send the request using the stub stub.hash/.validate
    }// main
}// PasswordClient
