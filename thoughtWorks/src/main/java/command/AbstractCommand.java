package command;


import core.AbstractGraph;

/**
 * 抽象命令类
 */
public class AbstractCommand implements Command {

    /**
     * 执行运算的图
     */
    protected final AbstractGraph graph;

    /**
     * 有参构造 必须要有图才能执行运算 所以这里抽象一层
     * @param graph 执行运算的图
     */
    public AbstractCommand(AbstractGraph graph) {
        this.graph = graph;
    }

    @Override
    public Object executeAlgorithmInteface(Object obj) {
        return null;
    }
}
