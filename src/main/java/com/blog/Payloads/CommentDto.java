package com.blog.Payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private Integer commentId;

    @NotBlank
    @Size(max = 255)
    private String content;
}
