package miniapp.view.manoeuvre;

import miniapp.abstraction.ICommand;
import miniapp.abstraction.Sort;

/**
 * 排序渲染命令
 * @author Tao
 */
public class SortFrameCommand<T> implements ICommand<T> {

    private Sort target;
    private AlgoFrame frame;


    public SortFrameCommand(Sort target, AlgoFrame frame) {
        this.target = target;
        this.frame = frame;
    }

    @Override
    public T Execute() {
        // 初始化区间
        frame.initOrdereds();
        target.sort(frame);
        // 排序空间
        frame.finish();
        frame.updateOrdereds(frame.length());
        return null;
    }
}
