package utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 通用工具类
 * @author Tao
 */
public class CommonUtils {

    /**
     * 判断集合是否为空
     * @param collection 传入集合
     * @return
     */
    public static boolean isEmpty(Collection collection){
        return collection == null || collection == Collections.EMPTY_LIST || collection.isEmpty();
    }

    /**
     * 获取集合的第一个元素
     * @param collection    集合对象
     * @param <T>           目标类型
     * @return              findFirst
     */
    public static<T> T findFirst(Collection<T> collection){
        if (isEmpty(collection)){
            throw new IllegalArgumentException(" collection is empty , Can't get the first one !");
        }
        if (collection instanceof Deque){
            return ((Deque<T>)collection).getFirst();
        }
        if (collection instanceof List){
            return ((List<T>)collection).get(0);
        }
        return collection.iterator().next();
    }


    /**
     * 获取集合的最后一个元素
     * @param collection    集合对象
     * @param <T>           目标类型
     * @return              findLast
     */
    public static<T> T findLast(Collection<T> collection){
        if (isEmpty(collection)){
            throw new IllegalArgumentException(" collection is empty , Can't get the last one !");
        }
        if (collection instanceof Deque){
            return ((Deque<T>)collection).getLast();
        }
        if (collection instanceof List){
            return ((List<T>)collection).get(collection.size() - 1);
        }
        final Iterator<T> itr = collection.iterator();
        T lastElement = itr.next();
        while(itr.hasNext()) {
            lastElement=itr.next();
        }
        return lastElement;
    }


}
