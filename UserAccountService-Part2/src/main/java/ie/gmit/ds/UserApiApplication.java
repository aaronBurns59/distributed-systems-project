package ie.gmit.ds;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserApiApplication extends Application<Configuration>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserApiApplication.class);

    @Override
    public void initialize(Bootstrap<Configuration> b){}
 
	@Override
	public void run(Configuration configuration, Environment environment) throws Exception
	{
        LOGGER.info("Registering REST resources");
        environment.jersey().register(new UserApiResource(environment.getValidator()));

        final UserHealthCheck healthCheck = new UserHealthCheck();
        environment.healthChecks().register("example", healthCheck);
	}// run
    
    // This main method is run via the jar that is referenced in the pom.xml file
	public static void main(String[] args) throws Exception
	{
		new UserApiApplication().run(args);
	}// main
}// UserApiApplication