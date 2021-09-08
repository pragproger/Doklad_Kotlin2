package org;

import org.client.LessStupidClient;
import org.client.StupidClient;
import org.server.LessStupidServer;
import org.server.StupidServer;

public class MainApp {

    public static void main(String[] args) {
        StupidServer stupidServer = new StupidServer();

        long stupidStart = System.currentTimeMillis();
        System.out.println("Stupid client");
        StupidClient stupidClient = new StupidClient();
        stupidClient.doWork(stupidServer);
        long stupidEnd = System.currentTimeMillis();
        System.out.println("End of stupid client work, time = " + (stupidEnd - stupidStart));

        long lStupidStart = System.currentTimeMillis();
        System.out.println("Less stupid client");

        LessStupidServer lessStupidServer = new LessStupidServer();
        LessStupidClient lessStupidClient = new LessStupidClient();
        lessStupidClient.doWork(lessStupidServer);
        long lstupidEnd = System.currentTimeMillis();
        
        System.out.println("End of less tupid client, time = " + (lstupidEnd - lStupidStart));

    }

}
