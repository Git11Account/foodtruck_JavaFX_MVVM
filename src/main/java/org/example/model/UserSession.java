package org.example.model;

public class UserSession {

    private static int userId;
    private static String username;
    private static String role;
    private static boolean active = false;

    public static void startSession(int id, String user, String r) {
        userId = id;
        username = user;
        role = r;
        active = true;

        System.out.println("=== SESSION STARTED ===");
        System.out.println("ID : " + id);
        System.out.println("Username : " + user);
        System.out.println("Role : " + r);
    }

    public static int getUserId() { return userId; }
    public static String getUsername() { return username; }
    public static String getRole() { return role; }

    public static boolean isLogged() { return active; }
    public static boolean isAdmin() { return active && "ADMIN".equals(role); }
    public static boolean isOwner() { return active && "OWNER".equals(role); }
    public static boolean isUser() { return active && "USER".equals(role); }

    public static void logout() {
        active = false;
        role = null;
        username = null;
        userId = 0;
        System.out.println("=== SESSION CLOSED ===");
    }
}
