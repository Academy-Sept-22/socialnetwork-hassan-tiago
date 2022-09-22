package com.socialnetwork;

public class SocialWallConsole {

    public static final String POST_SYMBOL = " -> ";
    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;
    private final SocialWallService socialWallService;

    public SocialWallConsole(InputConsole inputConsole, OutputConsole outputConsole, SocialWallService socialWallService) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
        this.socialWallService = socialWallService;
    }

    public void start() {

        while(true) {
            String readFromConsole = inputConsole.read();

            if (isPostCommand(readFromConsole)) {
                String[] partsOfUserCommand = readFromConsole.split(POST_SYMBOL);
                socialWallService.post(partsOfUserCommand[0], partsOfUserCommand[1]);
                continue;
            }

            if (isExitCommand(readFromConsole)) {
                break;
            }

            if (isReadingCommand(readFromConsole)) {
                String posts = socialWallService.returnPosts(readFromConsole);
                outputConsole.write(posts);
                continue;
            }
        }

    }

    private static boolean isReadingCommand(String readFromConsole) {
        return !readFromConsole.contains(" ");
    }

    private boolean isExitCommand(String readFromConsole) {
        return readFromConsole.equals("exit");
    }

    private boolean isPostCommand(String readFromConsole) {
        return readFromConsole.contains(POST_SYMBOL);
    }
}
