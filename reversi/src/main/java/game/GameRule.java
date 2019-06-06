package game;

import arithmetic.subsidiary.TranspositionTable;
import bean.BoardChess;
import bean.BoardData;
import bean.ChessStep;
import bean.Move;
import common.Constant;
import utils.BoardUtil;
import utils.CacheContext;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

import static common.Constant.DELAY;
import static common.Constant.DIRALL;
import static common.Constant.MODEL;
import static common.Constant.SIZE;
import static utils.CacheContext.dirInc;
import static utils.CacheContext.dirMask;

/**
 * @author Tao
 */
public class GameRule {


    /**
     * 获得行动力
     * */
    public static int valid_moves(BoardData data,boolean[][] moves){
        Chess[][] chess = data.getChess();
        BoardChess boardChess = data.getBoardChess();
        // 获取一维行动力
        return valid_moves(boardChess,moves);
    }



    /**
     * 单方向搜索判断是否可以走子
     * @return
     */
    private static boolean canSingDirFlips(byte[] chess, byte dir, byte cell,byte player){
        // 这个方向的棋子
        int pt = cell + dir;
        byte opt = BoardUtil.change(player);
        // 有对方棋子才可走子
        if (chess[pt] == opt){
            // 朝这个方向前进 直到遇到边界或者非对手子
            while (chess[pt] == opt){
                pt += dir;
            }
            return chess[pt] == player;
        }
        return false;
    }

