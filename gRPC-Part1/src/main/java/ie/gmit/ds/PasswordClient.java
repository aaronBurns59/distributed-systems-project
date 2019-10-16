package ie.gmit.ds;

import java.util.logging.Logger;

import io.grpc.ManagedChannel;

import io.grpc.ManagedChannelBuilder;

public class PasswordClient
{
	private static final Logger logger = Logger.getLogger(PasswordClient.class.getName());
	private final ManagedChannel channel;
	private final PasswordServiceGrpc.PasswordServiceBlockingStub passwordClientStub;

	public PasswordClient(String host, int portNo)
	{
		this.channel = ManagedChannelBuilder.forAddress(host, portNo)
			.usePlaintext()
			.build();
		this.passwordClientStub = PasswordServiceGrpc.newBlockingStub(channel);
	}// PasswordClient
	
}
