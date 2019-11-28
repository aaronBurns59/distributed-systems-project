package ie.gmit.ds;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class Login
{
    // Params needed for user login
    @NotBlank
    private int id;
    @Length(min=3, max = 40)
    @NotBlank
    private String password;

    public Login(){}// Default Constructor

    @JsonProperty
    public int getLoginId()
    {
        return id;
    }// getLoginId
    @JsonProperty
    public String getLoginPassword()
    {
        return password;
    }// getLoginPassword
}// Login