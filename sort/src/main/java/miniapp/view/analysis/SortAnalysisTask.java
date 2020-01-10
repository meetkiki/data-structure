package miniapp.view.analysis;

import miniapp.abstraction.ICommand;

import java.util.concurrent.RecursiveTask;

/**
 * 排序Action
 * @author Tao
 */
public class SortAnalysisTask extends RecursiveTask {

    private final ICommand command;

    public SortAnalysisTask(ICommand command){
        this.command = command;
    }

    @Override
    protected Object compute() {
        return command.Execute();
    }

}
