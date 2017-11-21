package com.example.emyeraky.chatmessage;

/**
 * Created by Emy Eraky on 11/7/2017.
 */

public class FriendlyMessage {
    private String text;
    private String name;
    private String photoUrl;
    private String profilphotoUrl;

    public FriendlyMessage() {
    }

    public FriendlyMessage(String text, String name, String photoUrl,String profilphotoUrl) {
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
        this.profilphotoUrl = profilphotoUrl;

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getProfilphotoUrl() {
        return profilphotoUrl;
    }

    public void setProfilphotoUrl(String profilphotoUrl) {
        this.profilphotoUrl = profilphotoUrl;
    }
}

