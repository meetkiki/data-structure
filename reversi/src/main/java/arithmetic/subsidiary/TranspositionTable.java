package arithmetic.subsidiary;

import bean.BoardChess;
import bean.MinimaxResult;
import common.Constant;
import common.EntryType;

import java.util.List;
import java.util.Random;

import static common.Constant.PLAYERTYPE;
import static utils.CacheContext.moves;

/**
 * @author ypt
 * @ClassName TranspositionTable 置换表
 * @Description Zobrist哈希
 * @date 2019/5/13 17:10
 */
public final class TranspositionTable {

    /**
     * 线程安全的置换表
     */
    public static final ThreadLocal<long[][]> zobrist = ThreadLocal.withInitial(() -> new long[0][]);

    /**
     * 哈希表的大小 1 << 24 大约为64m
     */
    public static final int hashMask = (1 << 24) - 1;

    /**
     * 哈希表的大小 1 << 24 大约为64m
     */
    public static final MinimaxResult[] entryList = new MinimaxResult[hashMask + 1];

    static {
        Random random = new Random();
        zobrist.set(new long[Constant.MODEL + 1][]);
        for (int cell = 0; cell < Constant.MODEL + 1; cell++) {
            zobrist.get()[cell] = new long[PLAYERTYPE];
            for (byte i = 0; i < PLAYERTYPE; i++) {
                zobrist.get()[cell][i] = random.nextLong();
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
        for (byte cell : moves) {
            if (chess[cell] != Constant.EMPTY){
                byte player = chess[cell];
                hash ^= zobrist.get()[cell][player];
            }
        }
        // 局面当前棋手
        byte player = boardChess.getCurrMove();
        if (player == Constant.WHITE){
            hash ^= TranspositionTable.zobrist.get()[Constant.MODEL][player];
        }
        return hash;
    }

    /**
     * 根据哈希值获取局面信息
     * @param hash
     * @return
     */
    public static MinimaxResult lookupTTentryByZobrist(long hash,int depth){
        MinimaxResult result = entryList[(int) (hash&hashMask)];
        // 深度越深,depth越大,result得到的估值越准确,即需要查找的深度不能大于存储的深度
        if (result != null && depth <= result.getDepth()){
            return result;
        }
        return null;
    }

    /**
     * 重置Zobrist置换表
     */
    public static void resetZobrist(){
        // 清楚置换表主要是为了增加垃圾回收 上一次的搜索结果对下次搜索作用较小
        for (int i = 0; i < entryList.length; i++) {
            entryList[i] = null;
        }
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
        int bucket = (int) (zobrist & hashMask);
        MinimaxResult oldresult = entryList[bucket];
        if (oldresult != null){
            // 因为result得到的depth越大 说明越准确
            int oldDepth = oldresult.getDepth();
            if (result.getDepth() >= oldDepth){
                entryList[bucket] = result;
            }
        }else{
            entryList[bucket] = result;
        }
    }

    /**
     * 改变选手  只需要计算一次就可以
     * @return
     */
    public static long passPlayer(BoardChess boardChess,byte player){
        long hash = boardChess.getZobrist();
        if (player == Constant.WHITE){
            hash ^= TranspositionTable.zobrist.get()[Constant.MODEL][player];
        }
        return hash;
    }

    /**
     * 增加/删除一个棋子的哈希值
     * @param boardChess
     * @param cell
     * @return
     */
    public static long changeMove(BoardChess boardChess,int cell,byte player){
        long hash = boardChess.getZobrist();
        hash ^= TranspositionTable.zobrist.get()[cell][player];
        return hash;
    }

    /**
     * 将other转变一批子为player
     *  1.将other的子移除
     *  2.为player这批子计算hash值 返回
     * @param boardChess
     * @return
     */
    public static long changeConvert(BoardChess boardChess, List<Byte> convert, byte player, byte other){
        long hash = boardChess.getZobrist();
        for (Byte cell : convert) {
            hash ^= TranspositionTable.zobrist.get()[cell][other];
            hash ^= TranspositionTable.zobrist.get()[cell][player];
        }
        return hash;
    }
}
