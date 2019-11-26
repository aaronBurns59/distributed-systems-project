package ie.gmit.ds;

import java.util.Collection;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserApiResource
{
	private HashMap<Integer, User> userMap = new HashMap<>();
	
	public UserApiResource()
	{
		User user1 = new User(1,"Aaron","aaronburns59@gmail.com","water");
		userMap.put(user1.getUserId(), user1);
		
		User user2 = new User(2,"Michael","michaelburns74@gmail.com","chesse");
		userMap.put(user2.getUserId(), user2);
	}// UserApiResource
	
	@GET
	public Collection<User> getArtists()
	{
		return userMap.values();
	}// getArtists
	
	@GET
	@Path("/{userId}")
	public User getUserById(@PathParam("artistId") int id)
	{
		if(userMap.containsKey(id));
			return userMap.get(id);
	}// getUserById
}// UserAPIResource
