package command;

import common.Constant;
import core.AbstractGraph;
import org.junit.Assert;
import utils.StrUtils;

/**
 * @author ypt
 * Simple factory class to create an appropriate {@link Command} instance from a command line.
 */
public final class CommandContext {

    /**
     * 执行运算的图
     */
    private final AbstractGraph graph;
    /**
     * 执行策略
     */
    private Command command;

    /**
     * 构造方法
     * @param graph
     */
    public CommandContext(AbstractGraph graph) {
        this.graph = graph;
    }

    /**
     * 根据名称创建命令执行器
     * @param commandName
     * @return
     */
    public CommandContext CommandContext(String commandName) {
        Assert.assertFalse("commandName Can not be empty !",StrUtils.isBlank(commandName));
        commandName = commandName.trim().toLowerCase();
        switch (commandName){
            case Constant.DISTANCE:
                command = new DistanceCommand(graph);
                break;
            case Constant.SHORTEST:
                command = new ShortestCommand(graph);
                break;
            case Constant.TRIPS:
                command = new TripsCommand(graph);
                break;
            default:
                throw new IllegalArgumentException(" commandName Cannot be parsed !");
        }
        return this;
    }

    /**
     * 执行运算
     * @param objs      请求参数
     * @return
     */
    public Object execute(Object objs){
        Assert.assertNotNull("You should first instantiate the command! ",command);
        return command.executeAlgorithmInteface(objs);
    }

    public static CommandContext.CommandContextBuilder builder() {
        return new CommandContext.CommandContextBuilder();
    }

    public static final class CommandContextBuilder{
        /**
         * 执行运算的图
         */
        private AbstractGraph graph;

        public CommandContextBuilder withGraph(AbstractGraph graph){
            this.graph = graph;
            return this;
        }

        public CommandContext build(){
            if (graph == null){
                throw new IllegalStateException(" graph may not be null");
            }
            return new CommandContext(this.graph);
        }
    }

    public AbstractGraph getGraph() {
        return graph;
    }
}