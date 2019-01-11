package red.clf.app.afinal.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author clf
 */
public class ThreadPool{
    private static ThreadPoolExecutor pool;

    static {
        BlockingQueue bqueue = new ArrayBlockingQueue(20);
        ThreadFactory threadFactory=new ThreadFactoryBuilder().setNameFormat("pool-%d").build();
        pool=new ThreadPoolExecutor(2,5,2,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(1024),threadFactory);
    }

    public static void start(Runnable runnable){
        pool.execute(runnable);
    }

    public static void start(Thread thread){
        pool.execute(thread);
    }

    public static void shutdown(){
        pool.shutdown();
    }
}
