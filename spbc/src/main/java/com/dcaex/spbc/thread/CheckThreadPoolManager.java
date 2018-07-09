package com.dcaex.spbc.thread;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2018/5/10.
 */
@Component
public class CheckThreadPoolManager implements BeanFactoryAware {

    //用于从IOC里取对象
    private BeanFactory factory; //如果实现Runnable的类是通过spring的application.xml文件进行注入,可通过 factory.getBean()获取，这里只是提一下

    // 线程池维护线程的最少数量
    private final static int CORE_POOL_SIZE = 2;
    // 线程池维护线程的最大数量
    private final static int MAX_POOL_SIZE = 10;
    // 线程池维护线程所允许的空闲时间
    private final static int KEEP_ALIVE_TIME = 0;
    // 线程池所使用的缓冲队列大小
    private final static int WORK_QUEUE_SIZE = 50;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        factory = beanFactory;
    }

    /**
     * 用于储存在队列中的订单,防止重复提交,在真实场景中，可用redis代替 验证重复
     */
    Map<String, Object> cacheMap = new ConcurrentHashMap<>();


    /**
     * 订单的缓冲队列,当线程池满了，则将订单存入到此缓冲队列
     */
    Queue<Object> msgQueue = new LinkedBlockingQueue<Object>();


    /**
     * 当线程池的容量满了，执行下面代码，将交易码存入到缓冲队列
     */
    final RejectedExecutionHandler handler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            //交易码加入到缓冲队列
            msgQueue.offer(((CheckThread) r).getTxHash());
            System.out.println("系统任务太忙了,把此交易码交给(调度线程池)逐一处理，交易码：" + ((CheckThread) r).getTxHash());
        }
    };


    /**创建线程池*/
    final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue(WORK_QUEUE_SIZE), this.handler);


    /**将任务加入订单线程池*/
    public void addOrders(String txHash){
        System.out.println("交易hash准备添加到线程池，交易码：" + txHash);
        //验证当前进入的交易码是否已经存在
        if (cacheMap.get(txHash) == null) {
            cacheMap.put(txHash, new Object());
            CheckThread checkThread = new CheckThread(txHash);
            threadPool.execute(checkThread);
        }
    }

    /**
     * 线程池的定时任务----> 称为(调度线程池)。此线程池支持 定时以及周期性执行任务的需求。
     */
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);


    /**
     * 检查(调度线程池)，每秒执行一次，查看交易码的缓冲队列是否有 交易码记录，则重新加入到线程池
     */
    final ScheduledFuture scheduledFuture = scheduler.scheduleAtFixedRate(new Runnable() {
        @Override
        public void run() {
            //判断缓冲队列是否存在记录
            if(!msgQueue.isEmpty()){
                //当线程池的队列容量少于WORK_QUEUE_SIZE，则开始把缓冲队列的交易码 加入到 线程池
                if (threadPool.getQueue().size() < WORK_QUEUE_SIZE) {
                    String txHash = (String) msgQueue.poll();
                    CheckThread checkThread = new CheckThread(txHash);
                    threadPool.execute(checkThread);
                    System.out.println("(调度线程池)缓冲队列出现交易码查询任务，重新添加到线程池，交易码："+txHash);
                }
            }
        }
    }, 0, 1, TimeUnit.SECONDS);


    /**获取消息缓冲队列*/
    public Queue<Object> getMsgQueue() {
        return msgQueue;
    }

    /**终止订单线程池+调度线程池*/
    public void shutdown() {
        //true表示如果定时任务在执行，立即中止，false则等待任务结束后再停止
        System.out.println("终止交易码线程池+调度线程池："+scheduledFuture.cancel(false));
        scheduler.shutdown();
        threadPool.shutdown();

    }
}