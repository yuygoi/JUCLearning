package SemaphoreDemo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author 叶俊晖
 * @date 2019/5/15 0015 21:32
 */
public class SemaphoreDemo {

    public static void main(String[] args){
        Semaphore semaphore = new Semaphore(3);//三个车位
        for (int i = 1; i <=6; i++) {//六辆车
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"  开始停车");
                    try {TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
                    System.out.println(Thread.currentThread().getName()+"  停车结束，离开");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            },"car_"+i).start();
        }
    }
}
