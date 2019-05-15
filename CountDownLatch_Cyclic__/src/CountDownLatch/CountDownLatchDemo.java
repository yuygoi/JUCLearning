package CountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author 叶俊晖
 * @date 2019/5/15 0015 21:11
 */
public class CountDownLatchDemo {

    public static void main(String[] args){
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <=6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"国,被灭了");
                countDownLatch.countDown();
            },Country.getCountry(i).getName()).start();
        }
        try {
            countDownLatch.await();
            System.out.println("-------------秦国统一华夏");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
