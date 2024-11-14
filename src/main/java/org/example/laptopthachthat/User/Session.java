package org.example.laptopthachthat.User;

public class Session {
    private static int loggedInUserId;

    public static void setLoggedInUserId(int userId) {
        loggedInUserId = userId;
    }

    public static int getLoggedInUserId() {
        return loggedInUserId;
    }
}
