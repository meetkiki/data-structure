package game;

import bean.BoardChess;
import bean.BoardData;
import bean.ChessStep;
import bean.Move;
import common.Bag;
import common.Constant;
import utils.BoardUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.RecursiveTask;

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
    public static void valid_moves(BoardChess chess,Bag<Byte> moves){
        byte[] bytes = chess.getChess();
        byte player = chess.getCurrMove();
        Bag<Byte> empty = chess.getEmpty();
        // 空位链表
        Iterator<Byte> byteIterator = empty.iterator();
        while (byteIterator.hasNext()){
            Byte cell = byteIterator.next();
            if (canFlips(bytes,cell,player)){
                // 移动链表
                moves.addFirst(cell);
            }
        }
    }

    /**
     * 获得行动力
     * */
    public static int valid_moves(BoardChess chess){
        byte[] bytes = chess.getChess();
        byte player = chess.getCurrMove();
        Bag<Byte> empty = chess.getEmpty();
        return valid_moves(bytes,empty,player);
    }

    /**
     * 获得行动力
     * */
    public static int valid_moves(byte[] bytes,Bag<Byte> empty,byte player){
        int canMove = 0;
        Iterator<Byte> byteIterator = empty.iterator();
        while (byteIterator.hasNext()) {
            Byte aByte = byteIterator.next();
            if (canFlips(bytes,aByte,player)){
                canMove++;
            }
        }
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
        int canMove = 0;
        initMoves(moves);
        byte[] bytes = chess.getChess();
        byte player = chess.getCurrMove();
        Bag<Byte> empty = chess.getEmpty();
        Iterator<Byte> byteIterator = empty.iterator();
        while (byteIterator.hasNext()){
            Byte aByte = byteIterator.next();
            if (canFlips(bytes, aByte,player)){
                Move move = BoardUtil.convertMove(aByte);
                moves[move.getRow()][move.getCol()] = true;
                canMove++;
            }
        }
        return canMove;
    }

    /**
     * 单方向搜索吃子
     * @return
     */
    private static Bag<Byte> singDirFlips(byte[] chess, byte dir, int cell, byte player){
        // 头结点
        Bag<Byte> node = new Bag<>();
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
                while (chess[pt] == opt){
                    // 吃子结点
                    node.addFirst((byte) pt);
                    // 建立父子关系
                    chess[pt] = player;
                    pt -= dir;
                }
            }
        }
        return node;
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
        Bag<ChessStep> steps = data.getSteps();
        Bag<Byte> empty = data.getEmpty();
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
    public static int make_move(byte[] chess,Bag<ChessStep> steps,Bag<Byte> empty, int cell, byte player){
        // 返回转变子
        int count = 0;
        //将row和col的值更改为player //玩家状态
        chess[cell] = player;
        // 转变链表 方便插入
        Bag<Byte> convert = new Bag<>();
        //遍历当前棋子 的周边棋子
        // 在八个方向试探 任意一个方向可以翻转对手就返回true
        for (int i = 0; i < DIRALL; i++) {
            int mask = 0x01 << i;
            if ((dirMask[cell] & mask) != 0){
                Bag<Byte> bag = singDirFlips(chess, dirInc[i], cell, player);
                count += bag.size();
                convert.addAll(bag);
            }
        }
        // 记录移动
        steps.addFirst(ChessStep.builder().cell((byte) cell).player(player).convert(convert).build());
        // 移除空链表
        empty.removeObj(new Byte((byte) cell));
        return count;
    }

    /**
     * 悔棋方法
     * 仅搜索和模拟
     */
    public static void un_move(BoardChess data){
        byte[] chess = data.getChess();
        Bag<ChessStep> steps = data.getSteps();
        ChessStep step = steps.pop();
        Bag<Byte> empty = data.getEmpty();
        un_move(chess,step,empty);
        data.setCurrMove(step.getPlayer());
    }

    /**
     * 悔棋方法
     * 仅搜索和模拟
     */
    public static void un_move(byte[] chess,ChessStep step,Bag<Byte> empty){
        byte player = step.getPlayer();
        byte other = BoardUtil.change(player);
        byte cell = step.getCell();
        Bag<Byte> convert = step.getConvert();
        if (convert.size() == 0){
            // 上一步为空手
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
        data.setCurrMove(BoardUtil.change(player));
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
            if (!moves[row][col]){
                throw new IllegalArgumentException("当前位置不可下棋!");
            }
            // 移除当前子的提示
            GameRule.removeHint(chess,nextmove);
            // 移除新的标志
            removeNew(chess);
            // 设置新子
            chess[row][col].setNewPlayer(nextmove);
            make_move(boardChess,BoardUtil.squareChess(move));
            Bag<ChessStep> steps = boardChess.getSteps();
            ChessStep first = steps.first();
            Bag<Byte> convert = first.getConvert();
            // 转变
            BoardUtil.converSion(first,chess);
            // 更新规则
            board.setCurrMove(BoardUtil.change(board.getCurrMove()));
            // 返回对手的可行步数
            return GameRule.valid_moves(board.getBoardData(), moves);
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
        Bag<Byte> empty = boardChess.getEmpty();
        if (empty.size() == 0){
            return true;
        }
        byte[] chess = boardChess.getChess();
        byte player = boardChess.getCurrMove();
        byte other = BoardUtil.change(player);
        // 如果双方无棋可走
        if (GameRule.valid_moves(chess,empty,player) == Constant.EMPTY
                && GameRule.valid_moves(chess,empty,other) == Constant.EMPTY){
            return true;
        }
        return false;
    }

}
