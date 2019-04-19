package miniapp.sort_algorithms.quicksort;

import miniapp.Enum.Constant;
import miniapp.Enum.LineColorEnum;
import miniapp.abstraction.SortMethod;

import java.util.Arrays;


/**
 * @author Tao
 */
public class DualPivotQuickSort implements SortMethod {

    @Override
    public String getCnName() {
        return "双轴快速排序";
    }

    @Override
    public LineColorEnum lineColor() {
        return LineColorEnum.Teal;
    }
    @Override
    public int[] sort(int[] arr) {
        int length = arr.length;
        dualPivotQuickSort(arr,0,length - 1);
        return arr;
    }

    @Override
    public String methodName() {
        return "DualPivotQuickSort";
    }

    /**
     * 双轴快速排序
     *
     *  1.对于很小的数组（长度小于27），会使用插入排序。
     *  2.选择两个点P1,P2作为轴心，比如我们可以使用第一个元素和最后一个元素。
     *  3.P1必须比P2要小，否则将这两个元素交换，现在将整个数组分为四部分：
     *  （1）第一部分：比P1小的元素。
     *  （2）第二部分：比P1大但是比P2小的元素。
     *  （3）第三部分：比P2大的元素。
     *  （4）第四部分：尚未比较的部分。
     *  在开始比较前，除了轴点，其余元素几乎都在第四部分，直到比较完之后第四部分没有元素。
     *  4.从第四部分选出一个元素a[K]，与两个轴心比较，然后放到第一二三部分中的一个。
     *  5.移动L，K，G指向。
     *  6.重复 4 5 步，直到第四部分没有元素。
     *  7.将P1与第一部分的最后一个元素交换。将P2与第三部分的第一个元素交换。
     *  8.递归的将第一二三部分排序。
     * @param arr
     * @param l
     * @param r
     */
    private void dualPivotQuickSort(int[] arr, int l, int r) {
        if (r - l < Constant.INSERTSIZE) {
            insertSort(arr,l,r);
            return;
        }
        // 保证pivot1 小于等于pivot2
        if (arr[l] > arr[r]){
            swap(arr,l,r);
        }
        // 排除相等情况
//        else if (arr[l] == arr[r]){
//            while (arr[l] == arr[r] && l < r){
//                l++;
//            }
//        }
//        // 类似于三路快排 有两个中枢 lt 左边小于pivot1 lt和gt之间大于pivot1小于pivot2 gt右边大于pivot2
        int i = l + 1,lt = l + 1,gt = r - 1,pivot1 = arr[l],pivot2 = arr[r];
        while (i <= gt){
            if (arr[i] < pivot1){
                swap(arr, i++, lt++);
            } else if (arr[i] > pivot2){
                swap(arr, i, gt--);
            } else{
                i++;
            }
        }

        swap(arr, l, --lt);
        swap(arr, r, ++gt);
        // 一次三向切分确定两个元素的位置 这两个元素将数组分为三份
        dualPivotQuickSort(arr, l,lt - 1);
        dualPivotQuickSort(arr,lt + 1,gt - 1);
        dualPivotQuickSort(arr,gt + 1,r);
    }

    @Override
    public void destory() {}


    public static void main(String[] args) {
//        int[] arr = {21,2,11,3,2,32,3,22,11,21};
        DualPivotQuickSort dualPivotQuickSort = new DualPivotQuickSort();
//        System.out.println(Arrays.toString(dualPivotQuickSort.sort(arr)));
        long sort = dualPivotQuickSort.testSort(10000000);
        System.out.println("DualPivotQuickSort 花费时间"+sort+"ms");
    }
}
