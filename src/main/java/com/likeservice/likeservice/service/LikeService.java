package com.likeservice.likeservice.service;

import com.likeservice.likeservice.constfiles.ConstFile;
import com.likeservice.likeservice.exception.LikeNotFoundException;
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



    public List<LikeDto> likesPage(String postOrCommentId,Integer page, Integer pageSize){
        if(page==null){
            page=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        Pageable firstPage = PageRequest.of(page-1, pageSize);
        List<Like> allLikes=likeRepo.findBypostorcommentID(postOrCommentId,firstPage);
        if(allLikes.isEmpty()){
            throw new LikeNotFoundException("Like ID Doesnot Exists");
        }
        List<LikeDto> likeDTOS = new ArrayList<>();
        for(Like like:allLikes){
            LikeDto likeDTO=new LikeDto(like.getLikeID(),like.getPostorcommentID(),
                     feignUser.findByID(like.getLikedBy()),like.getCreatedAt());
            likeDTOS.add(likeDTO);
        }
        return  likeDTOS;

    }


    public LikeDto likeDetailsOnID(String likeId){
        if(likeRepo.findById(likeId).isPresent()){
            Like like=likeRepo.findById(likeId).get();

            LikeDto likeDTO =new LikeDto(like.getLikeID(),like.getPostorcommentID(),
                    feignUser.findByID(like.getLikedBy()),like.getCreatedAt());
            return likeDTO;
        }
        else{
            throw  new LikeNotFoundException("Not found");
        }


    }

    public String deleteLike(String likeId){

        likeRepo.deleteById(likeId);
       throw  new LikeNotFoundException(ConstFile.delete);
    }
    public int countLikes(String postOrCommentId){
        List<Like> allData=likeRepo.findAll();
        int count=0;
        for(Like like:allData){
            if(like.getPostorcommentID().equals(postOrCommentId)){
                count++;
            }
        }
        return count;
    }

    public LikeDto likeCreate(Like like, String postOrCommentId){
        like.setPostorcommentID(postOrCommentId);
        like.setCreatedAt(LocalDateTime.now());
        likeRepo.save(like);
        LikeDto likeDTO =new LikeDto(like.getLikeID(),like.getPostorcommentID(),
                feignUser.findByID(like.getLikedBy()),like.getCreatedAt());
        return likeDTO;

    }
}
