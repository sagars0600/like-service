package com.likeservice.likeservice.controller;

import com.likeservice.likeservice.model.Like;
import com.likeservice.likeservice.model.LikeDto;
import com.likeservice.likeservice.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;
import java.util.List;


@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;


    @DeleteMapping("/postsOrComments/{postOrCommentId}/likes/{likeId}")
    public ResponseEntity<String> deleteLike(@PathVariable("likeId") String likeId, @PathVariable("postOrCommentId") String postOrCommentId ){
        return new ResponseEntity<>(likeService.deleteLike(likeId), HttpStatus.ACCEPTED);
}
    @PostMapping("/postsOrComments/{postOrCommentId}/likes")
    public ResponseEntity<Like> createLike(@PathVariable("postOrCommentId") String postOrCommentId, @RequestBody @Valid Like like){
        return new ResponseEntity<>(likeService.createLike(like,postOrCommentId), HttpStatus.ACCEPTED);
    }


    @GetMapping("/postsOrComments/{postOrCommentId}/likes")
    public ResponseEntity<List<LikeDto>> likesPage(@PathVariable("postOrCommentId") String postOrCommentId, @QueryParam("page") Integer  page, @QueryParam("pageSize") Integer  pageSize){
        return new ResponseEntity<>(likeService.likesPage(postOrCommentId,page,pageSize), HttpStatus.ACCEPTED);
    }


    @GetMapping("/postsOrComments/{postOrCommentId}/likes/{likeId}")
    public ResponseEntity<Like> likeDetailsOnID(@PathVariable("likeId") String likeId, @PathVariable("postOrCommentId") String postOrCommentId){
        return new ResponseEntity<>(likeService.likeDetailsOnID(likeId), HttpStatus.ACCEPTED);



    }
}
