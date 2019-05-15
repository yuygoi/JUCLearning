package volatiledemo;

import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 叶俊晖
 * @date 2019/5/11 0011 13:50
 */

/*
    volatile : java虚拟机提供的轻量级的同步机制（简单来说就是synchronize的阉割版）
        保证可见性: 见seeOK_atomicNG()
        不保证原子性：见seeOK_atomicNG()
               如何解决原子性：使用原子类见solveAtomicProblem()

 */

public class VolatileDemo {
    int x = 5;
    boolean in = false;
    public static void main(String[] args){
        for (int i = 0; i <20; i++) {
            new Thread(()->{
                VolatileDemo volatileDemo = new VolatileDemo();
                volatileDemo.method1();
                volatileDemo.method2();
            },"thread_"+i).start();
        }
    }

    public void method1(){
        this.x++;
        this.in = true;
    }

    public void method2(){
        if(in){
            this.x = this.x + 5;
            System.out.println("x = "+x);
        }
    }
    private static void solveAtomicProblem() {
        MyNumber myNumber = new MyNumber();
        //如何解决原子性问题
        for (int i = 0; i <200; i++) {
            new Thread(()->{
                myNumber.addAtomic();
            },"thread_"+i).start();
        }
        while(Thread.activeCount()>2){
            //正在活动的两个线程：1.main线程 2.GC线程
             Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+"  "+myNumber.atomicInteger);
    }

    private static void seeOK_atomicNG() {
        MyNumber myNumber = new MyNumber();
        //不保证原子性
        for (int i = 0; i <2000; i++) {
            new Thread(()->{
                myNumber.addPP();
            },"thread_"+i).start();
        }
        //保证可见性
        System.out.println(myNumber.numV);
    }

    private static void isSeeTest() {
        MyNumber myNumber = new MyNumber();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"  "+"mission start");
            myNumber.addto60();
            try { Thread.sleep(5*1000); } catch (InterruptedException e) { e.printStackTrace();}
            System.out.println(Thread.currentThread().getName()+"  "+"mission finished"+myNumber.number);
        },"线程1").start();
        while(Thread.activeCount()>2){
            //正在活动的两个线程：1.main线程 2.GC线程
             Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+"  "+myNumber.number);
    }
}
class MyNumber{
    volatile int numV = 0;
    int number = 0;
    AtomicInteger atomicInteger = new AtomicInteger();
    public void addto60(){
        this.number = 60;
    }
    public void addPP(){
        for (int i = 0; i < 100; i++) {
            this.numV++;
        }
    }
    public void addAtomic(){
        for (int i = 0; i < 100; i++) {
            atomicInteger.getAndIncrement();
        }
    }
}
