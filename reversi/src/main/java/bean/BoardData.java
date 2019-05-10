package bean;

import common.Constant;
import game.Chess;

import java.io.Serializable;

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
     * 下一步棋子:白棋优先
     *  WHITE
     *  BLACK
     */
    private byte currMove = Constant.WHITE;

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

    public byte getCurrMove() {
        return currMove;
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
        return currMove == Constant.WHITE ? Constant.DOT_W : Constant.DOT_B;
    }

    /**
     * 克隆棋盘
     * @return
     */
    public BoardData cloneData(){
        BoardData copyData = new BoardData();
        Chess[][] clone = new Chess[SIZE][SIZE];
        boolean[][] movesClone = new boolean[SIZE][SIZE];
        for(byte row=0;row<SIZE;++row){
            for(byte col=0;col<SIZE;++col) {
                clone[row][col] = chess[row][col].clone();
                movesClone[row][col] = moves[row][col];
            }
        }
        copyData.setChess(clone);
        copyData.setMoves(movesClone);
        copyData.setCurrMove(currMove);
        return copyData;
    }
}
