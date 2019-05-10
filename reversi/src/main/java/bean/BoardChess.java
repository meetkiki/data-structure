package bean;

import common.Constant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 计算用棋盘数据
 * @author Root
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardChess {
    /**
     * 棋盘共享数据
     */
    private byte[][] chess;
    /**
     * 下一步棋子:白棋优先
     *  WHITE
     *  BLACK
     */
    private byte currMove = Constant.WHITE;
}