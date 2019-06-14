package deadlock;

import java.util.concurrent.TimeUnit;

/**
 * @author 叶俊晖
 * @date 2019/5/21 0021 21:24
 */
public class DeadLock {
    public static void main(String[] args){
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new ShareData(lockA,lockB),"threadA").start();
        new Thread(new ShareData(lockB,lockA),"threadB").start();
    }
}

class ShareData implements Runnable{
    private String lockA;
    private String lockB;

    public ShareData(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }


    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"  持有"+lockA+"  尝试获得"+lockB);
            try {
                TimeUnit.SECONDS.sleep(2L); } catch (InterruptedException e) { e.printStackTrace(); }
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"  获得"+lockB+"成功");
            }
        }
    }
}
