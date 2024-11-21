/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.possystem;

/**
 *
 * @author Lenovo
 */
public class UserSession {
    private static UserSession instance;
    private String username;
    private String role;

    // Private constructor to enforce Singleton pattern
    private UserSession() {}

    // Get the instance of the session
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Clear the session (e.g., on logout)
    public void clearSession() {
        instance = null;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
