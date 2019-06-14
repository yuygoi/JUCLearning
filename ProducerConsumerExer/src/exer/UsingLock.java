package exer;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 叶俊晖
 * @date 2019/6/12 0012 10:42
 */
public class UsingLock {
    public static void main(String[] args){
        ShareDataLock shareDataLock = new ShareDataLock(2);
        new Thread(()->{
            try {
                shareDataLock.prod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"0").start();
        new Thread(()->{
            try {
                shareDataLock.prod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"1").start();
    }
}

class ShareDataLock{

    private Lock lock = new ReentrantLock();

    private Condition[] conditions;

    private volatile int ID = 0;

    private AtomicInteger index = new AtomicInteger(0);

    private final char[] data = new char[]{'t','h','i','s',' ','i','s',' ','a',' ','t','e','s','t'};


    public ShareDataLock(int capacity){
        this.conditions = new Condition[capacity];
        for (int i = 0; i < capacity; i++) {
            conditions[i]=lock.newCondition();
        }
    }

    public void prod() throws InterruptedException {
        while(index.get()<data.length){
            try{
                lock.lock();
                int id = Integer.parseInt(Thread.currentThread().getName());
                System.out.println(id);
                while (id!=ID&&index.get()<data.length){
                    conditions[id].await();
                }
                System.out.println("线程"+id+"  "+ data[index.getAndIncrement()]);
                id++;
                id%=conditions.length;
                conditions[id].signal();
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                lock.unlock();
            }
        }
    }

}
