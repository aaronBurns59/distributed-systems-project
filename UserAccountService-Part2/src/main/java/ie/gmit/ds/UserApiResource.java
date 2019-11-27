package ie.gmit.ds;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserApiResource
{
	public UserApiResource()
	{
	}// UserApiResource
	
	@GET
	public Collection<User> getArtists()
	{
        return null;
	}// getArtists
	
	@GET
	@Path("/{userId}")
	public User getUserById(@PathParam("artistId") int id)
	{
        return null;
	}// getUserById
}// UserAPIResource
