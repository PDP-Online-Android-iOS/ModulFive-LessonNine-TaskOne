package dev.ogabek.java.model;

public class Post {

    private final int photo;
    private final int profile;
    private final String fullName;

    public Post(int profile, String fullName, int photo) {
        this.photo = photo;
        this.profile = profile;
        this.fullName = fullName;
    }

    public int getPhoto() {
        return photo;
    }

    public int getProfile() {
        return profile;
    }

    public String getFullName() {
        return fullName;
    }
}
