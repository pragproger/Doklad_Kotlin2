package org.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.server.LessStupidServer;

public class LessStupidClient {

    private static class Pair {
        public int number;
        public double result;
    }

    public void doWork(LessStupidServer server) {
        List<CompletableFuture<Pair>> futures = new ArrayList<>();

        for(int i = 0; i < 10;i++) {
            final int num = i;

            CompletableFuture<Pair> future = server.calculateNumber().thenApply(value -> {
                Pair pair = new Pair();
                pair.result = value;
                pair.number = num;
                return pair;
            });

            futures.add(future);
        }

        Map<Object, Optional<Double>> results = futures.stream()
            .map(CompletableFuture::join)
            .collect(Collectors.groupingBy(pair -> pair.number,
                Collectors.mapping(pair -> pair.result,
                    Collectors.<Double>reducing((l1, l2) -> l1))));

        System.out.println("Final results:" +results);
        server.close();
    }

}
