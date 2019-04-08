package miniapp.view.analysis;

import miniapp.Enum.Constant;
import miniapp.Enum.SortEnum;
import miniapp.abstraction.ICommand;
import miniapp.abstraction.SortMethod;
import miniapp.utils.UnequalConversion;
import miniapp.view.screens.SortingAnalysisScreen;

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

    /**
     * 重复度 0 为几乎不重复 100为100%重复
     */
    private static volatile double multiplicity = 0.00;

    /**每个doSort对象对应的排序方式*/
    private SortMethod sortMethod;
    private ProgressBarPanel progrees;
    private MyCanvas myCanvas;

    public DoSortTask(String sortType, SortingAnalysisScreen frame) {
        this.sortMethod = SortEnum.valueOf(sortType).getSortMethod();
        this.progrees = frame.getProgrees();
        this.myCanvas = frame.getTrendChartCanvas();
    }

    private static final ForkJoinPool forkJoinPool = new ForkJoinPool(ProgressBarPanel.QueueSize);

    @Override
    public Void Execute() {
        try {
            forkJoinPool.submit(this).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void compute() {
        List<SortAnalysisTask> analysisTasks = new ArrayList<>();
        // 数组初始长度
        int length = 0;
        for (int i = 0; i < abscissa - 1; i++,length += increment) {
            // 重新定义数组长度
            int[] array = sortMethod.randomInt(length, UnequalConversion.getBounds(length,multiplicity));
            // 拆分子任务多线程运行
            analysisTasks.add(new SortAnalysisTask(new SortCommand(sortMethod,progrees, array)));
        }
        // 增加10000w
        int[] array = sortMethod.randomInt(lastMax,UnequalConversion.getBounds(length,multiplicity));
        // 拆分子任务多线程运行
        analysisTasks.add(new SortAnalysisTask(new SortCommand(sortMethod,progrees, array)));
        invokeAll(analysisTasks);
        analysisTasks.forEach((a) -> {a.join();myCanvas.paint();});
    }


    public static double getMultiplicity() {
        return multiplicity;
    }

    public static void setMultiplicity(int multiplicity) {
        if (multiplicity >= Constant.Hundred){
            multiplicity = 100;
        }
        if (multiplicity <= 0){
            multiplicity = 0;
        }
        DoSortTask.multiplicity = (multiplicity + 0.00) / 100.00;
    }

    public static ConcurrentHashMap<String, Double[]> getCacheMap() {
        return SortCommand.getCacheMap();
    }
}
