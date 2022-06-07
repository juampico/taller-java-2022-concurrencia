package Concurrencia;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class PiEstimatorCI implements IPiEstimator{


    @Override
    public double estimatePI(long BATCH_SIZE) {
        long inCircle = 0;
        int cores = Runtime.getRuntime().availableProcessors();
        ConcurrentPi[] threads = new ConcurrentPi[cores];
        CountDownLatch cd = new CountDownLatch(cores);
        for(int i = 0; i< cores; i++){
            threads[i] = new ConcurrentPi((BATCH_SIZE/cores), cd);
            new Thread(threads[i]).start();
        }
        try {
            cd.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (ConcurrentPi cp: threads){
            inCircle += cp.inCircleCount;
        }
        return (4 * ((double)inCircle / BATCH_SIZE));
    }

    private class ConcurrentPi implements Runnable{
        long inCircleCount;
        long iterations;
        CountDownLatch cd;


        public ConcurrentPi(long iterations, CountDownLatch cd){
            this.iterations = iterations;
            this.inCircleCount = 0;
            this.cd = cd;
        }

        @Override
        public void run() {
            for (int i = 0; i < iterations; i++) {
                double x = ThreadLocalRandom.current().nextDouble();
                double y = ThreadLocalRandom.current().nextDouble();
                if (x*x + y*y < 1)
                    inCircleCount++;
            }
            this.cd.countDown();
        }
    }
}
