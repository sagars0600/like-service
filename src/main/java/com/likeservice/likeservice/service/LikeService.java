package com.likeservice.likeservice.service;

import com.likeservice.likeservice.model.Like;
import com.likeservice.likeservice.repo.LikeRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

import java.time.LocalDateTime;

@Service
public class LikeService {

    @Autowired
    private LikeRepo likeRepo;
    public Like createLike(@NotNull Like like, String postorcommentId){
        like.setPostorcommentID(postorcommentId);
        like.setCreatedAt(LocalDateTime.now());
        return this.likeRepo.save(like);

    }


    public List<Like> getLikesPage(String postorcommentId, int page, int pageSize){
        Pageable firstPage = PageRequest.of(page, pageSize);
        List<Like> allLikes=likeRepo.findBypostorcommentID(postorcommentId , (java.awt.print.Pageable) firstPage);
        return  allLikes;

    }


    public Like likeDetailsOnID(String likeId){
        return likeRepo.findById(likeId).get();
    }

    public String deleteLike(String likeId){
        likeRepo.deleteById(likeId);
        return "Deleted likeId "+likeId+" successfully";
    }

}
