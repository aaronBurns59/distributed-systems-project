package ie.gmit.ds;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class UserApiApplication extends Application<UserApiConfig>
{

	@Override
	public void run(UserApiConfig configuration, Environment environment) throws Exception
	{
		final UserApiResource resource = new UserApiResource();
		environment.jersey().register(resource);
		
		final UserHealthCheck healthCheck = new UserHealthCheck();	
		environment.healthChecks().register("example", healthCheck);
	}// run
	
	public static void main(String[] args) throws Exception
	{
		new UserApiApplication().run(args);
	}// main

}// UserApiApplication