package miniapp.view.manoeuvre;

import miniapp.abstraction.ICommand;
import miniapp.abstraction.Sort;

/**
 * 排序渲染命令
 */
public class SortFrameCommand implements ICommand {

    private Sort target;
    private AlgoFrame frame;


    public SortFrameCommand(Sort target, AlgoFrame frame) {
        this.target = target;
        this.frame = frame;
    }

    @Override
    public void Execute() {
        // 初始化区间
        frame.updateOrdereds(0);
        target.sort(frame);
        // 排序空间
        frame.updateOrdereds(frame.length());
    }
}
