package org.server;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LessStupidServer {

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    public CompletableFuture<Double> calculateNumber() {
        CompletableFuture<Double> future = new CompletableFuture<>();

        executor.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            double result = 0;
            for(int i = 0;i < 10;i++) {
                result += i / 2 ;
            }
            result *= Math.random();
            future.complete(result);
        });

        return future;
    }

    public void close() {
        executor.shutdown();
    }

}
