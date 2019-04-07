package miniapp.view.analysis;

import miniapp.abstraction.ICommand;
import miniapp.abstraction.SortMethod;
import miniapp.sortassert.SortAssert;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tao
 */
public class SortCommand<T> implements ICommand<T> {

    private final SortMethod target;
    /**
     * 线程安全HashMap
     */
    private static final ConcurrentHashMap<String,double[]> cacheMap = new ConcurrentHashMap<>();
    private int[] array;

    public SortCommand(SortMethod target, int[] array) {
        this.target = target;
        this.array = array;
    }

    @Override
    public T Execute() {
        long s = System.currentTimeMillis();
        target.sort(array);
        long e = System.currentTimeMillis();
        SortAssert.isTrue(target.isSorted(array),target.getCnName() + "失败!");
        double[] times = checkCache();
        int i = array.length / DoSortTask.increment;
        times[i] = Double.valueOf(e) - Double.valueOf(s);
//        times[i] = (e - s);
//        System.out.println(Arrays.toString(times));
        array = null;
        return (T)times;
    }

    private synchronized double[] checkCache() {
        double[] sortTimes = cacheMap.get(target.methodName());
        if (sortTimes == null){
            sortTimes = new double[DoSortTask.abscissa];
            cacheMap.put(target.methodName(),sortTimes);
        }
        return sortTimes;
    }

    public static ConcurrentHashMap<String, double[]> getCacheMap() {
        return cacheMap;
    }
}
