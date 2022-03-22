package com.likeservice.likeservice.repo;


import com.likeservice.likeservice.model.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface LikeRepo extends MongoRepository<Like,String> {

    public List<Like> findBypostorcommentID(String postOrCommentId, Pageable pageable);
}
