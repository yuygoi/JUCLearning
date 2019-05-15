package ABAProblemDemo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author 叶俊晖
 * @date 2019/5/14 0014 21:30
 */
public class AtomicStampedReferenceDemo {
    static AtomicStampedReference<String> atomicReference = new AtomicStampedReference<>("A",1);

    public static void main(String[] args){

        new Thread(()->{
            String exp = atomicReference.getReference();
            int stamp = atomicReference.getStamp();
            try { Thread.sleep(1*1000); } catch (InterruptedException e) { e.printStackTrace();}
            atomicReference.compareAndSet(exp,"B",stamp,stamp+1);
            exp = atomicReference.getReference();
            stamp = atomicReference.getStamp();
            atomicReference.compareAndSet(exp,"A",stamp,stamp+1);
            System.out.println(atomicReference.getReference());
        },"one").start();

        new Thread(()->{
            int stamp = atomicReference.getStamp();
            System.out.println(stamp);
            try { Thread.sleep(5*1000); } catch (InterruptedException e) { e.printStackTrace();}
            atomicReference.compareAndSet("A","C",stamp,stamp+1);
            System.out.println("aaaadfsafs");
            System.out.println(atomicReference.getReference());
        },"two").start();
        try { Thread.sleep(3*1000); } catch (InterruptedException e) { e.printStackTrace();}
    }
}
