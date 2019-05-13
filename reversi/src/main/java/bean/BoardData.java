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
     * 下一步棋子:黑棋优先
     *  WHITE
     *  BLACK
     */
    private byte currMove = Constant.BLACK;
    /**
     * 一维棋盘
     */
    private BoardChess boardChess;

    public void setBoardChess(BoardChess boardChess) {
        this.boardChess = boardChess;
    }

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
        this.boardChess.setCurrMove(currMove);
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
    public BoardChess getBoardChess(){
        return boardChess;
    }
}
