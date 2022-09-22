package com.socialnetwork;

import java.util.concurrent.ArrayBlockingQueue;

public class TestInputConsole extends InputConsole {

    private final ArrayBlockingQueue<String> blockingQueue =
            new ArrayBlockingQueue<>(1);

    @Override
    public String read() {
        // block until some command is available
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            return null;
        }
    }

    public void write(String command) {
        // keep the command and signal the read to return
        blockingQueue.offer(command);
    }

}
