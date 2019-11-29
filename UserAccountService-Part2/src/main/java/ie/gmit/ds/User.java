package ie.gmit.ds;

import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.google.protobuf.ByteString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class User
{

    // Parameters needed for 
    @NotBlank
    private int id;
    @NotBlank
    @Length(min=2, max = 32)
    private String name;
    @NotBlank
	@Pattern(regexp=".+@.+\\.[a-z]+")
    private String email;
    @Length(min=3, max = 40)
    @NotBlank
    private String password;
    private ByteString salt;
    private ByteString hashPassword;
	
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
    public User(int id, String name, String email,  ByteString salt, ByteString hashPassword)
	{
		this.id = id;
		this.name = name;
		this.email = email;
        this.salt = salt;
        this.hashPassword= hashPassword;
	}// User
	
	@JsonProperty
	public int getId()
	{
		return id;
	}// getUserId
	@JsonProperty
	public String getName()
	{
		return name;
	}// getUserName
	@JsonProperty
	public String getEmail()
	{
		return email;
	}// getUserEmail
	@JsonProperty
	public String getPassword()
	{
		return password;
    }// getUserPassword
    @JsonProperty
	public ByteString getSalt()
	{
		return salt;
	}// getUserEmail
	@JsonProperty
	public ByteString getHashPassword()
	{
		return hashPassword;
	}// getUserPassword
}// User
