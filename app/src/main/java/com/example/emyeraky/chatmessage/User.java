package com.example.emyeraky.chatmessage;


public class User {
    String name, email,photo;

    public User() {
    }
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public User(String name, String email,String photo) {
        this.name = name;
        this.email = email;
        this.photo=photo;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
