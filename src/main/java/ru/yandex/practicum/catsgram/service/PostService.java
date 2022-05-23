package ru.yandex.practicum.catsgram.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.PostNotFoundException;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostService {
    private final UserService userService;
    private final List<Post> posts = new ArrayList<>();
    private static Integer globalId = 0;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll() {
        log.debug("Текущее количество постов: {}", posts.size());
        return posts;
    }

    public List<Post> findAll(Integer size, Integer from, String sort) {
        log.debug("Параметры поиска постов: size = {},  from = {}, sort = {}", size, from, sort);
        return posts.stream().sorted((p0, p1) -> {
            int comp = p0.getCreationDate().compareTo(p1.getCreationDate()); //прямой порядок сортировки
            if (sort.equals("desc")) {
                comp = -1 * comp; //обратный порядок сортировки
            }
            return comp;
        }).skip(from).limit(size).collect(Collectors.toList());
    }

    public Post create(Post post) {
        User postAuthor = userService.findUserByEmail(post.getAuthor());
        if (postAuthor == null) {
            throw new UserNotFoundException("Пользователь " + post.getAuthor() + " не найден");
        }
        post.setId(getNextId());
        posts.add(post);
        log.debug("Добавлен пост: {}", post);
        return post;
    }

    public Post findPostById(Integer postId) {
        for (Post post : posts) {
            if (post.getId() == postId) {
                log.debug("Пост с id = {}: {}", postId, post);
                return post;
            }
        }
        log.debug("Пост с id = " + postId + " не найден");
        throw new PostNotFoundException("Пост с id = " + postId + " не найден");
    }

    private static Integer getNextId() {
        return globalId++;
    }
}