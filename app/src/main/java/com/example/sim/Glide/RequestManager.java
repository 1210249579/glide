package com.example.sim.Glide;

import android.util.Log;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import kotlin.jvm.Synchronized;

/*
 * 管理消息隊列
 * 單例模式
 * */
public class RequestManager {
    private static RequestManager manager = new RequestManager();
    //创建请求队列,放入各个请求
    LinkedBlockingQueue<bitmapRequest> queue;
    //每个请求对应的线程
    private PicDispatcherThread[] dispatcher;
    //创建线程的最大数
    private int threadCount;

    public RequestManager() {
        queue = new LinkedBlockingQueue<>();
        //执行线程时，先关闭线程池里所有运行时的线程
        stopThread();
        //初始化线程
        initThread();
    }

    public static RequestManager getInstance() {
        return manager;
    }

    /**
     * 1.拿到APP最大能创建的线程数
     */
    private void initThread() {
        threadCount = Runtime.getRuntime().availableProcessors();
        dispatcher = new PicDispatcherThread[threadCount];
        //创建线程,把线程存入线程数组中
        for (int i = 0; i < threadCount; i++) {
            PicDispatcherThread thread = new PicDispatcherThread(queue);
            thread.start();
            dispatcher[i] = thread;
        }
    }

    /**
     * 将请求的对象加入到请求队列之中
     *
     * @param request
     */
    public void addRequest(bitmapRequest request) {
        if (!queue.contains(request)) {
            queue.add(request);
        }
    }

    public void stopThread() {
        if (dispatcher != null && dispatcher.length > 0) {
            for (PicDispatcherThread thread : dispatcher) {
                if (!thread.isInterrupted()) {//当线程没被回收，停止时
                    thread.interrupt();//kill thread
                }
            }
        }
    }
}
