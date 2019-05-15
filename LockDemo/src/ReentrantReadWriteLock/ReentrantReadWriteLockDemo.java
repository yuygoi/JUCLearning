package ReentrantReadWriteLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 叶俊晖
 * @date 2019/5/15 0015 19:45
 */
public class ReentrantReadWriteLockDemo {

    public static void main(String[] args){
//        MyCacheNoLock noLock = new MyCacheNoLock();
        MyCacheWithLock noLock = new MyCacheWithLock();
        for (int i = 0; i <5; i++) {
            final int key = i;
            new Thread(()->{
                noLock.write(key+"",key);
            },"thread_"+i).start();
        }
        for (int i = 0; i <5; i++) {
            final int key = i;
            new Thread(()->{
                noLock.read(key+"");
            },"thread_"+i).start();
        }
    }

}

class MyCacheWithLock{
    private Map<String,Object> map = new HashMap<>();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void write(String key,Object value){
        try{
            lock.writeLock().lock();
            System.out.println(Thread.currentThread().getName()+"  开始写入：");
            try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"  写入完成");
        }finally{
            lock.writeLock().unlock();
        }
    }

    public void read(String key){
        try{
            lock.readLock().lock();
            System.out.println(Thread.currentThread().getName()+"  开始读取：");
            try {TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            map.get(key);
            System.out.println(Thread.currentThread().getName()+"  读取完成");
        }finally{
            lock.readLock().unlock();
        }
    }
}

class MyCacheNoLock{
    private Map<String,Object> map = new HashMap<>();

    public void write(String key,Object value){
        System.out.println(Thread.currentThread().getName()+"  开始写入：");
        try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
        map.put(key,value);
        System.out.println(Thread.currentThread().getName()+"  写入完成");
    }

    public void read(String key){
        System.out.println(Thread.currentThread().getName()+"  开始读取：");
        try {TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
        map.get(key);
        System.out.println(Thread.currentThread().getName()+"  读取完成");
    }
}