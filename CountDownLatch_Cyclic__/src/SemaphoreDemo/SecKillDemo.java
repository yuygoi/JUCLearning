package SemaphoreDemo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author 叶俊晖
 * @date 2019/5/16 0016 13:49
 */
public class SecKillDemo {

    final static int PRODUCTS = 1000;
    final static int CONSUMERS = 100000;
    final static int TUBES = 10000;

    public static void main(String[] args){

        for (int i = 0; i <CONSUMERS; i++) {
            new Thread(new Consumer(),"买家_"+i).start();
        }
        try {
            Consumer.server.shop.countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("全部出售");
    }
}
class Server{
    int TUBES = 10000;
    Shop shop = new Shop();
    Semaphore semaphore = new Semaphore(TUBES);
}
class Shop{
    int PRODUCTS = 1000;
    CountDownLatch countDownLatch = new CountDownLatch(PRODUCTS);
}
class Consumer implements Runnable{
    static Server server = new Server();

    private boolean isBuyed = false;
    @Override
    public void run() {
        while (!isBuyed&&server.shop.countDownLatch.getCount()>0){
            if (server.semaphore.availablePermits()>0){
                try {
                    server.semaphore.acquire();
                    try {
                        TimeUnit.SECONDS.sleep((long) (Math.random()*5)); } catch (InterruptedException e) { e.printStackTrace(); }
                    System.out.println(Thread.currentThread().getName()+"  买到第"+server.shop.countDownLatch.getCount()+"件商品");
                    server.shop.countDownLatch.countDown();
                    isBuyed = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    server.semaphore.release();
                }
            }
        }
    }
}
