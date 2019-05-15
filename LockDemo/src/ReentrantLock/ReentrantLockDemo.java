package ReentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 叶俊晖
 * @date 2019/5/14 0014 20:04
 */
public class ReentrantLockDemo {

    public static void SameLockSync() {
        PhoneSync phoneSync = new PhoneSync();
        new Thread(()->{
            phoneSync.sendSMS();
        },"t1").start();
        new Thread(()->{
            phoneSync.sendSMS();
        },"t2").start();
    }
    public static void main(String[] args){
        PhoneLock phoneLock = new PhoneLock();
        new Thread(()->{
            phoneLock.sendSMS();
        },"t1").start();
        new Thread(()->{
            phoneLock.sendSMS();
        },"t2").start();

    }


}

class PhoneLock{
    private Lock lock = new ReentrantLock();
    public void sendSMS(){
        try{
//            lock.lock();
            lock.lock();
            System.out.println(Thread.currentThread().getName()+"  sendSMS()");
            sendEmail();
        }finally{
//            lock.unlock();
            lock.unlock();
        }
    }
    public void sendEmail(){
        try{
            lock.lock();
            System.out.println(Thread.currentThread().getName()+"  sendEmail()");
        }finally{
            lock.unlock();
        }
    }
}

class PhoneSync{

    public synchronized void sendSMS(){
        System.out.println(Thread.currentThread().getName()+"  sendSMS()");
        sendEmail();
    }
    public synchronized void sendEmail(){
        System.out.println(Thread.currentThread().getName()+"  sendEmail()");
    }
}