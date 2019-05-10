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
     * 棋盘共享数据
     */
    private BoardChess boardChess = new BoardChess();

    public Chess[][] getChess() {
        return chess;
    }

    public byte[][] getBytes() {
        byte[][] chess = boardChess.getChess();
        for(byte row=0;row<SIZE;++row)
            for(byte col=0;col<SIZE;++col)
                chess[row][col] = this.chess[row][col].getChess();
        return chess;
    }

    public void setChess(Chess[][] chess) {
        this.chess = chess;
    }

    public byte getCurrMove() {
        return this.boardChess.getCurrMove();
    }

    public void setCurrMove(byte currMove) {
        this.boardChess.setCurrMove(currMove);
    }

    /**
     * 下一步棋子提示:白棋优先
     *  DOT_W
     *  DOT_B
     */
    public byte getCanMove() {
        return this.boardChess.getCurrMove() == Constant.WHITE ? Constant.DOT_W : Constant.DOT_B;
    }

    /**
     * 克隆棋盘
     * @return
     */
    public BoardChess cloneChess(){
        byte[][] clone = new byte[SIZE][SIZE];
        for(byte row=0;row<SIZE;++row){
            for(byte col=0;col<SIZE;++col) {
                clone[row][col] = this.chess[row][col].getChess();
            }
        }
        return BoardChess.builder().chess(clone).currMove(this.boardChess.getCurrMove()).build();
    }
}
