package com.blog.Payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    @Length(max = 255)
    private String title;

    @NotBlank
    @Length(max = 5000)
    private String content;
    private String imageUrl;
    private CategoryDto categoryDto;
    private Date createdOn;
    private UserDto userDto;
    private List<CommentDto> comments;
}
