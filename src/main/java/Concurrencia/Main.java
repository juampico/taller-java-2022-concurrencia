package Concurrencia;

import javax.management.InstanceAlreadyExistsException;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {

        //ej2();

        //ej2();

        System.out.println(Stream.of(-2, -1, -1, 1, 0, 3).max(Comparator.comparingInt(value -> value)).get());

        /*FileSizeCalculatorSI fs = new FileSizeCalculatorSI();
        System.out.println(fs.getSize("/Users/Juampi/Desktop/Captcha"));
        //WordsUsesCounterSI wc = new WordsUsesCounterSI();
        //wc.run("/Users/Juampi/Desktop/Captcha");*/

        int[][] A = {
                {1, 2, 4, 5, 6},
                {2, 4, 6, 7, 9},
                {2, 4, 6, 8, 10},
                {1, 2, 3, 4, 5}
        };

        System.out.println(indexMax(A, 2, 4, 5));
    }


    public static double secuentialEstimatePI(){
        int trialCount = 0;
        int inCircleCount = 0;
        for (int i = 0; i < 1000000000; i++) {
            double x = Math.random();
            double y = Math.random();
            trialCount++;
            if (x*x + y*y < 1)
                inCircleCount++;
        }
        double estimateForPi = 4 * ((double)inCircleCount / trialCount);
        return estimateForPi;
    }


    public static void ej1(){

        Instant inst1 = Instant.now();
        FileSizeCalculatorSI fs = new FileSizeCalculatorSI();
        System.out.println(fs.getSize("/Users/Juampi/Desktop/"));
        Instant inst2 = Instant.now();
        System.out.println("Tiempo en ejecucion secuencial " + Duration.between(inst1, inst2).toString());

        //ACA  EMPIEZA LO CONCURRENTE

        ForkJoinPool fj = new ForkJoinPool();
        inst1 = Instant.now();
        System.out.println(fj.invoke(new FileSizeCalculatorCI("/Users/Juampi/Desktop/")));
        fj.shutdown();
        inst2 = Instant.now();
        System.out.println("Tiempo en ejecucion concurrente " + Duration.between(inst1, inst2).toString());
    }


    public static void ej2(){


        //SECUENCIAL
        WordsUsesCounterSI wcS = new WordsUsesCounterSI();
        Instant inst1 = Instant.now();
        System.out.println(wcS.getWordUses("/Users/Juampi/Desktop/Captcha"));
        Instant inst2 = Instant.now();
        System.out.println("Tiempo en ejecucion secuencial " + Duration.between(inst1, inst2).toString());





        //CONCURRENTE
        WordsUsesCounterCI wc = new WordsUsesCounterCI();
        inst1 = Instant.now();
        System.out.println(wc.getWordUses("/Users/Juampi/Desktop/Captcha"));
        inst2 = Instant.now();
        System.out.println("Tiempo en ejecucion concurrente " + Duration.between(inst1, inst2).toString());
    }


    public static void ej3(){
        //SECUENCIAL
        PiEstimatorSI piSec = new PiEstimatorSI();
        Instant inst1 = Instant.now();
        System.out.println(piSec.estimatePI(1_000_000_000));
        Instant inst2 = Instant.now();
        System.out.println("Tiempo en ejecucion secuencial " + Duration.between(inst1, inst2).toString());


        //CONCURRENTE
        PiEstimatorCI piCon = new PiEstimatorCI();
        inst1 = Instant.now();
        System.out.println(piCon.estimatePI(1_000_000_000));
        inst2 = Instant.now();
        System.out.println("Tiempo en ejecucion concurrente " + Duration.between(inst1, inst2).toString());
    }



    public static int indexMax(int[][] A, int n, int f, int c){
        int max = 0;
        int indexMax = 0;
        int count;
        for (int i= 0; i < f; i++){
            count = 0;
            for (int j = 0; j < c; j++){
                if (A[i][j] % n == 0){
                    count++;
                }
            }
            if (count > max){
                max = count;
                indexMax = i;
            }
        }
        return indexMax;
    }
}

