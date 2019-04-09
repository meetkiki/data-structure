package miniapp.view.analysis;

import miniapp.abstraction.ICommand;
import miniapp.abstraction.SortMethod;
import miniapp.sortassert.SortAssert;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tao
 */
public class SortCommand<T> implements ICommand<T> {

    private SortMethod target;
    private final ProgressBarPanel progrees;
    /**
     * 线程安全HashMap
     */
    private static final ConcurrentHashMap<String,Double[]> cacheMap = new ConcurrentHashMap<>();
    private int[] array;

    public SortCommand(SortMethod target,ProgressBarPanel progrees, int[] array) {
        this.target = target;
        this.array = array;
        this.progrees = progrees;
    }

    @Override
    public T Execute() {
        long s = System.currentTimeMillis();
        target.sort(array);
        long e = System.currentTimeMillis();
        SortAssert.isTrue(target.isSorted(array),target.getCnName() + "失败!");
        Double[] times = checkCache();
        int i = array.length / DoSortTask.increment > DoSortTask.abscissa ? DoSortTask.abscissa - 1 : array.length / DoSortTask.increment;
        times[i] = Double.valueOf(e) - Double.valueOf(s);
//        System.out.println(Arrays.toString(times));
        progrees.updateBar(target.methodName(),times);
        distory();
        return (T)times;
    }

    private synchronized Double[] checkCache() {
        Double[] sortTimes = cacheMap.get(target.methodName());
        if (sortTimes == null){
            sortTimes = new Double[DoSortTask.abscissa];
            cacheMap.put(target.methodName(),sortTimes);
        }
        return sortTimes;
    }

    public void distory(){
        this.target.destory();
        this.target = null;
        this.array = null;
    }

    public static ConcurrentHashMap<String, Double[]> getCacheMap() {
        return cacheMap;
    }
}
