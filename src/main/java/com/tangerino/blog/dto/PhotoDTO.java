package com.tangerino.blog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PhotoDTO {

    private Long id;

    @NotBlank
    private String content;

    private Date dataInsert = new Date();
}

