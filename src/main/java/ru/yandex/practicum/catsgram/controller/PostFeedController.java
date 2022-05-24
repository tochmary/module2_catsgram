package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.FeedParams;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.catsgram.Constants.SORTS;

@Slf4j
@RestController
@RequestMapping("/feed/friends")
public class PostFeedController {
    private final PostService postService;

    @Autowired
    public PostFeedController(PostService postService) {
        this.postService = postService;
    }
/*
    @PostMapping
    public List<Post> findPost(@RequestBody String params) {
        ObjectMapper objectMapper = new ObjectMapper();
        FeedParams friendsParams;
        try {
            log.info("params = {}", params);
            String paramsFromString = objectMapper.readValue(params, String.class);
            log.info("paramsFromString = {}", paramsFromString);
            friendsParams = objectMapper.readValue(paramsFromString, FeedParams.class);
            log.info("friendsParams = {}", friendsParams);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("невалидный формат json", e);
        }
        if (friendsParams != null) {
            List<Post> result = new ArrayList<>();
            for (String friend : friendsParams.getFriendsEmails()) {
                result.addAll(postService.findAllByUserEmail(friend, friendsParams.getSize(), friendsParams.getSort()));
            }
            return result;
        } else {
            throw new RuntimeException("Не заполнен параметр friends!");
        }
    }
*/
    @PostMapping
    List<Post> getFriendsFeed(@RequestBody FeedParams feedParams) {
        if (!SORTS.contains(feedParams.getSort()) || feedParams.getFriendsEmails().isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (feedParams.getSize() == null || feedParams.getSize() <= 0) {
            throw new IllegalArgumentException();
        }

        List<Post> result = new ArrayList<>();
        for (String friendEmail : feedParams.getFriendsEmails()) {
            result.addAll(postService.findAllByUserEmail(friendEmail, feedParams.getSize(), feedParams.getSort()));
        }
        return result;
    }
}
