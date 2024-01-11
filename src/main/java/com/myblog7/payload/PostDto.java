package com.myblog7.payload;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;



@Data
public class PostDto {
    private Long id;
    @NotEmpty
    @Size(min = 4,message = "Post title should be at least 2 characters")
    private String title;
    @NotEmpty
    @Size(min = 4,message = "Post description should be at least 4 characters")
    private String description;
    @NotEmpty
    @Size(min = 4,message = "Post content should be at least 4 characters")
    private String content;
}
