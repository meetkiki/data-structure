import view.AlgoVisualizer;

public class MiniApp {
    private static String InsertSort = "InsertSort";
    private static String SelectionSort = "SelectionSort";
    private static String BubbleSort = "BubbleSort";
    private static String BucketSort = "BucketSort";
    private static String CountingSort = "CountingSort";
    private static String HeapSort = "HeapSort";
    private static String MergeSort = "MergeSort";
    private static String QuickSort = "QuickSort";
    private static String ShellSort = "ShellSort";

    /**
     * 排序间隔
     */
    private static int DELAY = 1;

    public static void main(String[] args) {
        new AlgoVisualizer(MergeSort).setDelay(DELAY);
    }


}
