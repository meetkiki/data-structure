package game;

import bean.BoardData;
import common.Constant;

import java.util.List;

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
        List<byte[]> canMoves = data.getCanMoves();
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
                                if(isEmpty(x, y, chess[x][y]))
                                    break;
                                //如果棋盘上还有玩家可下的棋子时，将可移动数组设置为true，能走的步数自增
                                if(chess[x][y].getChess() == player){
                                    moves[row][col] = true;
                                    byte[] tem = new byte[2];
                                    tem[0] = row; tem[1] = col;
                                    canMoves.add(tem);
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
     * @param row
     * @param col
     * @param player
     */
    public static void make_move(BoardData data,byte row,byte col,byte player){
        Chess[][] chess = data.getChess();
        boolean[][] moves = data.getMoves();
        if (!moves[row][col]){
            throw new IllegalArgumentException("当前位置不可走!");
        }
        make_move(chess,row,col,player,true);
    }

    /**
     * 走棋方法,传入走棋坐标xy，走棋者
     *
     *  isShow 是否显示动画
     */
    public static void make_move(Chess[][] chess,byte row,byte col,byte player,boolean isShow){
        int rowdelta,coldelta,x,y;
        byte other = (player == Constant.WHITE) ? Constant.BLACK :Constant.WHITE;
        //将row和col的值更改为player //玩家状态
        chess[row][col].setNewPlayer(player);
        //遍历当前棋子
        for(rowdelta=-1;rowdelta<=1;++rowdelta){
            for(coldelta=-1;coldelta<=1;++coldelta){
                //如果大于边界，则跳出本次判断
                boolean border = isBorder(rowdelta, coldelta, row, col);
                if(border)
                    continue;
                //当走棋的位置为可行子的周围时执行下一步
                //【此步用于判断当前下棋者下子可行范围】
                if(chess [row + rowdelta][col + coldelta].getChess() == other){
                    x = row + rowdelta;
                    y = col + coldelta;
                    while(true){
                        x += rowdelta;
                        y += coldelta;
                        //如果x超出边界或者y超出边界或者棋盘为空时跳出循环
                        if(isEmpty(x, y, chess[x][y]))
                            break;
                        //如果数组中的可行位置为player时
                        if(chess[x][y].getChess() == player){
                            //循环吃子
                            while(chess[x -= rowdelta][y-=coldelta].getChess() == other){
                                // 是否执行动画
                                if (isShow) {
                                    chess[x][y].change(player);
                                } else {
                                    chess[x][y].setChess(player);
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    private static boolean isEmpty(int x, int y, Chess chess2) {
        return x < 0 || x >= SIZE || y < 0 || y >= SIZE || chess2.getChess() == Constant.EMPTY;
    }

    /**
     * /棋子统计方法
     */
    public int player_counters(Chess[][] chess,char player){
        int count = 0,row,col;
        for(row=0;row<SIZE;++row)
            for(col=0;col<SIZE;++col)
                if(chess[row][col].getChess() == player)
                    ++count;
        return count;
    }


}
