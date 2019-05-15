package ABAProblemDemo;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 叶俊晖
 * @date 2019/5/14 0014 21:15
 */
public class ABADemo {

    static AtomicReference<String> atomicReference = new AtomicReference<>();

    static {
        atomicReference.compareAndSet(null,"A");
    }
    public static void main(String[] args){

        new Thread(()->{
            String exp = atomicReference.get();
            atomicReference.compareAndSet(exp,"B");
            exp = atomicReference.get();
            System.out.println(exp);
            atomicReference.compareAndSet(exp,"A");
            System.out.println(atomicReference.get());
        },"one").start();
        try { Thread.sleep(1*1000); } catch (InterruptedException e) { e.printStackTrace();}
        new Thread(()->{
            atomicReference.compareAndSet("A","C");
            System.out.println(atomicReference.get());
        },"two").start();
    }
}
