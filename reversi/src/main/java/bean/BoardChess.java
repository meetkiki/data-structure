package bean;

import common.Bag;
import common.Constant;
import game.Chess;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.BoardUtil;

import static common.Constant.MODEL;
import static common.Constant.SIZE;

/**
 * 计算用棋盘数据
 * @author Root
 */
@Data
public class BoardChess {
    /**
     * 棋盘共享数据
     */
    private byte[] chess;

    /**
     * 下一步棋子:白棋优先
     *  WHITE
     *  BLACK
     */
    private byte currMove;

    /**
     * 空位链表
     */
    private Bag<Byte> empty;

    public BoardChess(){
        this.chess = new byte[MODEL];
        this.currMove = Constant.WHITE;
        this.empty = new Bag<>();
    }

    public BoardChess(byte[] chess,byte player) {
        this.chess = new byte[MODEL];
        this.currMove = player;
        this.empty = new Bag<>();
        // 初始化哨兵
        this.initChess();
        // 拷贝棋盘
        System.arraycopy(chess,0,this.chess,0,MODEL);
        // 初始化空位链表
        this.initEmpty(chess);
    }

    public BoardChess(Chess[][] chess,byte player) {
        this.chess = new byte[MODEL];
        this.currMove = player;
        this.empty = new Bag<>();
        // 初始化哨兵
        this.initChess();
        // 转换棋盘数据
        this.copyChess(chess);
        // 初始化空位链表
        this.initEmpty(this.chess);
    }

    /**
     * 初始化空位链表
     */
    private void initEmpty(byte[] chess) {
        for (byte i = 0; i < MODEL; i++) {
            if (chess[i] == Constant.EMPTY || chess[i] == Constant.DOT_W || chess[i] == Constant.DOT_B){
                this.empty.add(i);
            }
        }
    }

    /**
     * 初始化棋盘数据
     */
    public void initChess() {
        for (byte i = 0; i < MODEL; i++) {
            // 前10个和后10个均为边界哨兵 // 中间71个位置 8x8 7个哨兵位
            if (i < 10 || i > 80 || i % 9 == 0){
                this.chess[i] = Constant.BOUNDARY;
            }
        }
    }

    /**
     * 初始化棋盘数据
     */
    public void copyChess(Chess[][] chess) {
        // 转换棋盘数据
        BoardUtil.convert(chess,this.chess);
    }
}