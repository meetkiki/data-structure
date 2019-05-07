package game;


import common.ImageConstant;

import javax.swing.ImageIcon;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * @author Tao
 */
public class GameContext {
    /**
     * 图片常量
     */
    private static Map<ImageConstant, ImageIcon> resources = new HashMap<>(32);

    private static ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

    /**
     * 加载图片资源
     */
    static{
        ImageConstant[] values = ImageConstant.values();
        for (ImageConstant constant : values) {
            resources.put(constant,new ImageIcon(GameContext.class.getClassLoader().getResource(constant.getResources())));
        }
    }

    /**
     * 异步执行一个任务
     * @param run
     */
    public static ForkJoinTask submit(Runnable run){
        return forkJoinPool.submit(run);
    }

    /**
     * 异步执行一个任务
     * @param call
     */
    public static<T> ForkJoinTask<T> submit(Callable<T> call){
        return forkJoinPool.submit(call);
    }

    /**
     * 异步提交一个子任务 有返回值
     * @param recursiveTask
     */
    public static<T> T invoke(RecursiveTask<T> recursiveTask){
        return forkJoinPool.invoke(recursiveTask);
    }

    /**
     * 异步提交一个子任务 无返回值
     * @param recursiveAction
     */
    public static void invoke(RecursiveAction recursiveAction){
        forkJoinPool.invoke(recursiveAction);
    }

    /**
     * 获得返回并阻塞
     * @param <T>
     * @return
     */
    public static<T> T getCall(ForkJoinTask<T> forkJoinTask){
        try {
            return forkJoinTask.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("线程执行异常",e);
        }
    }

    /**
     * 线程睡眠
     * @param ms
     */
    public static void sleep(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Map<ImageConstant, ImageIcon> getResources() {
        return resources;
    }
}
