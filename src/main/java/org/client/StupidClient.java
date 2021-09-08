package org.client;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.server.StupidServer;

public class StupidClient {

    private final Map<Integer, Double> results = new ConcurrentHashMap<>();

    public void doWork(StupidServer server) {
        for(int i = 0; i < 10;i++) {
            final int num = i;
            server.calculateNumber(result -> {
                results.put(num, result);
                notifyClient();
            });
        }

        while (results.size() < 10) {
            synchronized (this) {
                try {
                    this.wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        System.out.println("Full calculation: " + results.size());
    }

    public synchronized void notifyClient() {
        this.notifyAll();
    }

}
