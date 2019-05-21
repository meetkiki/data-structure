package game;

import bean.BoardChess;
import bean.BoardData;
import bean.ChessStep;
import bean.Move;
import common.Bag;
import common.Constant;
import utils.BoardUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

import static common.Constant.DELAY;
import static common.Constant.DIRALL;
import static common.Constant.MODEL;
import static common.Constant.SIZE;
import static common.Constant.dirInc;
import static common.Constant.dirMask;

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
     * */
    public static void valid_moves(BoardChess chess, LinkedList<Integer> moves){
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
                byte mobility = moveCanMobility(chess, player, cell);
                shift |= mobility;
                // 移动链表
                moves.addFirst(shift);
                canOut ++;
            }
            if (canFlips(bytes,cell,other)){
                otherMove++;
            }
        }
        chess.setNextMobility(canOut);
        chess.setOtherMobility(otherMove);
    }

    /**
     * 基于行动力的估值
     *  head 空位链表
     * @return
     */
    private static byte moveCanMobility(BoardChess boardChess, byte player,byte next){
        make_move(boardChess,next);
        LinkedList<Byte> empty = boardChess.getEmpty();
        byte mobility = 0;
        Iterator<Byte> em = empty.iterator();
        while (em.hasNext()){
            Byte cell = em.next();
            if (GameRule.canFlips(boardChess.getChess(),cell,player)){
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
        chess.setNextMobility(canMove);
        chess.setOtherMobility(otherMove);
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
        chess.setNextMobility(playerMobility);
        chess.setOtherMobility(otherMobility);
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
        byte[] chess = data.getChess();
        byte player = data.getCurrMove();
        LinkedList<ChessStep> steps = data.getSteps();
        LinkedList<Byte> empty = data.getEmpty();
        int count = make_move(chess, steps, empty, cell, player);
        // 更新棋手
        data.setCurrMove(BoardUtil.change(player));
        return count;
    }

    /**
     * 走棋方法,传入走棋坐标xy，走棋者
     * 仅搜索和模拟
     *  changes 吃子数组
     *  only    简洁操作
     */
    public static int make_move(byte[] chess,LinkedList<ChessStep> steps,LinkedList<Byte> empty, int cell, byte player){
        // 返回转变子
        //将row和col的值更改为player //玩家状态
        chess[cell] = player;
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
        // 记录移动
        steps.addFirst(ChessStep.builder().cell((byte) cell).player(player).convert(convert).build());
        // 移除空链表
        empty.remove(new Byte((byte) cell));
        return convert.size();
    }

    /**
     * 悔棋方法
     * 仅搜索和模拟
     */
    public static void un_move(BoardChess data){
        byte[] chess = data.getChess();
        LinkedList<ChessStep> steps = data.getSteps();
        ChessStep step = steps.removeFirst();
        LinkedList<Byte> empty = data.getEmpty();
        un_move(chess,step,empty);
        data.setCurrMove(step.getPlayer());
    }

    /**
     * 悔棋方法
     * 仅搜索和模拟
     */
    public static void un_move(byte[] chess,ChessStep step,LinkedList<Byte> empty){
        byte player = step.getPlayer();
        byte other = BoardUtil.change(player);
        byte cell = step.getCell();
        LinkedList<Byte> convert = step.getConvert();
        // 如果是跳过
        if (convert.isEmpty()){
            return;
        }
        // 恢复原生空位
        chess[cell] = Constant.EMPTY;
        // 更新空位链表
        empty.addFirst(cell);
        // 还原棋子
        Iterator<Byte> conit = convert.iterator();
        while (conit.hasNext()){
            Byte next = conit.next();
            chess[next] = other;
        }
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
        data.setCurrMove(BoardUtil.change(player));
    }

    public static void passMove(BoardData boardData) {
        passMove(boardData.getBoardChess());
        boardData.setCurrMove(boardData.getBoardChess().getCurrMove());
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
            // 返回对手的可行步数
            int can = GameRule.valid_moves(board.getBoardData(), moves);
            // 异步更新页面
            GameContext.submit(()->{
                GameContext.await(latch);
                board.upshow();
            });
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
            // 悔棋到玩家可走为止
            byte next = board.getCurrMove();
            // 只能是final 这里暂时用数组替代
            CountDownLatch[] latch = new CountDownLatch[1];
            do {
                BoardData boardData = board.getBoardData();
                boolean[][] moves = board.getMoves();
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
                        // 转变
                        chess[cur.getRow()][cur.getCol()].setNewPlayer(nextmove);
                        // 转变子
                        other = first.getPlayer();
                    }
                }
                chessStep.setPlayer(other);
                latch[0] = BoardUtil.converSion(chessStep, chess, 20);
                // 更新规则
                board.setCurrMove(player);
                GameRule.valid_moves(boardData,moves);
            } while (board.getCurrMove() != next);
            // 异步更新页面
            GameContext.serialExecute(()->{
                GameContext.await(latch[0]);
                board.upshow();
            });
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
        if (boardChess.getNextMobility() == Constant.EMPTY
                && boardChess.getOtherMobility() == Constant.EMPTY){
            return true;
        }
        return false;
    }

}
