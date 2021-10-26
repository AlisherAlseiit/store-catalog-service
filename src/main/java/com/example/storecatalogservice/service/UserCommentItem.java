package com.example.storecatalogservice.service;

import com.example.storecatalogservice.model.Comment;
import com.example.storecatalogservice.model.UserComment;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserCommentItem {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallBackCommentItem")
    public UserComment getUserCommentItem(Long userId) {
        return restTemplate.getForObject("http://store-comment-service/comments/" + userId, UserComment.class);
    }

    public UserComment getFallBackCommentItem(Long userId) {
        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setId(0L);
        comment.setProduct_id(0L);
        comment.setProfile_id(userId);
        comment.setText("NO comment found");


        comments.add(comment);

        return  new UserComment(comments);
    }
}
