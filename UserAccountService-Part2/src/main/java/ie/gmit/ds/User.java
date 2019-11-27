package ie.gmit.ds;

import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class User
{
    @NotBlank
    private int id;
    @NotBlank
    @Length(min=2, max = 32)
    private String name;
    @NotBlank
	@Pattern(regexp=".+@.+\\.[a-z]+")
    private String email;
    @Length(min=9, max = 40)
    //@Pattern(regexp = ".+[A-Z].+\\.[0-9]+")
    @NotBlank
    private String password;
    private byte salt;
    private byte hashPassword;
	
	public User(){}// User
    
    // This construtor is for creating an updating users
	public User(int id, String name, String email, String password)
	{
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
    }// User
    
    // This constructor is for listing all users and returning a single user
    public User(int id, String name, String email, String password, byte salt, byte hashPassword)
	{
		this.id = id;
		this.name = name;
		this.email = email;
        this.password = password;
        this.salt = salt;
        this.hashPassword= hashPassword;
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
    @JsonProperty
	public byte getSalt() 
	{
		return salt;
	}// getUserEmail
	@JsonProperty
	public byte getHashPassword()
	{
		return hashPassword;
	}// getUserPassword
}// User
