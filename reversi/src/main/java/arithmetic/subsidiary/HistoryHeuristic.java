package arithmetic.subsidiary;

import utils.BoardUtil;

import java.util.List;

import static common.Constant.ALLSTEP;
import static utils.CacheContext.movesIndex;

/**
 * @author ypt
 * @ClassName HistoryHeuristic 历史启发
 * @date 2019/5/27 10:32
 */
public class HistoryHeuristic {

    /**
     * 历史得分 线程安全
     */
    private static final ThreadLocal<int[]> historytable = ThreadLocal.withInitial(() -> new int[ALLSTEP]);

    /**
     * 给定move得到历史得分
     * @param move
     * @return
     */
    public static int historyScore(byte move){
        return historytable.get()[movesIndex[move]];
    }


    /**
     * 将最佳走法存入history 深度越高越有效
     * depth 支持最大深度为31
     * @param move
     * @return
     */
    public static void setHistoryScore(byte move,int depth){
        historytable.get()[movesIndex[move]] += (2 << depth);
    }


    /**
     * 重置历史表
     */
    public static void resetHistory(){
        int[] history = historytable.get();
        for (int i = 0; i < history.length; i++) {
            history[i] = 0;
        }
    }

    /**
     * 根据历史表信息对移动进行排序
     */
    public static void sortMovesByHistory(List<Byte> moves){
        // 根据比分倒序
        BoardUtil.insertSort(moves,(o1,o2)-> historyScore(o2) - historyScore(o1));

//        // 用long存储 步数 低32位为分数 高33-40位为移动
//        for (int i = 0; i < moves.size(); i++) {
//            Byte cell = moves.get(i);
//            int score = historyScore(cell);
//            Long shift = BoardUtil.leftShift((long) cell, (byte) 32);
//            shift |= score;
//            longs.add(i,shift);
//        }
//        // 根据比分倒序
//        BoardUtil.insertSort(moves,(o1,o2)->{
//            long l1 = o1 & 0xFFFFFFFF;
//            long l2 = o2 & 0xFFFFFFFF;
//            return (int) (l2 - l1);
//        });
//        for (int i = 0; i < longs.size(); i++) {
//            Long aLong = longs.get(i);
//            byte cell = BoardUtil.rightShift(aLong, (byte) 32);
//            moves.set(i,cell);
//        }
//        longs.clear();
    }

}
