package org.example.Socket_Server.Thread;

import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * program: Thread.ThreadPool
 * description: 线程池实现类
 * author: SoCMo
 * create: 2020/9/21 19:03
 */
public class ThreadPool {
    public static UserMap userMap;
    private static ExecutorService pool;

    public ThreadPool()
    {
        userMap = new UserMap();
        pool = new ThreadPoolExecutor(100, 100,
                1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    }

    public void submit(ThreadTask threadTask){
        pool.execute(threadTask);
    }
}

