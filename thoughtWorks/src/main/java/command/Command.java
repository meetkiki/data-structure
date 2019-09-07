package command;

/**
 * @author ypt
 * 命令模式接口 抽象命令
 */
public interface Command {

    /**
     * 执行方法
     * @return  返回执行的结果
     */
    Object executeAlgorithmInteface(Object obj);

}