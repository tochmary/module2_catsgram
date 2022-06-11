package ru.yandex.practicum.catsgram.model;

import lombok.Data;

@Data
public class User {
    private String id;
    private String username;
    private String nickname;
}