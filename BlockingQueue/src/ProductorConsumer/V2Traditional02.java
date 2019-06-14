package ProductorConsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 叶俊晖
 * @date 2019/5/16 0016 16:17
 */
public class V2Traditional02 {

    public static void main(String[] args){
        ShareData02 shareData02 = new ShareData02();
        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    shareData02.add();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"producer").start();
        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    shareData02.decrease();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"consumer").start();
    }
}
class ShareData02{
    private int number=0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void add() throws Exception{
        try{
            lock.lock();
            while (number!=0){
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName()+"  "+number);
            condition.signalAll();
        }finally{
            lock.unlock();
        }
    }
    public void decrease() throws Exception{
        try{
            lock.lock();
            while (number==0){
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName()+"  "+number);
            condition.signalAll();
        }finally{
            lock.unlock();
        }
    }
}
