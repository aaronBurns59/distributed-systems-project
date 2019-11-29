package ie.gmit.ds;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class UserClient
{
    private final ManagedChannel channel;
    // using grpc methods for synchronized communication between the client and server
    private final PasswordServiceGrpc.PasswordServiceBlockingStub syncStub;
    // using grpc methods for asynchronized communication between the client and server
    private final PasswordServiceGrpc.PasswordServiceStub  asyncStub;
    // Constant for client listening time limit
    private final int TIME_OUT_LIMIT = 5;
    // used for displaying that the client is listening
    private static final Logger logger = Logger.getLogger(UserClient.class.getName());

    // creates the communication capability for the client and server
    public UserClient(String host, int port)
    {
        channel = ManagedChannelBuilder
                .forAddress(host, port)// listens for the server
                .usePlaintext()
                .build();
        syncStub = PasswordServiceGrpc.newBlockingStub(channel);
        asyncStub = PasswordServiceGrpc.newStub(channel);
    }// UserClient

    // Cancels the client after the allocated listening time is up
    public void shutdown() throws InterruptedException
    {
        channel.shutdown().awaitTermination(TIME_OUT_LIMIT, TimeUnit.SECONDS);
    }// shutdown

    public void hash(final User user)
    {
        StreamObserver<UserLoginResponse> res = new StreamObserver<UserLoginResponse>() {
            @Override
            public void onNext(UserLoginResponse userLogRes)
            {
                User u = new User(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        userLogRes.getHashedPassword(),
                        userLogRes.getSalt()
                );
                UserDatabase.addUser(user);
            }// onNext
            @Override
            public void onError(Throwable t) {}
            @Override
            public void onCompleted() {}
        };
        try
        {
            // use asynchronous stub with the PasswordServiceGRPC to generate the hashed password and salt for the user
            asyncStub.hash(UserLoginRequest.newBuilder()
                    .setUserId(user.getId())
                    .setPassword(user.getPassword())
                    .build(), res);
        }// try
        catch(StatusRuntimeException e)
        {
            logger.warning("Exception: " + e);
        }// catch
    }// hash
    // Use ByteString now byte
    public boolean validate(String password, ByteString hashedPassword, ByteString salt)
    {
        ConfirmPasswordRequest req = ConfirmPasswordRequest.newBuilder()
                .setPassword(password)
                .setHashedPassword(hashedPassword)
                .setSalt(salt).build();
        try
        {
            ConfirmPasswordResponse res = syncStub.validate(req);
            return res.getValid();
        }
        catch(StatusRuntimeException e)
        {
            logger.warning("Exception: " + e);
            return false;
        }
    }// validate
}// UserClient
