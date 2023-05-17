package com.example.Baloot5Backend.controller;

import com.example.Baloot5Backend.model.Comment;
import com.example.Baloot5Backend.model.Store;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/")
public class CommentController {
    Store store = Store.getInstance();
    @PostMapping ("comments")
    public List<Comment> sendComments(@RequestBody String commodityId) {
        StringTokenizer tokenizer = new StringTokenizer(commodityId,"=");
        String id = tokenizer.nextToken();
        ArrayList<Comment> tempComments = new ArrayList<>();
        for (Comment commentObj : store.getComments()){
            if (commentObj.getCommentId() == Integer.valueOf(id)){
                tempComments.add(commentObj);
            }
        }
        return tempComments;
    }
}
