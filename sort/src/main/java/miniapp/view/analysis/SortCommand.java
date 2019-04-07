package miniapp.view.analysis;

import miniapp.abstraction.ICommand;
import miniapp.abstraction.SortMethod;
import miniapp.sortassert.SortAssert;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tao
 */
public class SortCommand implements ICommand {

    private final SortMethod target;
    /**
     * 线程安全HashMap
     */
    private static final ConcurrentHashMap<String,long[]> cacheMap = new ConcurrentHashMap<>();
    private final int[] array;

    public SortCommand(SortMethod target, int[] array) {
        this.target = target;
        this.array = array;
    }

    @Override
    public void Execute() {
        long s = System.currentTimeMillis();
        target.sort(array);
        long e = System.currentTimeMillis();
        SortAssert.isTrue(target.isSorted(array),target.getCnName() + "失败!");
        long[] times = checkCache();
        int i = array.length / DoSortTask.increment;
        times[i - 1] = e - s;
    }

    private synchronized long[] checkCache() {
        long[] sortTimes = cacheMap.get(target.methodName());
        if (sortTimes == null){
            sortTimes = new long[DoSortTask.abscissa];
            cacheMap.put(target.methodName(),sortTimes);
        }
        return sortTimes;
    }

    public static ConcurrentHashMap<String, long[]> getCacheMap() {
        return cacheMap;
    }
}
