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

        UserRepository userRepository = new UserRepository();
        PostRepository postRepository = new PostRepository();
        socialWallService = new SocialWallService(userRepository, postRepository, clockService);

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

        new Thread(() -> socialWallConsole.start()).start();

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

        inputConsole.write("exit");

    }

    @Test
    void follow_and_wall() {

        new Thread(() -> socialWallConsole.start()).start();

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

//        > Charlie -> I'm in New York today! Anyone want to have a coffee?
//        > Charlie follows Alice
//        > Charlie wall
//        Charlie - I'm in New York today! Anyone want to have a coffee? (2 seconds ago)
//        Alice - I love the weather today (5 minutes ago)

        inputConsole.write("Charlie -> I'm in New York today! Anyone want to have a coffee?");
        inputConsole.write("Charlie follows Alice");
        clockService.setTime(initialTime.plusMinutes(5).plusSeconds(2));
        inputConsole.write("Charlie wall");
        assertThat(outputConsole.getOutput()).isEqualTo("Charlie - I'm in New York today! Anyone want to have a coffee? (2 seconds ago)");
        assertThat(outputConsole.getOutput()).isEqualTo("Alice - I love the weather today (5 minutes ago)");

//        > Charlie follows Bob
//        > Charlie wall
//        Charlie - I'm in New York today! Anyone wants to have a coffee? (15 seconds ago)
//        Bob - Good game though. (1 minute ago)
//        Bob - Damn! We lost! (2 minutes ago)
//        Alice - I love the weather today (5 minutes ago)

        inputConsole.write("Charlie follows Bob");
        clockService.setTime(initialTime.plusMinutes(5).plusSeconds(15));
        inputConsole.write("Charlie wall");
        assertThat(outputConsole.getOutput()).isEqualTo("Charlie - I'm in New York today! Anyone want to have a coffee? (15 seconds ago)");
        assertThat(outputConsole.getOutput()).isEqualTo("Good game though. (1 minute ago)");
        assertThat(outputConsole.getOutput()).isEqualTo("Damn! We lost! (2 minutes ago)");
        assertThat(outputConsole.getOutput()).isEqualTo("Alice - I love the weather today (5 minutes ago)");

        inputConsole.write("exit");
    }

}
