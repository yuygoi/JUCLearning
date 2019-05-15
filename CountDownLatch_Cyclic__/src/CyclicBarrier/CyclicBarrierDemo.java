package CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author 叶俊晖
 * @date 2019/5/15 0015 21:26
 */
public class CyclicBarrierDemo {

    public static void main(String[] args){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println("收集完成");
        });
        for (int i = 1; i <=7; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"  被收集");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },"第"+i+"颗龙珠").start();
        }
    }
}
