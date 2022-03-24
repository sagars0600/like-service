package com.likeservice.likeservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.catalina.User;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LikeDto {
    @Id
    private String likeID;
    private String postorcommentID;
    @NotEmpty(message = "user Object is required")
    private User likedBy;
    private LocalDateTime createdAt;
}
