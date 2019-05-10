package bean;

import common.Constant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static common.Constant.SIZE;

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
    private byte[][] chess = new byte[SIZE][SIZE];
    /**
     * 下一步棋子:白棋优先
     *  WHITE
     *  BLACK
     */
    private byte currMove = Constant.WHITE;


}