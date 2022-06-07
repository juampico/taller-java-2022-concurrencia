package Concurrencia;

public class PiEstimatorSI implements IPiEstimator{


    @Override
    public double estimatePI(long BATCH_SIZE) {
        long inCircleCount = 0;
        for (int i = 0; i < BATCH_SIZE; i++) {
            double x = Math.random();
            double y = Math.random();
            if (x*x + y*y < 1)
                inCircleCount++;
        }
        return (4 * ((double)inCircleCount / BATCH_SIZE));
    }

}
