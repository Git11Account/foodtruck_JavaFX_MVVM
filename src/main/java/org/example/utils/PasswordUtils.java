package org.example.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // Génère un hash
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Vérifie si le mdp correspond
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
