package com.javahabit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Sample5 {
    private final static ReentrantLock[] rlock = new ReentrantLock[5];
    static {
        for(int x = 0; x<5; x++){
            rlock[x] = new ReentrantLock();
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
        ReentrantLock lock = rlock[taskNumber % rlock.length];
        lock.lock();
        try{
            return sleeptask();
        }catch (Exception ex){
            //DO SOMETHING
        }finally {
            lock.unlock();
        }
        return 0;
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
