package bean;

import common.Bag;
import common.Constant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static common.Constant.MODEL;
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
    private byte[] chess = new byte[MODEL];
    /**
     * 下一步棋子:白棋优先
     *  WHITE
     *  BLACK
     */
    private byte currMove = Constant.WHITE;

    /**
     * 空位链表
     */
    private Bag<Byte> empty = new Bag<>();

    /**
     * 获取位置棋子
     *  0 <= row <= 7
     *  0 <= col <= 7
     * @return
     */
    public byte square(byte row, byte col){
        return chess[10 + col + row * 9];
    }

    /**
     * 获取位置棋子
     *  0 <= row <= 7
     *  0 <= col <= 7
     * @return
     */
    public Move move(byte cell){
        if (chess[cell] == Constant.BOUNDARY){
            return null;
        }
        byte row = (byte) ((cell - 10) / 9);
        byte col = (byte) ((cell - 10) % 9);
        return Move.builder().row(row).col(col).build();
    }
}