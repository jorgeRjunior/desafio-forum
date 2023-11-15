package com.tangerino.blog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PostDTO {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;


}

