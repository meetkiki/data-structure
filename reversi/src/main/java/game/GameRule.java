package game;

import bean.BoardData;
import bean.Move;
import common.Constant;
import utils.BoardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

import static common.Constant.SIZE;

/**
 * @author Tao
 */
public class GameRule {

    /**
     * 获得行动力
     * */
    public static int valid_moves(BoardData data, byte player){
        Chess[][] chess = data.getChess();
        boolean[][] moves = data.getMoves();
        return valid_moves(chess,moves,player);
    }


    public static int valid_moves(Chess[][] chess,boolean[][] moves,byte player){
        //定义五个参数，rowdelta和coldelta为边界+1-1,x和y为棋盘坐标
        //no_of_moves为累计不能走的棋子数
        int rowdelta,coldelta,x,y,no_of_moves = 0;
        //定义棋盘的行列row和col
        byte row = 0,col = 0;
        //当前对手
        byte other = (player == Constant.WHITE) ? Constant.BLACK :Constant.WHITE;
        for(row=0;row<SIZE;++row)
            for(col=0;col<SIZE;++col)
                moves[row][col] = false;
        for(row=0;row<SIZE;row++){
            for(col=0;col<SIZE;col++){
                // 如果棋盘不为空，则跳出当层循环
                byte curr = chess[row][col].getChess();
                if(curr != Constant.EMPTY && curr != Constant.DOT_W && curr != Constant.DOT_B)
                    continue;
                //判断在一行列上或者斜线上的棋子是否可行[周围]
                for(rowdelta = -1;rowdelta<=1;++rowdelta){
                    for(coldelta=-1;coldelta<=1;++coldelta){
                        //如果走到边界时，则跳出本次判断
                        if(isBorder(rowdelta, coldelta, row, col))
                            continue;
                        //如果移动者的步数和棋盘周围相同时【存在】，允许下一步
                        if(chess[row + rowdelta][col + coldelta].getChess() == other){
                            x = row + rowdelta;
                            y = col + coldelta;
                            while(true){
                                x += rowdelta;
                                y += coldelta;
                                //如果x超出边界或者y超出边界或者棋盘为空时跳出循环
                                boolean empty = x < 0 || x >= SIZE || y < 0 || y >= SIZE || chess[x][y].getChess() == Constant.EMPTY;
                                if(empty)
                                    break;
                                //如果棋盘上还有玩家可下的棋子时，将可移动数组设置为true，能走的步数自增
                                if(chess[x][y].getChess() == player){
                                    moves[row][col] = true;
                                    no_of_moves++;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        //返回剩下能下的位置的no_of_moves的值
        return no_of_moves;
    }

    /**
     * 是否在边界
     * @param rowdelta
     * @param coldelta
     * @param row
     * @param col
     * @return
     */
    private static boolean isBorder(int rowdelta, int coldelta, byte row, byte col) {
        return (row == 0 && rowdelta == -1) || row + rowdelta >= SIZE || (col == 0 && coldelta == -1) || col + coldelta >= SIZE || (rowdelta == 0 && coldelta == 0);
    }


    /**
     * 走棋方法
     * @param data
     * @param move
     */
    public static MakeMoveRun getMakeMove(BoardData data,Move move){
        return new MakeMoveRun(data,move);
    }

    /**
     * 异步执行走棋
     */
    public static class MakeMoveRun extends RecursiveTask<List<Chess>> {

        private BoardData data;
        private Move move;

        public MakeMoveRun(BoardData data, Move move) {
            this.data = data;
            this.move = move;
        }

        @Override
        public List<Chess> compute() {
            Chess[][] chess = data.getChess();
            boolean[][] moves = data.getMoves();
            byte nextmove = data.getNextmove();
            if (!moves[move.getRow()][move.getCol()]){
                throw new IllegalArgumentException("当前位置不可走!");
            }
            // 移除当前子的提示
            GameRule.removeHint(data);
            // 移除新的标志
            removeNew(chess);
            List<Move> make_move = make_move(chess, move, nextmove, false);
            List<Chess> chessList = new ArrayList<>();
            for (Move mo : make_move) {
                byte ro = mo.getRow();
                byte co = mo.getCol();
                chessList.add(chess[ro][co]);
            }
            // 转变
            BoardUtil.converSion(data.getNextmove(),chessList);
            // 更新规则
            data.setNextmove(BoardUtil.change(data.getNextmove()));
            GameRule.valid_moves(data,data.getNextmove());
            return chessList;
        }
    }


    /**
     * 走棋方法,传入走棋坐标xy，走棋者
     *
     *  changes 吃子数组
     *  only    简洁操作
     */
    public static List<Move> make_move(Chess[][] chess, Move move, byte player, boolean only){
        byte rowdelta,coldelta,x,y;
        byte row = move.getRow();
        byte col = move.getCol();
        byte other = (player == Constant.WHITE) ? Constant.BLACK :Constant.WHITE;
        //将row和col的值更改为player //玩家状态
        List<Move> changes = null;
        if (only){
            chess[row][col].onlyChess(player);
        }else {
            changes = new ArrayList<>();
            chess[row][col].setNewPlayer(player);
        }
        //遍历当前棋子 的周边棋子
        for(rowdelta = -1;rowdelta <= 1; ++rowdelta){
            for(coldelta = -1;coldelta <= 1; ++coldelta){
                //如果大于边界，则跳出本次判断
                if(isBorder(rowdelta, coldelta, row, col))
                    continue;
                //当走棋的位置周边有对方子时下一步
                if(chess [row + rowdelta][col + coldelta].getChess() == other){
                    x = (byte) (row + rowdelta);
                    y = (byte) (col + coldelta);
                    while(true){
                        x += rowdelta;
                        y += coldelta;
                        //如果x超出边界或者y超出边界或者棋盘为空时跳出循环
                        boolean empty = x < 0 || x >= SIZE || y < 0 || y >= SIZE || chess[x][y].getChess() == Constant.EMPTY;
                        if(empty)
                            break;
                        //如果在这个连线上找到己方子 则吃掉中间子
                        if(chess[x][y].getChess() == player){
                            //循环吃子
                            while(chess[x -= rowdelta][y -= coldelta].getChess() == other){
                                // 是否执行动画
                                if (only) {
                                    chess[x][y].onlyChess(player);
                                } else {
                                    changes.add(new Move(x,y));
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
        return changes;
    }



    /**
     * 移除提示
     * @param boardChess
     */
    public static void removeHint(BoardData boardChess) {
        Chess[][] chess = boardChess.getChess();
        byte nextmove = boardChess.getNextmove();
        byte temp;
        if(nextmove == Constant.WHITE){
            temp = Constant.DOT_W;
        }else if(nextmove == Constant.BLACK){
            temp = Constant.DOT_B;
        }else return;
        for (int i = 0; i < chess.length; i++) {
            for (int j = 0; j < chess[i].length; j++) {
                if(chess[i][j].getChess() == temp){
                    chess[i][j].setChess(Constant.EMPTY);
                }
            }
        }
    }

    /**
     * 移除新下的提示
     */
    public static void removeNew(Chess[][] chess) {
        for (int i = 0; i < chess.length; i++) {
            for (int j = 0; j < chess[i].length; j++) {
                if(chess[i][j].isNewMove()){
                    byte ch = chess[i][j].getChess();
                    chess[i][j].setChess(ch);
                }
            }
        }
    }

    /**
     * 校验移动
     * @param data
     * @param player
     */
    public static boolean checkMove(BoardData data,Move move,byte player){
        Chess[][] chess = data.getChess();
        boolean[][] dataMoves = data.getMoves();
        int moves = valid_moves(chess, dataMoves, player);
        if (moves == 0){
            return false;
        }else {
            return dataMoves[move.getRow()][move.getCol()];
        }
    }

}