    /**
     * 八方向搜索判断是否可以走子
     * @return
     */
    public static boolean canFlips(byte[] chess, byte cell, byte player){
        // 在八个方向试探 任意一个方向可以翻转对手就返回true
        for (int i = 0; i < DIRALL; i++) {
            // 八位 每一位的位运算 00000001 、00000010 、00000100 、00001000...
            // 分别对应方向数组 从右往左的值dirInc[i]
            int mask = 0x01 << i;
            if ((dirMask[cell] & mask) != 0){
                if (canSingDirFlips(chess,dirInc[i],cell,player)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获得行动力
     *  和对手行动力
     * */
    public static int valid_moves_mobility(BoardChess chess, LinkedList<Integer> moves){
        byte[] bytes = chess.getChess();
        byte player = chess.getCurrMove();
        LinkedList<Byte> empty = chess.getEmpty();
        byte other = BoardUtil.change(player);
        // 空位链表
        int canOut = 0,otherMove = 0;
        // 这里需要再循环中删除和增加 用fori
        for (int i = 0; i < empty.size(); i++) {
            Byte cell = empty.get(i);
            if (canFlips(bytes,cell,player)){
                // 左八位存cell 右八位为存走这步棋之后对手的行动力
                Integer shift = BoardUtil.leftShift(cell, Constant.BITVALUE);
                byte mobility = moveCanMobility(chess, cell);
                shift |= mobility;
                // 移动链表
                moves.addFirst(shift);
                canOut ++;
            }
            if (canFlips(bytes,cell,other)){
                otherMove++;
            }
        }
        chess.setOurMobility(canOut);
        chess.setOppMobility(otherMove);
        return canOut;
    }


    /**
     * 获得行动力
     * */
    public static int valid_moves(BoardChess chess, LinkedList<Byte> moves){
        byte[] bytes = chess.getChess();
        byte player = chess.getCurrMove();
        LinkedList<Byte> empty = chess.getEmpty();
        byte other = BoardUtil.change(player);
        // 空位链表
        int canOut = 0,otherMove = 0;
        // 这里需要再循环中删除和增加 用fori
        for (int i = 0; i < empty.size(); i++) {
            Byte cell = empty.get(i);
            if (canFlips(bytes,cell,player)){
                // 移动链表
                moves.addFirst(cell);
                canOut ++;
            }
            if (canFlips(bytes,cell,other)){
                otherMove++;
            }
        }
        chess.setOurMobility(canOut);
        chess.setOppMobility(otherMove);
        chess.updateStatus();
        return canOut;
    }

    /**
     * 基于行动力的估值
     *  head 空位链表
     * @return
     */
    private static byte moveCanMobility(BoardChess boardChess,byte next){
        make_move(boardChess,next);
        LinkedList<Byte> empty = boardChess.getEmpty();
        byte mobility = 0;
        Iterator<Byte> em = empty.iterator();
        while (em.hasNext()){
            Byte cell = em.next();
            if (GameRule.canFlips(boardChess.getChess(),cell,boardChess.getCurrMove())){
                mobility++;
            }
        }
        un_move(boardChess);
        return mobility;
    }

    /**
     * 获得行动力
     * */
    public static int valid_moves(BoardChess chess){
        int canMove = 0,otherMove = 0;
        byte[] bytes = chess.getChess();
        byte player = chess.getCurrMove();
        byte other = BoardUtil.change(player);
        LinkedList<Byte> empty = chess.getEmpty();
        Iterator<Byte> byteIterator = empty.iterator();
        while (byteIterator.hasNext()) {
            Byte aByte = byteIterator.next();
            if (canFlips(bytes,aByte,player)){
                canMove++;
            }
            if (canFlips(bytes,aByte,other)){
                otherMove++;
            }
        }
        chess.setOurMobility(canMove);
        chess.setOppMobility(otherMove);
        chess.updateStatus();
        return canMove;
    }

    public static void initMoves(boolean[][] moves){
        for (byte row = 0;row < SIZE;row++){
            for (byte col = 0;col < SIZE;col++){
                moves[row][col] = false;
            }
        }
    }

    /**
     * 获得行动力
     * */
    public static int valid_moves(BoardChess chess,boolean[][] moves){
        initMoves(moves);
        int playerMobility = 0,otherMobility = 0;
        byte[] bytes = chess.getChess();
        byte player = chess.getCurrMove();
        byte other = BoardUtil.change(player);
        LinkedList<Byte> empty = chess.getEmpty();
        Iterator<Byte> emptyIt = empty.iterator();
        while (emptyIt.hasNext()){
            Byte aByte = emptyIt.next();
            if (canFlips(bytes, aByte,player)){
                Move move = BoardUtil.convertMove(aByte);
                moves[move.getRow()][move.getCol()] = true;
                playerMobility++;
            }
            if (canFlips(bytes, aByte,other)){
                otherMobility++;
            }
        }
        // 设置行动力
        chess.setOurMobility(playerMobility);
        chess.setOppMobility(otherMobility);
        chess.updateStatus();
        return playerMobility;
    }

    /**
     * 单方向搜索吃子
     * @return
     */
    private static LinkedList<Byte> singDirFlips(byte[] chess, byte dir, int cell, byte player,LinkedList<Byte> convert){
        // 这个方向的棋子
        int pt = cell + dir;
        byte opt = BoardUtil.change(player);
        // 有对方棋子才可走子
        if (chess[pt] == opt){
            // 朝这个方向前进 直到遇到边界或者非对手子
            while (chess[pt] == opt){
                pt += dir;
            }
            // 如果是己方子 则返回吃子
            if (chess[pt] == player){
                pt -= dir;
                // 朝这个方向前进 直到遇到边界或者非对手子
                do {
                    // 更新子
                    chess[pt] = player;
                    // 吃子结点
                    convert.addFirst((byte) pt);
                    pt -= dir;
                } while (pt != cell);
            }
        }
        return convert;
    }

    /**
     * 走棋方法,传入走棋坐标xy，走棋者
     * 仅搜索和模拟
     *  changes 吃子数组
     *  only    简洁操作
     */
    public static int make_move(BoardChess data, int cell){
        byte player = data.getCurrMove();
        byte other = BoardUtil.change(player);
        LinkedList<ChessStep> steps = data.getSteps();
        LinkedList<Byte> empty = data.getEmpty();
        int count = make_move(data, steps, empty, cell, player);
        // 更新棋子数
        data.incrementCount(player,count+1).incrementCount(other,-count);
        // 更新棋手
        data.setCurrMove(other);
        return count;
    }

    /**
     * 走棋方法,传入走棋坐标xy，走棋者
     * 仅搜索和模拟
     *  changes 吃子数组
     *  only    简洁操作
     */
    public static int make_move(BoardChess data,LinkedList<ChessStep> steps,LinkedList<Byte> empty, int cell, byte player){
        byte[] chess = data.getChess();
        LinkedList<Byte> fields = data.getFields();
        // 返回转变子
        //将row和col的值更改为player //玩家状态
        chess[cell] = player;
        fields.add((byte) cell);
        // 转变链表 方便插入
        LinkedList<Byte> convert = new LinkedList<>();
        //遍历当前棋子 的周边棋子
        // 在八个方向试探 任意一个方向可以翻转对手就返回true
        for (int i = 0; i < DIRALL; i++) {
            int mask = 0x01 << i;
            if ((dirMask[cell] & mask) != 0){
                singDirFlips(chess, dirInc[i], cell, player,convert);
            }
        }
        // 移除空链表
        empty.remove(new Byte((byte) cell));
        // 更新哈希值
//        data.setZobrist(TranspositionTable.changeMove(data,cell,player));
        // 更新转变子哈希值
//        data.setZobrist(TranspositionTable.changeConvert(data,convert,player,data.getOther()));
        // 更新棋手哈希值
//        data.setZobrist(TranspositionTable.passPlayer(data,data.getCurrMove()));
        // 计算稳定子
        LinkedList<Byte> stators = sum_stators(data, (byte) cell);
        // 记录移动
        steps.addFirst(ChessStep.builder().cell((byte) cell).player(player).stators(stators).convert(convert).build());
        return convert.size();
    }

    /**
     * 计算稳定子
     */
    public static LinkedList<Byte> sum_stators(BoardChess data,byte cell){
        byte[] chess = data.getChess();
        LinkedList<Byte> stators = new LinkedList<>();
        // 绝对稳定子
        if (CacheContext.includeStabistor(cell)){
            stators.add(cell);
            data.addStators((byte) cell,chess[cell]);
        }
        // 如果双方都无绝对稳定子 则不用计算没有稳定子
        if (data.checkEmptyStators()) return stators;
        // 非空位
        LinkedList<Byte> fields = data.getFields();
        Iterator<Byte> fieldIt = fields.iterator();
        while (fieldIt.hasNext()){
            Byte next = fieldIt.next();
            // 校验该子是不是稳定子
            if (stators_check(next,data)){
                stators.add(next);
                data.addStators(next,chess[next]);
                fieldIt.remove();
            }
        }
        return stators;
    }

    /**
     * 校验稳定子方法
     *  1.稳定子必间接或者直接接触绝对稳定子
     *  2.稳定子八个方向不会被反转
     *  3.稳定子在接下来下棋过程中不会被反转
     * @param next
     * @param data
     * @return
     */
    private static boolean stators_check(Byte cell, BoardChess data) {
        Set<Byte> wstators = data.getwStators();
        Set<Byte> bstators = data.getbStators();
        byte[] chess = data.getChess();
        // 与敌我双方任何一个稳定子接触
        // 在八个方向试探 任意一个方向可以翻转就返回false
        return nearstators(cell,chess,wstators,bstators);
    }

    /**
     *  判断是否为稳定子
     * @param cell
     * @param wstators
     * @return
     */
    private static boolean nearstators(Byte cell, byte[] chess, Set<Byte> wstators, Set<Byte> bstators) {
        boolean flag = false;
        int i = 0;
        for (; i < DIRALL; i++) {
            // 八位 每一位的位运算 00000001 、00000010 、00000100 、00001000...
            // 分别对应方向数组 从右往左的值dirInc[i]
            int mask = 0x01 << i;
            if ((dirMask[cell] & mask) != 0){
                byte pt = (byte) (cell + dirInc[i]);
                if (wstators.contains((Byte) pt) || bstators.contains((Byte) pt)){
                    flag = true;break;
                }
            }
        }
        // 在八个方向试探 任意一个方向可以翻转就返回false
        if (flag){
            for (int j = 0; j < DIRALL; j++) {
                int mask = 0x01 << j; // 跳过稳定子方向
                if ((dirMask[cell] & mask) != 0 && j != i){
                    // 这里先直接判断这个方向有没有空格 这个可以优化为计算是否含有前沿子
                    if (singDirFlipstators(cell,chess,dirInc[i])){
                        return false;
                    }
                }
            }
            // 如果八个方向都没有空格 则可以确定这个是一个稳定子
            return true;
        }
        return flag;
    }

    /**
     * 判断这个方向上是否有empty成员
     * @param chess
     * @param b
     * @param empty
     * @return
     */
    private static boolean singDirFlipstators(Byte cell,byte[] chess, byte dir) {
        // 这个方向的棋子
        int pt = cell + dir;
        // 朝这个方向前进 直到边界或者空地
        do{
            pt += dir;
        } while (chess[pt] != Constant.EMPTY || chess[pt] != Constant.BOUNDARY);
        // 如果已到达边界
        if (chess[pt] == Constant.BOUNDARY){
            return false;
        }
        return true;
    }


    /**
     *  每次计算时统计
     * 内部子 前沿子
     */
    public static final void sum_inners_frontiers(BoardChess data){
        // 清空计算
        data.clearInnersFrontiers();
        LinkedList<Byte> fields = data.getFields();
        byte[] chess = data.getChess();
        Iterator<Byte> it = fields.iterator();
        while (it.hasNext()){
            Byte cell = it.next();
            // 判断是否是前沿子
            if (sum_inners_frontiers(data,cell)){
                data.addFrontiers(cell,chess[cell]);
            }
        }
    }

    /**
     * 判断该子是否为内部子
     * @param data
     * @param cell
     * @return
     */
    private static boolean sum_inners_frontiers(BoardChess data, Byte cell) {
        byte[] chess = data.getChess();
        boolean flag = true;
        for (int i = 0; i < DIRALL; i++) {
            // 八位 每一位的位运算 00000001 、00000010 、00000100 、00001000...
            // 分别对应方向数组 从右往左的值dirInc[i]
            int mask = 0x01 << i;
            if ((dirMask[cell] & mask) != 0){
                byte pt = (byte) (cell + dirInc[i]);
                // 如果有一个方向为空则为前沿子
                if (chess[pt] == Constant.EMPTY){
                    flag = false;
                    return true;
                }
            }
        }
        // 如果都不为空 则为内部子
        if (flag){
            data.addInners(cell,chess[cell]);
        }
        return false;
    }

    /**
     * 悔棋方法
     * 仅搜索和模拟
     */
    public static void un_move(BoardChess data){
        LinkedList<ChessStep> steps = data.getSteps();
        ChessStep step = steps.removeFirst();
        LinkedList<Byte> convert = step.getConvert();
        LinkedList<Byte> empty = data.getEmpty();
        un_move(data,step,empty);
        // 更新棋手
        data.setCurrMove(step.getPlayer());
        // 更新棋子数
        if (!convert.isEmpty()){
            byte player = step.getPlayer();
            byte other = BoardUtil.change(player);
            // 更新棋子数
            data.incrementCount(player,-(convert.size() + 1)).incrementCount(other,convert.size());
        }
    }

    /**
     * 悔棋方法
     * 仅搜索和模拟
     */
    public static void un_move(BoardChess data,ChessStep step,LinkedList<Byte> empty){
        byte[] chess = data.getChess();
        byte player = step.getPlayer();
        byte other = BoardUtil.change(player);
        byte cell = step.getCell();
        LinkedList<Byte> convert = step.getConvert();
        // 更新棋手
//        data.setZobrist(TranspositionTable.passPlayer(data,step.getPlayer()));
        // 如果是跳过
        if (convert.isEmpty()){
            return;
        }
        // 恢复原生空位
        chess[cell] = Constant.EMPTY;
        // 移除标志
        LinkedList<Byte> fields = data.getFields();
        fields.remove(new Byte((byte) cell));
        // 移除稳定子
        LinkedList<Byte> stators = step.getStators();
        if (!stators.isEmpty()){
            for (Byte stator : stators) {
                data.removeStators(stator,player);
            }
        }
        // 更新空位链表
        empty.addFirst(cell);
        // 还原棋子
        Iterator<Byte> conit = convert.iterator();
        while (conit.hasNext()){
            Byte next = conit.next();
            chess[next] = other;
        }
        // 移除空位哈希值
//        data.setZobrist(TranspositionTable.changeMove(data,cell,player));
        // 更新转变子哈希值 将下的子哈希值移除 增加对手的哈希值
//        data.setZobrist(TranspositionTable.changeConvert(data,convert,other,player));
    }

    /**
     * 跳过当前对手
     *  即无棋可走
     */
    public static void passMove(BoardChess data){
        byte player = data.getCurrMove();
        LinkedList<ChessStep> steps = data.getSteps();
        ChessStep step = ChessStep.builder().convert(new LinkedList<>()).player(player).build();
        steps.addFirst(step);
        byte other = BoardUtil.change(player);
        // 转变选手
        data.setCurrMove(other);
        // 更新哈希值
//        data.setZobrist(TranspositionTable.passPlayer(data,player));
    }

    public static void passMove(BoardData boardData) {
        passMove(boardData.getBoardChess());
        boardData.setCurrMove(boardData.getBoardChess().getCurrMove());
    }




    /**
     * 走棋方法
     * @param board
     * @param move
     */
    public static MakeMoveRun getMakeMove(Board board,Move move){
        boolean[][] moves = board.getMoves();
        GameRule.valid_moves(board.getBoardData(),moves);
        if (!moves[move.getRow()][move.getCol()]){
            throw new IllegalArgumentException("当前位置不可下棋!");
        }
        return new MakeMoveRun(board,move);
    }



    /**
     * 异步执行走棋
     */
    public static class MakeMoveRun extends RecursiveTask<Integer> {

        private Board board;
        private Move move;

        public MakeMoveRun(Board board, Move move) {
            this.board = board;
            this.move = move;
        }

        @Override
        public Integer compute() {
            int can = 0;
            try {
                BoardData boardData = board.getBoardData();
                byte nextmove = board.getCurrMove();
                boolean[][] moves = board.getMoves();
                Chess[][] chess = board.getChess();
                BoardChess boardChess = boardData.getBoardChess();
                GameRule.valid_moves(boardData,moves);
                byte row = move.getRow();
                byte col = move.getCol();
                // 移除当前子的提示
                GameRule.removeHint(chess,nextmove);
                // 移除新的标志
                removeNew(chess);
                // 设置新子
                chess[row][col].setNewPlayer(nextmove);
                make_move(boardChess,BoardUtil.squareChess(move));
                LinkedList<ChessStep> steps = boardChess.getSteps();
                ChessStep first = steps.getFirst();
                LinkedList<Byte> convert = first.getConvert();
                // 转变
                CountDownLatch latch = BoardUtil.converSion(first, chess, DELAY);
                // 更新规则
                board.setCurrMove(BoardUtil.change(board.getCurrMove()));
                can = GameRule.valid_moves(boardData,moves);
                // 异步更新页面
                GameContext.submit(()->{
                    GameContext.await(latch);
                    board.upshow();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return can;
        }
    }

    /**
     * 悔棋方法
     * @param board
     * @param move
     */
    public static UnMoveRun getUnMove(Board board){
        BoardData boardData = board.getBoardData();
        LinkedList<ChessStep> steps = boardData.getBoardChess().getSteps();
        if (steps.isEmpty()){
            throw new IllegalArgumentException("还未走棋 不可悔棋!");
        }
        return new UnMoveRun(board);
    }

    /**
     * 异步执行悔棋
     */
    public static class UnMoveRun extends RecursiveAction {

        private Board board;

        public UnMoveRun(Board board) {
            this.board = board;
        }

        @Override
        public void compute() {
            try {
                // 悔棋到玩家可走为止
                byte next = board.getCurrMove();
                // 只能是final 这里暂时用数组替代
                CountDownLatch[] latch = new CountDownLatch[1];
                BoardData boardData = board.getBoardData();
                boolean[][] moves = board.getMoves();
                do {
                    BoardChess boardChess = boardData.getBoardChess();
                    LinkedList<ChessStep> steps = boardChess.getSteps();
                    if (steps.isEmpty()) break;
                    byte nextmove = board.getCurrMove();
                    Chess[][] chess = board.getChess();
                    ChessStep chessStep = steps.getFirst();

                    // 移除当前子的提示
                    GameRule.removeHint(chess,nextmove);
                    // 移除新的标志
                    removeNew(chess);

                    // 执行悔棋操作
                    un_move(boardChess);

                    byte player = chessStep.getPlayer();
                    byte other = BoardUtil.change(player);
                    // 如果不是禁手
                    if (!chessStep.getConvert().isEmpty()){
                        Move move = BoardUtil.convertMove(chessStep.getCell());
                        // 恢复原生空位
                        chess[move.getRow()][move.getCol()].setChess(Constant.EMPTY);
                        // 转变子
                        LinkedList<Byte> convert = chessStep.getConvert();
                        // 获取上上步的棋手
                        if (!steps.isEmpty()){
                            // 还原棋子
                            ChessStep first = steps.getFirst();
                            // 设置新子
                            Move cur = BoardUtil.convertMove(first.getCell());
                            // 如果非禁手
                            if (cur != null){
                                // 转变
                                chess[cur.getRow()][cur.getCol()].setNewPlayer(nextmove);
                            }
                            // 转变子
                            other = first.getPlayer();
                        }
                    }
                    chessStep.setPlayer(other);
                    latch[0] = BoardUtil.converSion(chessStep, chess, 20);
                    // 更新规则
                    board.setCurrMove(boardChess.getCurrMove());
                } while (board.getCurrMove() != next && board.getBoardChess().getOurMobility() > 0);
                GameRule.valid_moves(boardData,moves);
                // 异步更新页面
                GameContext.serialExecute(()->{
                    GameContext.await(latch[0]);
                    board.upshow();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 移除提示
     * @param chess
     */
    public static void removeHint(Chess[][] chess,byte nextmove) {
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
                    chess[i][j].setNewMove(false);
                    byte ch = chess[i][j].getChess();
                    chess[i][j].setChess(ch);
                }
            }
        }
    }

    /**
     * 校验移动
     * @param data
     */
    public static boolean checkMove(Board data,Move move){
        BoardData boardData = data.getBoardData();
        boolean[][] dataMoves = data.getMoves();
        int moves = valid_moves(boardData, dataMoves);
        if (moves == 0){
            return false;
        }else {
            return dataMoves[move.getRow()][move.getCol()];
        }
    }

    /**
     * 是否结束
     * @return
     */
    public static boolean isShutDown(BoardChess boardChess){
        // 如果没有棋子可下
        LinkedList<Byte> empty = boardChess.getEmpty();
        if (empty.size() == 0){
            return true;
        }
        GameRule.valid_moves(boardChess);
        // 如果双方无棋可走
        if (boardChess.getOurMobility() == Constant.EMPTY
                && boardChess.getOppMobility() == Constant.EMPTY){
            return true;
        }
        return false;
    }


    /**
     * /棋子统计方法
     */
    public static int player_counters(byte[] chess, byte player){
        int count = 0;
        for (byte i = 0; i < MODEL; i++) {
            if (chess[i] == player){
                count ++;
            }
        }
        return count;
    }

}
