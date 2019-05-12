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
        return valid_moves(chess,moves,data.getCurrMove());
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
    public static void valid_moves(BoardChess chess,Bag<Integer> moves){
        byte[] bytes = chess.getChess();
        byte player = chess.getCurrMove();
        for (int i = 0; i < MODEL; i++) {
            // 如果有子不可落子
            if (bytes[i] == Constant.WHITE || bytes[i] == Constant.BLACK || bytes[i] == Constant.BOUNDARY){
                continue;
            }
            if (canFlips(bytes, (byte) i,player)){
                moves.addFirst(i);
            }
        }
    }

    /**
     * 获得行动力
     * */
    public static int valid_moves(BoardChess chess){
        byte[] bytes = chess.getChess();
        byte player = chess.getCurrMove();
        return valid_moves(bytes,player);
    }

    /**
     * 获得行动力
     * */
    public static int valid_moves(byte[] bytes,byte player){
        int canMove = 0;
        for (int i = 0; i < bytes.length; i++) {
            if (canFlips(bytes, (byte) i,player)){
                canMove++;
            }
        }
        return canMove;
    }

    /**
     * 获得行动力
     * */
    public static int valid_moves(BoardChess chess,boolean[][] moves){
        int canMove = 0;
        byte[] bytes = chess.getChess();
        byte player = chess.getCurrMove();
        for (int i = 0; i < bytes.length; i++) {
            // 如果有子不可落子
            if (bytes[i] == Constant.WHITE || bytes[i] == Constant.BLACK || bytes[i] == Constant.BOUNDARY){
                continue;
            }
            if (canFlips(bytes, (byte) i,player)){
                Move move = BoardUtil.convertMove((byte) i);
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
        // 恢复原生空位
        chess[cell] = Constant.EMPTY;
        // 更新空位链表
        empty.addFirst(cell);
        // 还原棋子
        Bag<Byte> convert = step.getConvert();
        Iterator<Byte> conit = convert.iterator();
        while (conit.hasNext()){
            Byte next = conit.next();
            chess[next] = other;
        }
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
            try {
                Chess[][] chess = board.getChess();
                boolean[][] moves = board.getMoves();
                byte nextmove = board.getCurrMove();
                GameRule.valid_moves(board.getBoardData(),moves);
                if (!moves[move.getRow()][move.getCol()]){
                    throw new IllegalArgumentException("当前位置不可下棋!");
                }
                // 移除当前子的提示
                GameRule.removeHint(chess,nextmove);
                // 移除新的标志
                removeNew(chess);
                List<Move> make_move = make_move(chess, move, nextmove);
                List<Chess> chessList = new ArrayList<>();
                for (Move mo : make_move) {
                    byte ro = mo.getRow();
                    byte co = mo.getCol();
                    chessList.add(chess[ro][co]);
                }
                // 转变
                BoardUtil.converSion(board.getCurrMove(),chessList);
                // 更新规则
                board.setCurrMove(BoardUtil.change(board.getCurrMove()));
                // 返回对手的可行步数
                return GameRule.valid_moves(board.getBoardData(), moves);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 走棋方法,传入走棋坐标xy，走棋者
     * 显示视图
     *  changes 吃子数组
     *  only    简洁操作
     */
    public static List<Move> make_move(Chess[][] chess, Move move, byte player){
        byte rowdelta,coldelta,x,y,row = move.getRow(),col = move.getCol();
        byte other = (player == Constant.WHITE) ? Constant.BLACK :Constant.WHITE;
        //将row和col的值更改为player //玩家状态
        List<Move> changes = new ArrayList<>();
        chess[row][col].setNewPlayer(player);
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
                                chess[x][y].onlyChess(player);
                                // 是否执行动画
                                changes.add(new Move(x,y));
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

}
