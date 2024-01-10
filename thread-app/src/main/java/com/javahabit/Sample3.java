package com.javahabit;

import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * How many Virtual threads can be created
 */
public class Sample3 {
    static Integer MAX_INT = 10000;
    static String type = "P";
    public static void main(String[] args) {
        Set<String> platformThreadNameSet = ConcurrentHashMap.newKeySet();
        Set<String> threadPoolNameSet = ConcurrentHashMap.newKeySet();

        List<Thread> threadList = createThreadList(platformThreadNameSet, threadPoolNameSet, MAX_INT, type);
        //Start Time
        Long  startTime = System.currentTimeMillis();
        threadList.forEach(t-> t.start());
        threadList.forEach(t-> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }});
        Long endTime = System.currentTimeMillis();;
        System.out.println("Time taken(ms): " + (endTime - startTime));
        System.out.println("# of CPU cores available: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Pool Size: " + threadPoolNameSet.size());
        System.out.println("# of Platform threads used: " + platformThreadNameSet.size());


    }

    private static List<Thread> createThreadList(Set<String> platformThreadNameSet, Set<String> threadPoolNameSet, Integer MAX_INT, String type) {
        switch (type){
            case "V": return IntStream.range(0, MAX_INT).mapToObj(
                    v -> Thread.ofVirtual().unstarted(() -> {
                        String poolName = getPoolName(type);
                        threadPoolNameSet.add(poolName);
                        String platformThreadName = getWorkerName(type);
                        platformThreadNameSet.add(platformThreadName);
                        sleepTask(v);

                    })
            ).collect(Collectors.toList());
            default: return IntStream.range(0, MAX_INT).mapToObj(
                    v -> Thread.ofPlatform().unstarted(() -> {
                        String poolName = getPoolName(type);
                        threadPoolNameSet.add(poolName);
                        String platformThreadName = getWorkerName(type);
                        platformThreadNameSet.add(platformThreadName);
                        sleepTask(v);

                    })
            ).collect(Collectors.toList());
        }

    }

    private static void sleepTask(int v) {
        try {
            //System.out.println("Task Id: " + v + "Running on Thread: "+ Thread.currentThread() + " - Going to Sleep");
            Thread.sleep(5000);
            //System.out.println("Task Id: " + v + "Running on Thread: "+ Thread.currentThread() + " - Woke up");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getPoolName(String type) {
        String poolName = Thread.currentThread().toString();
        //System.out.println(poolName);
        switch (type){
            //Sample Virtual toString ==> VirtualThread[#20]/runnable@ForkJoinPool-1-worker-1
            case "V": return poolName.substring(poolName.indexOf("@ForkJoinPool"), poolName.indexOf("-worker"));
            //Sample Platform Thread ==> Thread[#21,Thread-1,5,main]
            default: return poolName.substring(poolName.indexOf("Thread"), poolName.indexOf("Thread") + 1);
        }




    }

    private static String getWorkerName(String type) {
        String workerName = Thread.currentThread().toString();
        switch (type){
            case "V": return workerName.substring(workerName.indexOf("worker"));
            default: return workerName;
        }
    }
}
