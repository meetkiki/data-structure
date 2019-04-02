package fork_join;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author ypt
 * @ClassName TestExecute
 * @Description TODO
 * @date 2019/4/2 15:58
 */
public class TestExecute {



    public static void main(String[] args) {
        int[] randomInt = SortArray.randomInt(100000000);
        int[] randomInt2 = randomInt.clone();
        SortArray sort = new SortArray(randomInt);

        // 执行一个任务
        ForkJoinPool forkjoinPool = new ForkJoinPool();
        RunTask task = new RunTask(sort);

        long s1 = System.currentTimeMillis();
        ForkJoinTask submit = forkjoinPool.submit(task);
        long s2 = 0;
        try {
            submit.get();
            s2 = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("排序 ===== "+ (sort.isSorted() ? "成功!":"失败!"));

        long s3 = System.currentTimeMillis();
        Arrays.sort(randomInt2);
        long s4 = System.currentTimeMillis();

        System.out.println("fork join sort "+(s2 - s1) + "ms!");
        System.out.println("Arrays sort "+(s4 - s3) + "ms!");

        //System.out.println(Arrays.toString(sort.getData()));

    }

}
