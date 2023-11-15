package com.tangerino.blog.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class PostInfoDTO {
        private String user;
        private String title;
        private String content;
        private List<CommentDTO> comments;

}
