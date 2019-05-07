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


    public static Map<ImageConstant, ImageIcon> getResources() {
        return resources;
    }
}
