package com.likeservice.likeservice.service;

import com.likeservice.likeservice.model.Like;
import com.likeservice.likeservice.repo.LikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LikeService {

    @Autowired
    private LikeRepo likeRepo;
    public Like createLike(Like like, String postOrCommentId){
        like.setPostOrCommentId(postOrCommentId);
        like.setCreatedAt(LocalDateTime.now());
        return this.likeRepo.save(like);

    }

}
