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

	public UserApiResource(Validator val)
	{
        this.val = val;
	}// UserApiResource
     
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
            // If a violation of the contraints placed on the user class properties are found return here
            // Generate the response
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }// if

        // If  the user's id is not found add it to the userDatabase(HashMap) 
        if(u != null)
        {
            // userCLient
            UserDatabase.updateUser(user.getId(), user);
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
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<User> violation : violations)
            {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }// for
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }// if

        if (u != null) 
        {
            // overwrite the original data with the given id
            // USERCLIENT
            UserDatabase.updateUser(id, user);
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
            UserDatabase.deleteUser(id);
            return Response.ok().build();
        }// if
        else
            return Response.status(Status.NOT_FOUND).build();
    }// deleteUser
}// UserAPIResource
