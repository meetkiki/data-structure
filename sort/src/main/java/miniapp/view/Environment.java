package miniapp.view;

import miniapp.abstraction.Sort;

/**
 * 环境角色
 */
public class Environment {

    /**
     * 维护一个目标对象
     */
    private Sort target;
    private AlgoFrame frame;

    private long start = 0;
    private long end = 0;
    public Environment(Sort sortMethod, AlgoFrame frame){
        this.target = sortMethod;
        this.frame = frame;
    }

    private void init(){
        start = 0;
        end = 0;
    }

    /**
     * 执行排序方法
     */
    public void invoke(){
        // 初始化
        init();
        frame.updateOrdereds(0);
        start = System.currentTimeMillis();
        target.sort(frame);
        end = System.currentTimeMillis();
        assert target.isSorted(frame.getData());
        // 排序空间
        frame.updateOrdereds(frame.length());
    }

    /**
     * 获取执行时间 ms
     *
     * @return
     */
    private long getTime() {
        return System.currentTimeMillis() - start;
    }

    /**
     * 获取总共执行时间 ms
     *
     * @return
     */
    public long takeTime() {
        if (end == 0){
//            throw new RuntimeException("this algorithms is Running!");
            return getTime();
        }
        return end - start;
    }
}
