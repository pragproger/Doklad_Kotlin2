package org.server;

public class ThreadedServer {

    public void calculateNumber(CalculationCallback callback) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            double result = 0;
            for(int i = 0;i < 1000;i++) {
                result += i / 2 ;
            }
            result *= Math.random();

            callback.handleResult(result);
        });

        thread.start();
    }

}
