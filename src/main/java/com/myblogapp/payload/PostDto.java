package com.myblogapp.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull; //validation is extention of hibernate annotation.
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;

@NotNull
@Size(min = 2, message = "post title should have at least 2 characters")
    private String title;

@NotNull
@Size(min = 10, message = "post description should have at least 10 characters or more")
    private String description;
@NotNull
@NotEmpty
    private String content;

//for email use @Email annotation and message = Email format is not valid
// for mobile number use @Size(min = 10, max=10) annotation and message = mobile number is not valid.
}
