package interactive;

import arithmetic.AlphaBeta;
import arithmetic.ReversiEvaluation;
import bean.BoardChess;
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
        this.ob.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        MouseListener mouseListener = (MouseListener) o;
        // 棋盘UI
        Board board = mouseListener.getBoard();
//        while (GameRule.valid_moves(board.getBoardData(),board.getMoves()) > 0 || GameRule.valid_moves(board.getBoardData().getBytes(), board.setCurrMove(BoardUtil.change(board.getCurrMove()))) > 0){
        // 棋盘数据
        Integer join = 0;
        BoardData boardData = board.getBoardData();
        BoardChess boardChess = boardData.getBoardChess();
        while (join == 0){
//            if (boardData.getCurrMove() == Constant.WHITE){
//                AlphaBeta.Depth = 3;
//            }else{
//                AlphaBeta.Depth = 4;
//            }
            long st = System.currentTimeMillis();
            MinimaxResult result = AlphaBeta.alphaBeta(boardChess);
            long en = System.currentTimeMillis();
            System.out.println("AlphaBeta 耗时 " + (en - st) + "ms");
            System.out.println(result + "Thread : " + Thread.currentThread().getName());
            // 必须要先走玩家棋
            mouseListener.getTask().join();

            // 走这步棋
            GameRule.MakeMoveRun makeMove = GameRule.getMakeMove(board, result.getMove());
            join = makeMove.fork().join();
            if (join == 0){
                System.out.println("计算机继续走棋");
            }
            board.upshow();
        }
        System.out.println("WHITE -- " + ReversiEvaluation.player_counters(boardChess.getChess(), Constant.WHITE));
        System.out.println("BLACK -- " + ReversiEvaluation.player_counters(boardChess.getChess(), Constant.BLACK));
//        }
    }
}
