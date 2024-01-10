package com.javahabit.previous;

import java.util.ArrayList;
import java.util.List;

public class ThreadConsumer {
    public static void main(String[] args) throws InterruptedException {

        for(int i =0; i<100_000_000;i++){
            Thread t =  Thread.ofVirtual().unstarted(()-> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            System.out.println(i+1);
            t.start();

        }

    }
}
