package miniapp.sort_algorithms.parallelsort.merge;

/**
 * @author Tao
 */
public class DataValue {
    /**
     * 数据项
     */
    public int[] data;
    /**
     * 临时数组
     */
    public int[] aux;
    /**
     * 左指针
     */
    public int l;
    /**
     * 右指针
     */
    public int r;
    /**
     * N路
     */
    public int n;

    public DataValue(int[] data) {
        this(data,data.clone(),0,data.length - 1);
    }

    public DataValue(int[] data, int l, int r) {
        this(data,null,l,r,0);
    }

    public DataValue(int[] data, int[] aux, int l, int r) {
        this(data,aux,l,r,0);
    }

    public DataValue(int[] data, int[] aux, int l, int r,int n) {
        this.data = data;
        this.aux = aux;
        this.l = l;
        this.r = r;
        this.n = n;
    }

    public void destroy(){
        this.data = null;
        this.aux = null;
    }
}
