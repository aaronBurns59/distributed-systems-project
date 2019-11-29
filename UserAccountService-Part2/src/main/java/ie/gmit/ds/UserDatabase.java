package ie.gmit.ds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDatabase
{
    public static HashMap<Integer, User> users = new HashMap<>();
    static
    {
        users.put(1, new User(1, "Aaron", "aaronburns59@gmail.com", "123"));
        users.put(2, new User(2, "Martin", "martin@gmail.com", "321"));
        users.put(3, new User(3, "Fred", "freddy@gmail.com ", "1010"));
    }// static

    public static List<User> getUsers()
    {
        return new ArrayList<User>(users.values());
    }// getUsers
    public static User getUserById(int id)
    {
        return users.get(id);
    }// getUserById
    public static void addUser(User user) {users.put(user.getId(), user); }// addUser
    public static void updateUser(int id, User user)
    {
        users.put(id, user);
    }// updateUser
    public static void deleteUser(int id)
    {
        users.remove(id);
    }// deleteUser
}// UserDatabase