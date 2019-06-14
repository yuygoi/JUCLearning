package ProductorConsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 叶俊晖
 * @date 2019/5/17 0017 16:50
 */
public class V3BlockingQueue {

    public static void main(String[] args){
        Sharedata sharedata = new Sharedata(new ArrayBlockingQueue<String>(3));
        new Thread(()->{
            try {
                sharedata.produce();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"producer").start();
        new Thread(()->{
            try {
                sharedata.consumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"consumer").start();
        try {TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("5秒钟过去，老板喊停");
        System.out.println();
        System.out.println();
        sharedata.stop();
    }
}
class Sharedata{
    private AtomicInteger atomicInteger = new AtomicInteger();
    private volatile boolean FLAG = true;
    private BlockingQueue<String> blockingQueue = null;

    public Sharedata(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName()+"  come in");
    }

    public void produce() throws Exception {
        String data = null;
        boolean b;
        while (FLAG){
            //++i
            data = atomicInteger.incrementAndGet()+"";
            b = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (b){
                System.out.println(Thread.currentThread().getName()+"  生产第"+data+"件商品");
            }else{
                System.out.println(Thread.currentThread().getName()+"  插入队列失败");
            }
            try {TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        System.out.println("老板叫停");
    }
    public void consumer() throws Exception {
        String data = null;
        while (FLAG||!blockingQueue.isEmpty()){
            //++i
            data = blockingQueue.poll(1L,TimeUnit.SECONDS)+"";
            if(data==null||"".equals(data)){
                FLAG = false;
                System.out.println("停止生产了");
                return;
            }else if(data!=null&&!"null".equals(data)){
                System.out.println(Thread.currentThread().getName()+"  消费第"+data+"件商品");
                try {TimeUnit.SECONDS.sleep(4); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }

    public void stop(){
        FLAG = false;
    }
}
