package ProductorConsumer;

/**
 * @author 叶俊晖
 * @date 2019/5/16 0016 16:02
 */
/*
    线程操作资源类，高内聚，低耦合
    一判断，二开工，三唤醒
    严防虚假判断
 */
public class V1Traditional01 {
    public static void main(String[] args){
        ShareData01 shareData01 = new ShareData01();
        for (int i = 0; i <5; i++) {
            new Thread(()->{
                try {
                    shareData01.add();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"producer_"+i).start();
        }
        for (int i = 0; i <5; i++) {
            new Thread(()->{
                try {
                    shareData01.decrease();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"consumer_"+i).start();
        }
    }
}
class ShareData01{
    private int number = 0;

    public synchronized void add() throws Exception{
        while (number!=0){
            this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName()+"  生产 "+number);
        this.notifyAll();
    }
    public synchronized void decrease() throws Exception{
        while (number==0){
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName()+"  消费 "+number);
        this.notifyAll();
    }

}
