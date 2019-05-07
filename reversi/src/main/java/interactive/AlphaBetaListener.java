package interactive;

import arithmetic.AlphaBeta;
import bean.BoardData;
import bean.MinimaxResult;
import common.Constant;
import game.Board;
import game.GameRule;

import java.util.Observable;
import java.util.Observer;

/**
 * @author ypt
 * @ClassName AlphaBetaListener
 * @Description 观察者
 * @date 2019/5/7 10:14
 */
public class AlphaBetaListener implements Observer {

    private Observable ob;

    public AlphaBetaListener(Observable ob) {
        this.ob = ob;
        ob.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        MouseListener mouseListener = (MouseListener)o;
        // 模拟棋盘
        BoardData copyBoard = mouseListener.getCopyBoard();
        // 显示棋盘
        BoardData boardChess = mouseListener.getBoardChess();
        // 棋盘UI
        Board board = mouseListener.getBoard();
        MinimaxResult result = AlphaBeta.alpha_Beta(copyBoard);
        if (copyBoard.getNextmove() == Constant.BLACK){
            System.out.println(result);
            // 走这步棋
            GameRule.make_move(boardChess, result.getMove());
            GameRule.valid_moves(boardChess,boardChess.getNextmove());
            board.upshow();
        }
    }
}
