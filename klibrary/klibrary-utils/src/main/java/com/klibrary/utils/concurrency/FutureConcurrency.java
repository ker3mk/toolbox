package com.klibrary.utils.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureConcurrency {


    public static void main(String args[]) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<?> future = executor.submit(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Epic fail.");
                }
            }
        });
        System.out.println("Waiting for task to finish..");
        future.get();
        System.out.println("Task finished!");
        executor.shutdown();
    }
}
