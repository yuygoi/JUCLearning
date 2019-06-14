package mythreadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 叶俊晖
 * @date 2019/5/21 0021 20:22
 */
public class MyThreadPool {

    //获取CPU数量  采取CPU密集型策略 核心数+1
    private static int maximumPoolSize = Runtime.getRuntime().availableProcessors()+1;
    //常驻核心线程数
    private static int corePoolSize = 2;
    //空闲时间长度
    private static long keepAliveTime = 1L;
    //时间单位
    private static TimeUnit unit = TimeUnit.SECONDS;
    //阻塞队列容量
    private static int queueSize = 3;

    public static void main(String[] args){
        System.out.println(maximumPoolSize);
        System.out.println();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                new LinkedBlockingQueue<>(queueSize),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );
        try{
            for (int i = 0; i < 100; i++) {
                final int temp = i;
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"  办理业务"+temp);
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            threadPool.shutdown();
        }
    }
}
