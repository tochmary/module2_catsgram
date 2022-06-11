package ru.yandex.practicum.catsgram.model;

import lombok.Data;

@Data
public class Follow {
    private String author;
    private String follower;

    public Follow(String author, String follower) {
        this.author = author;
        this.follower = follower;
    }
}
