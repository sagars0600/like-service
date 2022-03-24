package com.likeservice.likeservice.service;

import com.likeservice.likeservice.feign.FeignUser;
import com.likeservice.likeservice.model.Like;
import com.likeservice.likeservice.model.LikeDto;
import com.likeservice.likeservice.repo.LikeRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

@Service
public class LikeService {

    @Autowired
    private LikeRepo likeRepo;
    @Autowired
    private FeignUser feignUser;

    public Like createLike(@NotNull Like like, String postorcommentId){
        like.setPostorcommentID(postorcommentId);
        like.setCreatedAt(LocalDateTime.now());
        return this.likeRepo.save(like);

    }


    public List<LikeDto> likesPage(String postOrCommentId, Integer page, Integer pageSize){
        if(page==null){
            page=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        Pageable firstPage = PageRequest.of(page-1, pageSize);
        List<Like> allLikes=likeRepo.findBypostorcommentID(postOrCommentId, (java.awt.print.Pageable) firstPage);

        List<LikeDto> likeDTOS = new ArrayList<>();
        for(Like like:allLikes){
            LikeDto likeDTO=new LikeDto(like.getLikeID(),like.getPostorcommentID(),
                    feignUser.findByID(like.getLikedBy()),like.getCreatedAt());
            likeDTOS.add(likeDTO);
        }
        return  likeDTOS;

    }


    public Like likeDetailsOnID(String likeId){
        return
                likeRepo.findById(likeId).get();
    }

    public String deleteLike(String likeId){
        likeRepo.deleteById(likeId);
        return "Deleted likeId "+likeId+" successfully";
    }

}
