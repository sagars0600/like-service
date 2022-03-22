package com.likeservice.likeservice.service;

import com.likeservice.likeservice.model.Like;
import com.likeservice.likeservice.repo.LikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepo likeRepo;
    public Like likeDetailsOnID(String likeId){
        return likeRepo.findById(likeId).get();
    }
}
