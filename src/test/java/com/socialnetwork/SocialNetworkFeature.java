package com.socialnetwork;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SocialNetworkFeature {

    private TestInputConsole inputConsole;
    private TestOutputConsole outputConsole;

    private TestClockService clockService;

    private SocialWallConsole socialWallConsole;
    private SocialWallService socialWallService;

    @BeforeEach
    public void setup() {
        inputConsole = new TestInputConsole();
        outputConsole = new TestOutputConsole();
        clockService = new TestClockService();
        socialWallService = new SocialWallService();

        socialWallConsole = new SocialWallConsole(inputConsole, outputConsole, socialWallService);
    }

    @Test
    void should_post() {
//        Posting: Alice can publish messages to a personal timeline
//        > Alice -> I love the weather today
//        > Bob -> Damn! We lost!
//        > Bob -> Good game though.

//        Reading: Bob can view Alice's timeline
//        > Alice
//        I love the weather today (5 minutes ago)
//        > Bob
//        Good game though. (1 minute ago)
//        Damn! We lost! (2 minutes ago)

        LocalDateTime initialTime = LocalDateTime.of(2022, 9, 1, 12, 0, 0);
        clockService.setTime(initialTime);
        inputConsole.write("Alice -> I love the weather today");
        clockService.setTime(initialTime.plusMinutes(3));
        inputConsole.write("Bob -> Damn! We lost!");
        clockService.setTime(initialTime.plusMinutes(4));
        inputConsole.write("Bob -> Good game though.");

        clockService.setTime(initialTime.plusMinutes(5));
        inputConsole.write("Alice");
        assertThat(outputConsole.getOutput()).isEqualTo("I love the weather today (5 minutes ago)");

        clockService.setTime(initialTime.plusMinutes(5));
        inputConsole.write("Bob");
        assertThat(outputConsole.getOutput()).isEqualTo("Good game though. (1 minute ago)");
        assertThat(outputConsole.getOutput()).isEqualTo("Damn! We lost! (2 minutes ago)");

    }

}
