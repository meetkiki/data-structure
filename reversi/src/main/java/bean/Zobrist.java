package bean;

import common.Constant;
import common.EntryType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static common.Constant.PLAYERTYPE;

/**
 * @author ypt
 * @ClassName Zobrist
 * @Description Zobrist哈希
 * @date 2019/5/13 17:10
 */
public final class Zobrist {

    public static final long[][] zobrist;

    /**
     * 哈希表的大小 1 << 24 大约为64m
     */
    public static final int hashMask = 1 << 24;

    /**
     * 记录命中次数
     */
    private static int count = 0;

    /**
     * 哈希表的大小 1 << 24 大约为64m
     */
    public static final Map<Long,MinimaxResult> entryMap = new HashMap<>(hashMask);

    static {
        Random random = new Random();
        zobrist = new long[Constant.MODEL + 1][];
        for (int cell = 0; cell < Constant.MODEL + 1; cell++) {
            zobrist[cell] = new long[PLAYERTYPE];
            for (byte i = 0; i < PLAYERTYPE; i++) {
                zobrist[cell][i] = random.nextLong();
            }
        }
    }


    /**
     * 获得当前局面的Zobrist哈希值
     * @param boardChess
     * @return
     */
    public static long initZobrist(BoardChess boardChess) {
        long hash = 0;
        byte[] chess = boardChess.getChess();
        for (byte cell : Constant.moves) {
            byte player = chess[cell];
            int type = player == Constant.BLACK ? 2 : player;
            hash ^= zobrist[cell][type];
        }
        // 局面当前棋手
        byte player = boardChess.getCurrMove();
        hash ^= zobrist[Constant.MODEL][player == Constant.BLACK ? 2 : player];
        return hash;
    }

    /**
     * 根据哈希值获取局面信息
     * @param hash
     * @return
     */
    public static MinimaxResult lookupTTentryByZobrist(long hash,int depth){
        MinimaxResult result = entryMap.get(hash);
        // 深度越深,depth越大,result得到的估值越准确,即需要查找的深度不能大于存储的深度
        if (result != null && depth <= result.getDepth()){
            count++;
            return result;
        }
        return null;
    }

    /**
     * 重置Zobrist棋盘
     */
    public static void resetZobrist(){
        count= 0;
//        entryMap.clear();
    }

    /**
     * 写入置换表棋盘数据
     * @param value
     * @param depth
     * @param type
     */
    public static void insertZobrist(long zobrist, int value, int depth, EntryType type){
        insertZobrist(zobrist, MinimaxResult.builder().mark(value).type(type).depth(depth).build());
    }

    /**
     * 写入置换表棋盘数据
     * @param zobrist
     * @param result
     */
    public static void insertZobrist(long zobrist, MinimaxResult result){
        MinimaxResult oldresult = entryMap.get(zobrist);
        if (oldresult != null){
            // 因为result得到的depth越大 说明越准确
            int oldDepth = oldresult.getDepth();
            if (result.getDepth() >= oldDepth){
                entryMap.put(zobrist, result);
            }
        }else{
            entryMap.put(zobrist, result);
        }
    }

    public static int getCount() {
        return count;
    }
}
