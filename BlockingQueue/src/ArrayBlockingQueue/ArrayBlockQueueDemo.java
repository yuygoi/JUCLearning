package ArrayBlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author 叶俊晖
 * @date 2019/5/16 0016 15:32
 */
public class ArrayBlockQueueDemo {

    public static void main(String[] args) throws Exception{
        BlockingQueue<String> blockingQeque = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQeque.offer("1",1L, TimeUnit.SECONDS));
        System.out.println(blockingQeque.offer("2",1L, TimeUnit.SECONDS));
        System.out.println(blockingQeque.offer("3",1L, TimeUnit.SECONDS));
        System.out.println(blockingQeque.offer("4",1L, TimeUnit.SECONDS));
        System.out.println(blockingQeque.poll(1L,TimeUnit.SECONDS));
        System.out.println(blockingQeque.poll(1L,TimeUnit.SECONDS));
        System.out.println(blockingQeque.poll(1L,TimeUnit.SECONDS));
        System.out.println(blockingQeque.poll(1L,TimeUnit.SECONDS));
    }

    public static void blockingGroup(BlockingQueue<String> blockingDeque) throws InterruptedException {
        blockingDeque.put("1");
        blockingDeque.put("2");
        blockingDeque.put("3");
//        blockingDeque.put("4");
        System.out.println(blockingDeque.take());
        System.out.println(blockingDeque.take());
        System.out.println(blockingDeque.take());
        System.out.println(blockingDeque.take());
    }

    public static void specialGroup(BlockingQueue<String> blockingDeque) {
        System.out.println(blockingDeque.offer("1"));
        System.out.println(blockingDeque.offer("2"));
        System.out.println(blockingDeque.offer("3"));
        System.out.println(blockingDeque.offer("4"));
        System.out.println(blockingDeque.poll());
        System.out.println(blockingDeque.poll());
        System.out.println(blockingDeque.poll());
        System.out.println(blockingDeque.poll());
    }

    public static void withException(BlockingQueue<String> blockingDeque) {
        System.out.println(blockingDeque.add("1"));
        System.out.println(blockingDeque.add("2"));
        System.out.println(blockingDeque.add("3"));
//        System.out.println(blockingDeque.add("4"));

        System.out.println(blockingDeque.remove());
        System.out.println(blockingDeque.remove());
        System.out.println(blockingDeque.remove());
        System.out.println(blockingDeque.remove());
    }
}
