package com.twitter.sl;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
public class TweetSO {

    private static final String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    @NotNull(message = "Tweet message can't be null")
    @Size(min=2, max = 160, message= "Tweet message should be between 2 and 160 characters")
    private String tweetMessage;

    @Pattern(regexp = emailRegex, message = "Email should be valid")
    private String userEmail;
}
