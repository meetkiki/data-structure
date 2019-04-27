package bean;

import common.Constant;
import game.Chess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static common.Constant.SIZE;

/**
 * @author Tao
 */
public class BoardData implements Serializable {


    /**
     * 棋盘数组
     */
    private Chess[][] chess = new Chess[SIZE][SIZE];

    /**
     * 可以下棋位置
     */
    private boolean[][] moves = new boolean[SIZE][SIZE];

    /**
     * 可以下棋对
     */
    private List<Move> canMoves = new ArrayList<>();
    /**
     * 下一步棋子:白棋优先
     *  WHITE
     *  BLACK
     */
    private byte nextmove = Constant.WHITE;

    public Chess[][] getChess() {
        return chess;
    }

    public void setChess(Chess[][] chess) {
        this.chess = chess;
    }

    public boolean[][] getMoves() {
        return moves;
    }

    public void setMoves(boolean[][] moves) {
        this.moves = moves;
    }

    public byte getNextmove() {
        return nextmove;
    }

    public void setNextmove(byte nextmove) {
        this.nextmove = nextmove;
    }

    public List<Move> getCanMoves() {
        return canMoves;
    }

    public void setCanMoves(List<Move> canMoves) {
        this.canMoves = canMoves;
    }

    /**
     * 下一步棋子提示:白棋优先
     *  DOT_W
     *  DOT_B
     */
    public byte getCanMove() {
        return nextmove == Constant.WHITE ? Constant.DOT_W : Constant.DOT_B;
    }
}
