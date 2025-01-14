package org.example.laptopthachthat;

public class LoggedInUser {
    private int userID;
    private String username;

    private static LoggedInUser instance;

    private LoggedInUser(int userID, String username) {
        this.userID = userID;
        this.username = username;
    }

    public static LoggedInUser getInstance() {
        return instance;
    }

    public static void login(int userID, String username) {
        if (instance == null) {
            instance = new LoggedInUser(userID, username);
        }
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public static void logout() {
        System.out.println(instance.getUsername() + " đăng xuất");
        instance = null;
    }

    public static String showLoggedInUser() {
        return "userID=" + instance.userID + ", username='" + instance.username + "'";
    }
}