package com.socialnetwork;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SocialWallConsoleShould {

    @Mock InputConsole inputConsole;
    @Mock OutputConsole outputConsole;
    @Mock ClockService clockService;
    @Mock
    SocialWallService socialWallService;

    @Test
    void read_two_posts_from_input_console_and_pass_to_social_wall() {

        SocialWallConsole socialWallConsole = new SocialWallConsole(inputConsole, outputConsole, socialWallService);

        when(inputConsole.read()).thenReturn("Alice -> I love the weather today",
                "Bob -> Damn! We lost!",
                "exit");

        socialWallConsole.start();

        verify(socialWallService).post("Alice", "I love the weather today");
        verify(socialWallService).post("Bob", "Damn! We lost!");

        // assert that the social wall class has been called with the posting method

    }

    @Test
    void return_a_post_from_a_user() {

        SocialWallConsole socialWallConsole = new SocialWallConsole(inputConsole, outputConsole, socialWallService);

        when(inputConsole.read()).thenReturn("Alice",
                "exit");

        when(socialWallService.returnPosts("Alice")).thenReturn("I love the weather today (5 minutes ago)");
        socialWallConsole.start();


        verify(outputConsole).write("I love the weather today (5 minutes ago)");
    }


}
