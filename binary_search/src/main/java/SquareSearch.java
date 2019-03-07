public class SquareSearch {


    /**
     * 基于二分法完成求算数平方根
     *  思路和二分查询类似
     *      1.利用二分法求一个mid值位于min和max之间
     *      2.判断一个数是x的平方根方法
     *          若一个数的(mid + 0.000001) * (mid + 0.000001) > x 且
     *          (mid - 0.000001) * (mid - 0.000001) < x 根据介值定理
     *          可知mid即为求解值
     * @return
     */
    public double square(double n){
        double l = 0.00,r = n;
        while (true){
            double mid = l + ((r - l)/2);
            if ((mid - 0.000001) * (mid - 0.000001) < n && (mid + 0.000001) * (mid + 0.000001) < n){
                l = mid + 0.00001;
            }else if((mid - 0.000001) * (mid - 0.000001) > n && (mid + 0.000001) * (mid + 0.000001) > n){
                r = mid - 0.00001;
            }else{
                return mid;
            }
        }

    }



    public static void main(String[] args) {
        double square = new SquareSearch().square(2.00);
        System.out.println(square);


        System.out.println(square*square);
    }



}
