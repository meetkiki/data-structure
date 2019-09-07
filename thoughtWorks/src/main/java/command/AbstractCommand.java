package command;


import core.AbstractGraph;
import org.junit.Assert;

/**
 * 抽象命令类
 */
public abstract class AbstractCommand implements Command {

    /**
     * 执行运算的图
     * @see AbstractGraph
     */
    protected final AbstractGraph graph;

    /**
     * 有参构造 必须要有图才能执行运算 所以这里抽象一层
     * @param graph 执行运算的图
     */
    public AbstractCommand(AbstractGraph graph) {
        Assert.assertNotNull(graph);
        this.graph = graph;
    }

}
