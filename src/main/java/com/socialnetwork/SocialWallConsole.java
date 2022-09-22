package com.socialnetwork;

public class SocialWallConsole {

    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;
    private final SocialWallService socialWallService;

    public SocialWallConsole(InputConsole inputConsole, OutputConsole outputConsole, SocialWallService socialWallService) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
        this.socialWallService = socialWallService;
    }

    public void start() {

        String readFromConsole = inputConsole.read();
        String[] partsOfUserCommand = readFromConsole.split(" -> ");

        socialWallService.post(partsOfUserCommand[0], partsOfUserCommand[1]);


        // read an input from input console, split username and message, pass to social wall service

    }
}
