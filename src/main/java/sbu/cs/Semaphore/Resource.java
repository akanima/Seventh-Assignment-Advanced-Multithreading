package sbu.cs.Semaphore;

import java.util.concurrent.Semaphore;

public class Resource {
    static Semaphore sem=new Semaphore(2,true);


    public static void runAccessResource(){
        try{
            sem.acquire();
        }catch (InterruptedException e){
            throw  new RuntimeException();
        }
        try{
            accessResource();
        }finally {
            sem.release();
        }
    }

    public static void accessResource() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
