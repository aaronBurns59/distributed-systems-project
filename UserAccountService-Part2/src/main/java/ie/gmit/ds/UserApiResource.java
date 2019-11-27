package ie.gmit.ds;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ch.qos.logback.core.joran.conditional.ElseAction;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserApiResource
{
    private final Validator val;

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
        // Validation to make sure that the what the user enters is correct
        Set<ConstraintViolation<User>> violations = val.validate(user);
        User u = UserDatabase.getUserById(user.getUserId());
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
            UserDatabase.updateUser(user.getUserId(), user);
            // Generate the response
            return Response.created(new URI("/users/" + user.getUserId())).build();
        }// if
        else
        {
            return Response.status(Status.NOT_FOUND).build();
        }// else
    }// createUser
}// UserAPIResource
