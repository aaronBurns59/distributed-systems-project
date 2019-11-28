package ie.gmit.ds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDatabase
{
    public static HashMap<Integer, User> users = new HashMap<>();
    static
    {
        users.put(1, new User(1, "Aaron", "aaronburns59@gmail.com", "water"));
        users.put(2, new User(2, "Martin", "martin@gmail.com", "Peace"));
        users.put(3, new User(3, "Fred", "freddy@gmail.com ", "LOL"));
    }// static

    public static List<User> getUsers()
    {
        return new ArrayList<User>(users.values());
    }// getUsers
    public static User getUserById(Integer id)
    {
        return users.get(id);
    }// getUserById
    public static void updateUser(Integer id, User user)
    {
        users.put(id, user);
    }// updateUser
    public static void deleteUser(Integer id)
    {
        users.remove(id);
    }// deleteUser
}// UserDatabase