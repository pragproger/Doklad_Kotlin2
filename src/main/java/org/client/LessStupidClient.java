package org.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.server.LessStupidServer;
import org.server.StupidServer;

public class LessStupidClient {

    private final Map<Integer, Double> results = new ConcurrentHashMap<>();

    public void doWork(StupidServer stupidServer, LessStupidServer server) {
        List<CompletableFuture<Double>> futures = new ArrayList<>();

        for(int i = 0; i < 10;i++) {
            final int num = i;

            final CompletableFuture<Double> stupidFuture = new CompletableFuture<>();

            stupidServer.calculateNumber(value -> {
                stupidFuture.complete(value);
            });

            CompletableFuture<Double> lessStupidFuture = server.calculateNumber().thenApply(value -> {
                results.put(num, value);
                return value;
            });

            CompletableFuture<Double> future = stupidFuture.thenCombineAsync(lessStupidFuture, (stupidValue, value) ->
                stupidValue + value
            );

            future.thenApply(value -> {
                results.put(num, value);
                return value;
            });

            futures.add(future);
        }

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        allFutures.join();

        System.out.println("Final results:" +results);
        server.close();
    }

}
