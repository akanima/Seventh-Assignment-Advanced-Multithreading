package sbu.cs.CalculatePi;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PiCalculator {
    static BigDecimal finalRes=new BigDecimal(0);
    public static class calculatePi implements Runnable{
        private  int n;
        public calculatePi(int n) {
            this.n=n;
        }
        @Override
        public void run() {
            BigDecimal sign=new BigDecimal(1);
            if(n%2==0){
                sign=new BigDecimal(-1);
            }
            BigDecimal numerator=new BigDecimal(4);
            numerator=numerator.multiply(sign);
            int stepsForDividing=n * 2 - 1;
            BigDecimal denominator=new BigDecimal(stepsForDividing);
            BigDecimal initialResult=numerator.divide(denominator,10000,RoundingMode.HALF_DOWN);
            sumBigDecimal(initialResult);
        }
        public BigDecimal calFact(int x){
            BigDecimal y=new BigDecimal(1);
            if(x==0){
                return new BigDecimal(1);
            }
            else{
                for(int i=1; i<=x; i++){
                    y=y.multiply(BigDecimal.valueOf(i));
                }
            }
            return y;
        }
        public BigDecimal calFact(BigDecimal x){
            BigDecimal y=new BigDecimal(1);
            int z=x.intValue();
            for(int i=1; i<=z; i++){
                y=y.multiply(BigDecimal.valueOf(i));
            }

            return y;
        }
        public static synchronized void sumBigDecimal(BigDecimal value){
           finalRes=finalRes.add(value);
        }
    }
    public  String calculate(int floatingPoint)
    {
        ExecutorService tp=Executors.newFixedThreadPool(6);

        for(int i=1; i<1000; i++){
            calculatePi cpTask=new calculatePi(i);
            tp.execute(cpTask);
        }
        tp.shutdown();
        try {
            tp.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return finalRes.setScale(floatingPoint,RoundingMode.FLOOR).toString();
    }
}
