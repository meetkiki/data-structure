package miniapp.view.analysis;

import miniapp.Enum.SortEnum;
import miniapp.abstraction.ICommand;
import miniapp.abstraction.SortMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author Tao
 */
public class DoSortTask extends RecursiveTask implements ICommand {

    public static final int abscissa = 21;
    public static final int increment = 50000;

    /**每个doSort对象对应的排序方式*/
    private SortMethod sortMethod;
    private Integer bounds;

    public DoSortTask(){}
    public DoSortTask(String sortType){
        this(sortType,null);
    }

    public DoSortTask(String sortType, Integer bounds) {
        this.sortMethod = SortEnum.valueOf(sortType).getSortMethod();
        this.bounds = bounds;
    }

    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();

    @Override
    public void Execute() {
        try {
            forkJoinPool.submit(this).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Object compute() {
        List<SortAnalysisTask> analysisTasks = new ArrayList<>();
        // 数组初始长度
        int length = increment;
        for (int i = 0; i < abscissa; i++,length += increment) {
            // 重新定义数组长度
            int[] array = sortMethod.randomInt(length,bounds == null ? length : bounds);
            // 拆分子任务多线程运行
            analysisTasks.add(new SortAnalysisTask(new SortCommand(sortMethod, array)));
        }
        invokeAll(analysisTasks);
        for (SortAnalysisTask task : analysisTasks) {
            task.join();
        }
        return SortCommand.getCacheMap();
    }

    public static ConcurrentHashMap<String, long[]> getCacheMap() {
        return SortCommand.getCacheMap();
    }
}
