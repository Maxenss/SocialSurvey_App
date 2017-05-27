package com.example.social.classes;

import org.json.JSONObject;

/**
 * Created by Maxim on 22.05.2017.
 */

public class User {
    private int userId;
    private String login;
    private String firstName;
    private String lastName;
    private String middleName;
    private String role;
    private String password = " ";
    private boolean isDeleted;

    public User(int userId, String login, String firstName,
                String lastName, String middleName, String role, boolean isDeleted) {
        this.userId = userId;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.role = role;
        this.isDeleted = isDeleted;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJSONForSetDataOnServer() {
        JSONObject data = new JSONObject();

        try {
            data.put("userId", userId);
            //data.put("login", login);
            data.put("firstName", firstName);
            data.put("lastName", lastName);
            data.put("middleName", middleName);
            data.put("role", role);
            // Чекнуть,если что
            data.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data.toString();
    }

}
