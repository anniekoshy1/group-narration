import java.util.ArrayList;
import java.util.UUID;

public class UserList {

    private static UserList instance; 
    private ArrayList<User> users;  // List of users in the system

    private UserList() {
        this.users = new ArrayList<>();
    }

    public static UserList getInstance() {
        if (instance == null) {
            instance = new UserList();
        }
        return instance;
    }

    // Add a user to the list
    public void addUser(User user) {
        users.add(user);
    }

    // Remove a user from the list by their UUID
    public void removeUser(UUID userId) {
        users.removeIf(user -> user.getId().equals(userId));
    }

    // Find a user by their username 
    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    // Find a user by their UUID
    public User findUserById(UUID userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    // Get the list of all users
    public ArrayList<User> getUsers() {
        return users;
    }

    // Get the total number of users in the system
    public int getTotalUsers() {
        return users.size();
    }
}