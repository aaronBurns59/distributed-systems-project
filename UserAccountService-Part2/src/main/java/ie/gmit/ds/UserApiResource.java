package ie.gmit.ds;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserApiResource
{
    private final Validator val;
    // This is the client that will talk to the server in part1
    private UserClient client;
    private static final String LOCAL_HOST = "localhost";
    private static final int PORT_NUMBER = 50551;

	public UserApiResource(Validator val)
	{
        this.val = val;
        this.client = new UserClient(LOCAL_HOST, PORT_NUMBER);
	}// UserApiResource

     // Adpated from https://howtodoinjava.com/dropwizard/tutorial-and-hello-world-example/
	@GET
	public Response getUsers()
    {
        return Response.ok(UserDatabase.getUsers()).build();
	}// getArtists
	
	@GET
	@Path("/{id}")
	public Response getUserById(@PathParam("id") int id)
	{
        User user = UserDatabase.getUserById(id);
        if(user != null)
            return Response.ok(user).build();
        else
            return Response.status(Status.NOT_FOUND).build();
    }// getUserById
    
    @POST
    public Response createUser (User user) throws URISyntaxException 
    {
        System.out.println("In the post method");
        // Validation to make sure that the what the user enters is correct
        Set<ConstraintViolation<User>> violations = val.validate(user);
        User u = UserDatabase.getUserById(user.getId());
        if (violations.size() > 0)
        {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<User> violation : violations) 
            {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }// for
            // If a violation of the constraints placed on the user class properties are found return here
            // Generate the response
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }// if
        // If  the user's id is not found add it to the userDatabase(HashMap) 
        if(u != null)
        {
            // use instance of UserClient otherwise issue with it not being static
            client.hash(user);
            UserDatabase.addUser(user);
            // Generate the response
            return Response.created(new URI("/users/" + user.getId())).build();
        }// if
        else
        {
            return Response.status(Status.NOT_FOUND).build();
        }// else
    }// createUser

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") int id, User user)
    {
        // Validation to make sure that the what the user enters is correct
        Set<ConstraintViolation<User>> violations = val.validate(user);
        User u = UserDatabase.getUserById(user.getId());
        if (violations.size() > 0) 
        {
            ArrayList<String> validationMessages = new ArrayList<>();
            for (ConstraintViolation<User> violation : violations)
            {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }// for
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }// if
        if (u != null) 
        {
            // update the user in db before hashing it
            UserDatabase.addUser(user);
            client.hash(user);
            return Response.status(Status.OK).build();
        }// if
        else
        {
            return Response.status(Status.NOT_FOUND).build();
         }// else
    }// updateUser

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id)
     {
        User user = UserDatabase.getUserById(id);
        if (user != null) 
        {
            // don't need any methods from the client for deleting
            UserDatabase.deleteUser(id);
            return Response.ok().build();
        }// if
        else
            return Response.status(Status.NOT_FOUND).build();
    }// deleteUser

    // Same as previous post request except with different params
    @POST
    @Path("/login")
    public Response loginUser(Login login)
    {
        // Validation to make sure that the what the user enters is correct
        Set<ConstraintViolation<Login>> violations = val.validate(login);
        // get the user from the db
        User user = UserDatabase.getUserById(login.getLoginId());
        if (violations.size() > 0)
        {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<Login> violation : violations)
            {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }// for
            // If a violation of the constraints placed on the user class properties are found return here
            // Generate the response
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }// if
        if(user != null)
        {
            // use the validate method from the client/gRPC to check its valid
            if(client.validate(login.getLoginPassword(),user.getHashPassword(), user.getSalt()))
                return Response.status(Status.OK).build();
            else
                return Response.status(Response.Status.NOT_FOUND).build();
        }// if
        else
            return Response.status(Status.NOT_FOUND).build();
    }// loginUser
}// UserAPIResource
