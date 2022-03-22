package com.likeservice.likeservice.service;

import com.likeservice.likeservice.model.Like;
import com.likeservice.likeservice.repo.LikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LikeService {

    @Autowired
    private LikeRepo likeRepo;

    public List<Like> getLikesPage(String postorcommentId, int page, int pageSize){
        Pageable firstPage = PageRequest.of(page, pageSize);
        List<Like> allLikes=likeRepo.findBypostorcommentID(postorcommentId , (java.awt.print.Pageable) firstPage);
        return  allLikes;

    }

}
