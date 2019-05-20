package bean;

import common.Bag;
import lombok.Builder;
import lombok.Data;

import java.util.LinkedList;

/**
 * 每步操作
 *  用于悔棋计算
 * @author Tao
 */
@Data
@Builder
public class ChessStep {
    /**
     * 落子 位置 对应Warren Smith模型
     */
    private byte cell;
    /**
     * 落子方
     */
    private byte player;
    /**
     * 转变子
     */
    private LinkedList<Byte> convert;

}
