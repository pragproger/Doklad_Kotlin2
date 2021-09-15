package org;

import org.client.FutureBasedClient;
import org.client.SimpleClient;
import org.server.TaskServer;
import org.server.ThreadedServer;

public class MainApp {

    public static void main(String[] args) {
        System.out.println("Java concurrency examples");
        ThreadedServer stupidServer = new ThreadedServer();

        long stupidStart = System.currentTimeMillis();
        System.out.println("Simple client");
        SimpleClient stupidClient = new SimpleClient();
        stupidClient.doWork(stupidServer);
        long stupidEnd = System.currentTimeMillis();
        System.out.println("End of simple client work, time = " + (stupidEnd - stupidStart));

        long lStupidStart = System.currentTimeMillis();
        System.out.println("Future based client");

        TaskServer lessStupidServer = new TaskServer();
        FutureBasedClient lessStupidClient = new FutureBasedClient();
        lessStupidClient.doWork(stupidServer, lessStupidServer);
        long lstupidEnd = System.currentTimeMillis();

        System.out.println("End of future based client, time = " + (lstupidEnd - lStupidStart));

    }

}
