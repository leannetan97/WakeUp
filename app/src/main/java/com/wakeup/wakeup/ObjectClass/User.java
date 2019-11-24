package com.wakeup.wakeup.ObjectClass;

public class User {

    private String email;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    // Get
    public String getEmail() {
        return email;
    }

    public String getEmailHashed() {
        return String.valueOf(email.hashCode());
    }

    // Set
    public void setEmail(String email) {
        this.email = email;
    }
// check email exists -->signup, checkpw
}
