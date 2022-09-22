package com.socialnetwork;

import java.time.LocalDateTime;

public class Post {

    private String userName;
    private String post;
    private LocalDateTime time;

    public Post(String userName, String post, LocalDateTime time) {
        this.userName = userName;
        this.post = post;
        this.time = time;
    }
}
