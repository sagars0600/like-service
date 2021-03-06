package com.likeservice.likeservice.model;

import com.likeservice.likeservice.constfiles.ConstFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "like")
@ToString
public class Like {
    @Id
    private String likeID;

@NotEmpty(message=ConstFile.postorcommentID)
    private String postorcommentID;
    private String likedBy;
    private LocalDateTime createdAt;


}
