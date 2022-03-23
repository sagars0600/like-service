package com.likeservice.likeservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "like")
public class Like {
    @Id
    private String likeID;
    @NotEmpty(message = "user ID is required")
    private String userID;
    private String postorcommentID;
    private String likedBy;
    private LocalDateTime createdAt;


}
