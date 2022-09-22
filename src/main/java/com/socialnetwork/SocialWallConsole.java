package com.socialnetwork;

public class SocialWallConsole {

    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;
    private final ClockService clockService;

    public SocialWallConsole(InputConsole inputConsole, OutputConsole outputConsole, ClockService clockService) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
        this.clockService = clockService;
    }
}
