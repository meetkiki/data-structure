package arithmetic;

import game.Chess;

import static common.Constant.SIZE;

/**
 * @author ypt
 * @ClassName Evaluation
 * @Description TODO
 * @date 2019/5/8 13:58
 */
public class Evaluation {

    /**
     * //每一个棋子的权重
     */
    private final static int[][] evaluation = {
            {90, -60, 10, 10, 10, 10, -60, 90},
            {-60, -80, 5, 5, 5, 5, -80, -60},
            {10, 5, 1, 1, 1, 1, 5, 10},
            {10, 5, 1, 1, 1, 1, 5, 10},
            {10, 5, 1, 1, 1, 1, 5, 10},
            {10, 5, 1, 1, 1, 1, 5, 10},
            {-60, -80, 5, 5, 5, 5, -80, -60},
            {90, -60, 10, 10, 10, 10, -60, 90}};

    /**
     * 位置估值
     * @param chess
     * @param player
     * @return
     */
    public static int evaluation(Chess[][] chess, byte player){
        int count = 0,row,col;
        for(row=0;row<SIZE;++row)
            for(col=0;col<SIZE;++col)
                if(chess[row][col].getChess() == player)
                    count += evaluation[row][col];
        return count;
    }


    /**
     * /棋子统计方法
     */
    public static int player_counters(Chess[][] chess, byte player){
        int count = 0,row,col;
        for(row=0;row<SIZE;++row)
            for(col=0;col<SIZE;++col)
                if(chess[row][col].getChess() == player)
                    ++count;
        return count;
    }

}
