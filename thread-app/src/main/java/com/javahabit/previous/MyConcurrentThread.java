package com.javahabit.previous;

import java.util.stream.IntStream;

public class MyConcurrentThread extends Thread{
    private int threadId;
    public MyConcurrentThread(int threadId) {
        this.threadId = threadId;
    }

    @Override
    public void run(){

            try {
                //System.out.println(i + " Thread Id: " + threadId);
                System.out.println(Thread.currentThread().isVirtual());
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


    }
}
