package game;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import common.ImageConstant;

import javax.swing.ImageIcon;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Tao
 */
public class GameContext {
    /**
     * 图片常量
     */
    private static Map<ImageConstant, ImageIcon> resources = new HashMap<>(32);


    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("context-pool-%d").build();

    /**
     *  全局线程池
     */
    private static ExecutorService executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(),
            1000L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

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
     * 执行一个任务
     * @param run
     */
    public static void execute(Runnable run){
        executorService.execute(run);
    }


    public static Map<ImageConstant, ImageIcon> getResources() {
        return resources;
    }
}
