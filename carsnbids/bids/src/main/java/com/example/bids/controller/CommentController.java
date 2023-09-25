package com.example.bids.controller;

import com.example.bids.entity.Ads;
import com.example.bids.entity.Comments;
import com.example.bids.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("biding/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public Comments addComment(@RequestBody Comments comments, @RequestHeader("Authorization") String authorizationHeader){
        return commentService.addComment(comments,authorizationHeader);
    }

    @RequestMapping(value="/viewComments/{adId}", method = RequestMethod.GET)
    public List<Comments> viewComments(@PathVariable("adId") int adId){
        return commentService.viewComment(adId);
    }

}
