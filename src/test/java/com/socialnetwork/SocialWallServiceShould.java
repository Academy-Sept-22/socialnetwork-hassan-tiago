package com.socialnetwork;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SocialWallServiceShould {

    @Mock ClockService clockService;
    @Mock private UserRepository userRepository;
    @Mock private PostRepository postRepository;
    private SocialWallService service;

    @BeforeEach
    void setUp() {
        service = new SocialWallService(userRepository, postRepository, clockService);
    }

    @Test
    void create_user_and_save_first_post() {

        LocalDateTime postTime = LocalDateTime.of(2022, 9, 1, 12, 0, 0);
        String userName = "Alice";
        String post = "I love the weather today";

        // 1 check if user exists
        when(userRepository.get(userName)).thenReturn(null);
        // 3 get current time
        when(clockService.getCurrentTime()).thenReturn(postTime);

        service.post(userName, post);

        // 2 create user on user repository
        verify(userRepository).create(userName);
        // 4 store post on post repository
        verify(postRepository, times(1)).create(userName, post, postTime);
        verify(userRepository, times(1)).get(userName);
    }

    @Test
    void save_post_for_existing_user() {

        LocalDateTime postTime = LocalDateTime.of(2022, 9, 1, 12, 0, 0);
        String userName = "Alice";
        String post = "I love the weather today";

        // 1 check if user exists
        when(userRepository.get(userName)).thenReturn(new User(userName));

        // 2 get current time
        when(clockService.getCurrentTime()).thenReturn(postTime);

        service.post(userName, post);

        // 3 store post on post repository
        verify(postRepository).create(userName, post, postTime);

        verify(userRepository, times(1)).get(userName);


    }

//    @Test
//    void return_posts_for_user() {
//    }

}