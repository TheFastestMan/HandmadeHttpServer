package ru.rail;

import java.io.IOException;

public class HttpServerRunner {
    public static void main(String[] args) throws IOException {
        HttpServerTask httpServerTask = new HttpServerTask(8084, 100);
        httpServerTask.run();
    }
}
