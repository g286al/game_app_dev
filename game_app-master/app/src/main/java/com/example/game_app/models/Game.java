package com.example.game_app.models;

public class Game {
    private String name;
    private String releaseDate;
    private String imageUrl;

    public Game(String name, String releaseDate, String imageUrl) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
