package SpinLock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 叶俊晖
 * @date 2019/5/14 0014 20:59
 */
public class SpinLockDemo {

    AtomicReference<Thread> reference = new AtomicReference<>();

    public static void main(String[] args){
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(()->{
            spinLockDemo.myLock();
            try { Thread.sleep(5*1000); } catch (InterruptedException e) { e.printStackTrace();}
            spinLockDemo.myUnLock();
        },"t1").start();
        try { Thread.sleep(1*1000); } catch (InterruptedException e) { e.printStackTrace();}
        new Thread(()->{
            spinLockDemo.myLock();
            try { Thread.sleep(1*1000); } catch (InterruptedException e) { e.printStackTrace();}
            spinLockDemo.myUnLock();
        },"t2").start();
    }

    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName()+" come in");
        while(!reference.compareAndSet(null,thread)){
            //自旋
        }
    }
    
    public void myUnLock(){
        Thread thread = Thread.currentThread();
        reference.compareAndSet(thread,null);
        System.out.println(thread.getName()+" get out");
    }
}

