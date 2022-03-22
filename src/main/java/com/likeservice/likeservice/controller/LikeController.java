package com.likeservice.likeservice.controller;

import com.likeservice.likeservice.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;
}
