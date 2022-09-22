package com.socialnetwork;

import java.util.LinkedList;

public class TestOutputConsole extends OutputConsole {

    private final LinkedList<String> fifo = new LinkedList<>();

    @Override
    public void write(String line) {
        fifo.add(line);
    }

    public String getOutput() {
        return fifo.getFirst();
    }

}
