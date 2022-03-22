package com.likeservice.likeservice.repo;


import com.likeservice.likeservice.model.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LikeRepo extends MongoRepository<Like,String> {

}
