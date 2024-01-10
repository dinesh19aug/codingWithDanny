package com.javahabit;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Virtual Thread release platform threads as soon as virtual thread is blocked
 */
public class Sample2 {
    public static void main(String[] args) throws InterruptedException {
        List<Thread> threadList = IntStream.range(1, 11)
                .mapToObj(v -> Thread.ofVirtual()
                        .unstarted(() -> {
                            // If work started on VIRTUAL Thread A and goes sto sleep, WILL It wake up and finish on same Thread???

                            System.out.println("Current Thread (Before Sleep) - " + v + " : " + Thread.currentThread());

                            try {
                                Thread.sleep(100);
                            } catch (Exception ex) {
                                System.out.println("Thread exception");
                            }

                            System.out.println("Current Thread (After Sleep) - " + v + " : " + Thread.currentThread());

                        })).collect(Collectors.toList());

        threadList.forEach(Thread::start);
        for (Thread t: threadList){
            t.join();
        }

    }
}
