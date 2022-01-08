package ch05;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class TestHarness {
    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread(){
                @Override
                public void run() {
                    try {
                        startGate.await(); //래치에 참여한다.
                        task.run();
                        endGate.countDown(); //카운트 갯수를 -1씩 내린다.
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }
        long start = System.nanoTime();
        startGate.countDown();//카운트 갯수를 내린다 1->0 동시에 시작한다
        endGate.await(); //모두가 끝날때까지 기다린다.
        long end = System.nanoTime();
        return end-start;


    }


    public static void main(String[] args) throws InterruptedException {
        TestHarness t = new TestHarness();
        AtomicInteger i = new AtomicInteger(0);
        long time = t.timeTasks(10, () -> System.out.println("do somthing..." + i.addAndGet(1)));
        System.out.println("time : "+ time+" ns ");
    }
}
