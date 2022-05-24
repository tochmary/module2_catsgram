package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<Post> findAll(@RequestParam(defaultValue = "desc") String sort,
                              @RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "10") Integer size) {

        if (!(sort.equals("asc") || sort.equals("desc"))) {
            throw new IllegalArgumentException();
        }
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException();
        }
        Integer from = page * size;
        return postService.findPostById(size, from, sort);
    }

    @GetMapping("/posts/{postId}")
    public Post findPost(@PathVariable(required = false) Integer postId) {
        if (postId != null) {
            return postService.findPostById(postId);
        } else {
            return null;
        }
    }

    @PostMapping(value = "/post")
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }
}