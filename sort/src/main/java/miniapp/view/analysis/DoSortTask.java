package miniapp.view.analysis;

import miniapp.Enum.SortEnum;
import miniapp.abstraction.ICommand;
import miniapp.abstraction.SortMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author Tao
 */
public class DoSortTask extends RecursiveAction implements ICommand {
    /**
     * 列
     */
    public static final int abscissa = 22;
    /**
     * 数组长度增量
     */
    public static final int increment = 500000;
    /**
     * Max列长度
     */
    public static final int lastMax = 100000000;

    /**每个doSort对象对应的排序方式*/
    private SortMethod sortMethod;
    private Integer bounds;
    private ProgressBarPanel progrees;
    private MyCanvas myCanvas;

    public DoSortTask(String sortType,SortingAnalysisFrame frame){
        this(sortType,frame,null);
    }

    public DoSortTask(String sortType,SortingAnalysisFrame frame, Integer bounds) {
        this.sortMethod = SortEnum.valueOf(sortType).getSortMethod();
        this.bounds = bounds;
        this.progrees = frame.getProgrees();
        this.myCanvas = frame.getTrendChartCanvas();
    }

    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();

    @Override
    public Void Execute() {
        forkJoinPool.invoke(this);
        return null;
    }

    @Override
    protected void compute() {
        List<SortAnalysisTask> analysisTasks = new ArrayList<>();
        // 数组初始长度
        int length = 0;
        for (int i = 0; i < abscissa - 1; i++,length += increment) {
            // 重新定义数组长度
            int[] array = sortMethod.randomInt(length,bounds == null ? length : bounds);
            // 拆分子任务多线程运行
            analysisTasks.add(new SortAnalysisTask(new SortCommand(sortMethod,progrees, array)));
        }
        // 增加10000w
        int[] array = sortMethod.randomInt(lastMax,bounds == null ? lastMax : bounds);
        // 拆分子任务多线程运行
        analysisTasks.add(new SortAnalysisTask(new SortCommand(sortMethod,progrees, array)));
        invokeAll(analysisTasks);
        analysisTasks.forEach((a) -> {a.join();myCanvas.paint();});
        analysisTasks.clear();
    }

    public static Void cancel(){
        boolean terminated = forkJoinPool.isTerminated();
        if (!terminated){
            forkJoinPool.shutdownNow();
        }
        return null;
    }

    public static ConcurrentHashMap<String, Double[]> getCacheMap() {
        return SortCommand.getCacheMap();
    }
}
