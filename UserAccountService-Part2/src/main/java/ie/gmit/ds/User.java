package ie.gmit.ds;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User
{
	private int userId;
	private String userName;
	@Pattern(regexp = ".+@.+\\.[a-z].+\\.com")
	private String userEmail;
	private String userPassword;
	
	public User(){}// User
	
	public User(int id, String name, String email, String password)
	{
		userId = id;
		userName = name;
		userEmail = email;
		userPassword = password;
	}// User
	
	@JsonProperty
	public int getUserId()
	{
		return userId;
	}// getUserId
	@JsonProperty
	public String getUserName() 
	{
		return userName;
	}// getUserName
	@JsonProperty
	public String getUserEmail() 
	{
		return userEmail;
	}// getUserEmail
	@JsonProperty
	public String getUserPassword()
	{
		return userPassword;
	}// getUserPassword
}// User
