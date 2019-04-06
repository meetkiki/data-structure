package miniapp.view.manoeuvre;

import miniapp.abstraction.ICommand;

/**
 * 环境角色
 * @author Tao
 */
public class Environment {

    /**
     * 维护一个目标对象
     */
    private ICommand command;

    private long start = 0;
    private long end = 0;
    public Environment(ICommand command){
        this.command = command;
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
        start = System.currentTimeMillis();
        command.Execute();
        end = System.currentTimeMillis();
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
