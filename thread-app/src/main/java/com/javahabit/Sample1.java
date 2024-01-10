package com.javahabit;

/**
 * How to create virtual threads and platform threads
 */

public class Sample1 {
    public static void main(String[] args) throws InterruptedException {
        //Platform thread
        Thread pThread = Thread.ofPlatform().name("MyPlatForm-Thread").unstarted(()->
                System.out.println("Current Platform Thread: " + Thread.currentThread()));
        pThread.start();
        Thread vThread = Thread.ofVirtual().name("MyVirtual-Thread").unstarted(()->
                System.out.println("Current Virtual Thread: " + Thread.currentThread()));
        vThread.start();

        pThread.join();
        vThread.join();
    }

}
