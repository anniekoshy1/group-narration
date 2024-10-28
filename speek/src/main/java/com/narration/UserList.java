/**
 * Singleton class managing the list of users in the language learning system.
 * Provides methods to add, remove, and retrieve users by username or UUID.
 * The user list is initialized from data loaded by DataLoader.
 * @author Four Musketeers
 */
package com.narration;

import java.util.ArrayList;
import java.util.UUID;

public class UserList {

    private final ArrayList<User> users;  // List of users in the system
    public static UserList userList;

    private UserList() {
        this.users = DataLoader.getUsers();
    }

    public static UserList getInstance() {
        if (userList == null) {
            userList = new UserList();
        }
        return userList;
    }

    /**
     * Adds a user to the list.
     *
     * @param user The user to add.
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Removes a user from the list by their UUID.
     *
     * @param userId The UUID of the user to remove.
     */
    public void removeUser(UUID userId) {
        users.removeIf(user -> user.getId().equals(userId));
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username to search for.
     * @return The user if found, otherwise null.
     */
    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Finds a user by their UUID.
     *
     * @param userId The UUID to search for.
     * @return The user if found, otherwise null.
     */
    public User findUserById(UUID userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Retrieves the list of all users.
     *
     * @return An ArrayList of all users.
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Gets the total number of users in the system.
     *
     * @return The total number of users.
     */
    public int getTotalUsers() {
        return users.size();
    }

