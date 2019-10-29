package ie.gmit.ds;

import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;

public class PasswordServiceImpl extends PasswordServiceGrpc.PasswordServiceImplBase
{
	@Override
	public void hash(UserLoginRequest req, StreamObserver<UserLoginResponse> resObserver)
	{
		// Creating the variables that will be passed with the gRPC request
		// Generating a random password
		char[] password = req.getPassword().toCharArray();	
		// using method from 'Passwords' class to get a salt for the hash method
		byte[] salt = Passwords.getNextSalt();
		// Creating a hashed version of the password using the salt for security
		byte[] hashedPassword = Passwords.hash(password, salt);
		
		// Generate the response
		UserLoginResponse res =UserLoginResponse.newBuilder() 
				.setUserId(req.getUserId())
				.setHashedPassword(ByteString.copyFrom(hashedPassword))
                .setSalt(ByteString.copyFrom(salt))
                .build();
		// send the res to the client
		resObserver.onNext(res);
		// signal the request is  finished
		resObserver.onCompleted();
	}// hash
	
	@Override
	public void validate(ConfirmPasswordRequest req, StreamObserver<BoolValue> resObserver)
	{
		try
		{
		// Variables are the same as the above methods (Not Physically)
		char[] password = req.getPassword().toCharArray();
		byte[] salt = Passwords.getNextSalt();
		byte[] hashedPassword = Passwords.hash(password, salt);

		if (Passwords.isExpectedPassword(password, salt, hashedPassword))
			resObserver.onNext(BoolValue.newBuilder().setValue(true).build());
		else
    	  	resObserver.onNext(BoolValue.newBuilder().setValue(false).build()); 
		}// try
		catch (RuntimeException ex)
		{
			resObserver.onNext(BoolValue.newBuilder().setValue(false).build());
		}// catch
		// signal the request is  finished
		resObserver.onCompleted();
	}// validate
}// PasswordService
