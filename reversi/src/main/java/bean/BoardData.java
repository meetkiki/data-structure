package bean;

import common.Constant;
import game.Chess;

import static common.Constant.SIZE;

/**
 * @author Tao
 */
public class BoardData {

    /**
     * 棋盘数组
     */
    private Chess[][] chess = new Chess[SIZE][SIZE];

    /**
     * 下一步棋子:白棋优先
     *  WHITE
     *  BLACK
     */
    private byte currMove = Constant.BLACK;

    public Chess[][] getChess() {
        return chess;
    }

    public byte[] getBytes() {
        return new BoardChess(chess,currMove).getChess();
    }

    public byte getCurrMove() {
        return this.currMove;
    }

    public void setCurrMove(byte currMove) {
        this.currMove = currMove;
    }

    /**
     * 下一步棋子提示:白棋优先
     *  DOT_W
     *  DOT_B
     */
    public byte getCanMove() {
        return this.currMove == Constant.WHITE ? Constant.DOT_W : Constant.DOT_B;
    }

    /**
     * 克隆棋盘
     * @return
     */
    public BoardChess cloneChess(){
        return new BoardChess(this.chess,this.getCurrMove());
    }
}
