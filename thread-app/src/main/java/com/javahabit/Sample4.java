package com.javahabit;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Sample4 {
    private final static Object[] lock = new Object[5];
    static {
        for(int x = 0; x<5; x++){
            lock[x] = new Object();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        final long start = System.currentTimeMillis();
        try(ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()){
            for (int i=0;i<20;i++){
                int taskId = i;
                System.out.println("Thread:: "+ Thread.currentThread() +"  | Elapsed Time: " + (System.currentTimeMillis() - start));
                executorService.submit(()->{
                    synchronizedTasks(taskId);
                    System.out.println("Thread:: "+ Thread.currentThread() +"  | Elapsed Time: " + (System.currentTimeMillis() - start));
                });
            }
        }
        System.out.println("Thread:: "+ Thread.currentThread() +"  | Total Time: " + (System.currentTimeMillis() - start));
    }

    private static long synchronizedTasks(int taskNumber){
        synchronized (lock[taskNumber % lock.length]){
            return sleeptask();
        }
    }

    private static long sleeptask() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


}
