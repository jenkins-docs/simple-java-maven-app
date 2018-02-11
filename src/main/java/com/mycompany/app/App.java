package com.mycompany.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);


    public void greet(String who) {
        LOGGER.info("Hello {}!", who);
    }

    public void run() throws InterruptedException {

        Deque d;
        Queue q;

        executorService.scheduleAtFixedRate(() -> greet("World"), 2, 2, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(() -> greet("Everybody"), 2, 2, TimeUnit.SECONDS);
        Thread.sleep(20000);
        executorService.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        new App().run();
    }
}
