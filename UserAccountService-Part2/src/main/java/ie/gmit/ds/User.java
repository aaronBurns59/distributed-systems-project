package ie.gmit.ds;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User
{
	private int id;
	private String name;
	@Pattern(regexp = ".+@.+\\.[a-z].+\\.com")
	private String email;
	private String password;
	
	public User(){}// User
	
	public User(int id, String name, String email, String password)
	{
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}// User
	
	@JsonProperty
	public int getUserId()
	{
		return id;
	}// getUserId
	@JsonProperty
	public String getUserName() 
	{
		return name;
	}// getUserName
	@JsonProperty
	public String getUserEmail() 
	{
		return email;
	}// getUserEmail
	@JsonProperty
	public String getUserPassword()
	{
		return password;
	}// getUserPassword
}// User
