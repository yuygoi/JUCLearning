package NotifyAppointLock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 叶俊晖
 * @date 2019/5/16 0016 17:05
 */
public class NotifyTest {

    public static void main(String[] args){
        ShareData shareData = new ShareData(5);
        for (int i = 0; i <5; i++) {
            final int tempInt = i;
            new Thread(()->{
                for (int j = 0; j < 5; j++) {
                    shareData.print(5*(tempInt+1));
                }
            },String.valueOf(i)).start();
        }
    }
}
class ShareData{
    private volatile int id=0;
    private Lock lock = new ReentrantLock();
    private Condition[] conditions;

    public ShareData(int capicity){
        this.conditions = new Condition[capicity];
        for (int i = 0; i < capicity; i++) {
            conditions[i]=lock.newCondition();
        }
    }

    public void print(int times){
        try{
            lock.lock();
            int id2=Integer.parseInt(Thread.currentThread().getName());
            while (id!=id2){
                conditions[id2].await();
            }
            System.out.println("打印机_"+Thread.currentThread().getName()+"  正在打印。。。");
            for (int i = 1; i <= times; i++) {
                System.out.print(i+" ");
            }
            System.out.println();
            id++;
            id%=5;
            conditions[id].signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
}
