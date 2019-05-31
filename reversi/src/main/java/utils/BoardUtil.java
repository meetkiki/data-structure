package utils;

import bean.BoardChess;
import bean.ChessStep;
import bean.Move;
import common.Bag;
import common.Constant;
import common.ImageConstant;
import game.Chess;
import game.GameContext;
import game.GameRule;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import static common.Constant.SIZE;
import static utils.CacheContext.mapMove;
import static utils.CacheContext.moveConstant;

/**
 * @author Tao
 */
public class BoardUtil {


	/**
	 * 棋子动画
	 * 	//转变棋子动画
	 *
	 * 		1 --- 6
	 *     白     黑
	 * @param chess
	 * @param //repaint
	 */
	public static CountDownLatch converSion(ChessStep step, Chess[][] chess, int ms){
		// 通知线程完成 主线程里调用latch.await()方法
		CountDownLatch latch = new CountDownLatch(1);
		List<Chess> curr = new ArrayList<>();
		LinkedList<Byte> stepConvert = step.getConvert();
		for (Byte aByte : stepConvert) {
			Move move = BoardUtil.convertMove(aByte);
			curr.add(chess[move.getRow()][move.getCol()]);
		}
		byte player = step.getPlayer();
		// 优先修正棋子
		fixChess(player,curr);
		Timer timer = new Timer();
		//根据传参的正负判断转变的棋子方向 6 -> 1 表示黑变白
		int tem = player == Constant.WHITE ? 6 : 1;
		TimerRunTask task = new TimerRunTask(tem,player,curr,latch);
		timer.schedule(task,0,ms);
		return latch;
	}

	/**
	 * 反转棋子任务
	 */
	public static class TimerRunTask extends TimerTask{
		private int count;
		private byte chess;
		private List<Chess> curr;
		private CountDownLatch latch;

		public TimerRunTask(int count, byte chess, List<Chess> curr,CountDownLatch latch) {
			this.count = count;
			this.chess = chess;
			this.curr = curr;
			this.latch = latch;
		}

		@Override
		public void run() {
			if(!curr.isEmpty() && count > 0 && count <= 6){
				updateImg(count,curr);
				if(chess == Constant.WHITE) count--;
				else count++;
			}else{
				try {
					//结束任务
					cancel();
					//修正图标
					fixImg(chess,curr);
				} finally {
					latch.countDown();
				}
			}
		}
	}

	/**
	 * 设置翻转图片
	 * @param count
	 * @param chessList
	 */
	private static void updateImg(int count, List<Chess> chessList) {
		for (Chess curr : chessList) {
			String url = String.format(Constant.OVERTURN, count + "");
			Image image = GameContext.getResources().get(ImageConstant.valueOf(url)).getImage();
			curr.setImage(image);
			curr.repaint();
		}
	}
	/**
	 * 修正图标
	 * @param chess
	 * @param chessList
	 */
	private static void fixImg(byte chess, List<Chess> chessList) {
		for (Chess curr : chessList) {
			curr.setImgChess(chess);
			curr.repaint();
		}
	}
	/**
	 * 修正图标
	 * @param chess
	 * @param chessList
	 */
	private static void fixChess(byte chess, List<Chess> chessList) {
		for (Chess curr : chessList) {
			curr.onlyChess(chess);
		}
	}

	/**
	 *  /控制台显示棋盘
	 */
	public static void display(BoardChess data){
		System.out.println("===================chess==================");
		boolean[][] moves = new boolean[SIZE][SIZE];
		GameRule.valid_moves(data,moves);
		byte[] chess = data.getChess();
		byte player = data.getCurrMove();
		char col_label = 'a';
		//打印第一行的a-z字母标识
		byte col = 0,row=0;
		System.out.print("  ");
		for(col = 0;col<SIZE;++col)
			System.out.printf("   %c",(char)(col_label+col));
		System.out.println();
		//打印棋盘
		for(row=0;row<SIZE;++row){
			System.out.printf("   +");
			col = (byte) SIZE;
			while(col>0){
				System.out.printf("---+");--col;
			}
			//打印第一列【1-SIZE】的值
			System.out.printf("\n%2d |",row+1);
			for(col = 0;col<SIZE;++col){
				if(!moves[row][col]){
					byte bChess = chess[squareChess(row,col)];
					char cChess = ' ';
					switch (bChess){
						case Constant.WHITE: cChess = 'o';break;
						case Constant.BLACK: cChess = '*';break;
						case Constant.DOT_B: cChess = '.';break;
						case Constant.DOT_W: cChess = '\'';break;
						default:break;
					}
					System.out.printf(" %s |", cChess);
				}else{
					if (player == Constant.WHITE){
						System.out.print(" . |");
					}else{
						System.out.print(" ` |");
					}
				}
			}
			System.out.println();
		}
		System.out.printf("   +");
		for(col=0;col<SIZE;++col)
			System.out.printf("---+");
		System.out.println();
	}

	/**
	 *  /控制台显示棋盘
	 */
	public static void display(Chess[][] data,boolean[][] moves,byte player){
		System.out.println("===================chess==================");
		char col_label = 'a';
		//打印第一行的a-z字母标识
		byte col = 0,row=0;
		System.out.print("  ");
		for(col = 0;col<SIZE;++col)
			System.out.printf("   %c",(char)(col_label+col));
		System.out.println();
		//打印棋盘
		for(row=0;row<SIZE;++row){
			System.out.printf("   +");
			col = (byte) SIZE;
			while(col>0){
				System.out.printf("---+");--col;
			}
			//打印第一列【1-SIZE】的值
			System.out.printf("\n%2d |",row+1);
			for(col = 0;col<SIZE;++col){
				if(!moves[row][col]){
					byte bChess = data[row][col].getChess();
					char cChess = ' ';
					switch (bChess){
						case Constant.WHITE: cChess = 'o';break;
						case Constant.BLACK: cChess = '*';break;
						case Constant.DOT_B: cChess = '.';break;
						case Constant.DOT_W: cChess = '\'';break;
						default:break;
					}
					System.out.printf(" %s |", cChess);
				}else{
					if (player == Constant.WHITE){
						System.out.print(" . |");
					}else{
						System.out.print(" ` |");
					}
				}
			}
			System.out.println();
		}
		System.out.printf("   +");
		for(col=0;col<SIZE;++col)
			System.out.printf("---+");
		System.out.println();
	}

	/**
	 * 改变棋盘方向
	 * @param player
	 * @return
	 */
	public static byte change(byte player){
		if(player==Constant.WHITE){
			return Constant.BLACK;
		}else if(player==Constant.BLACK){
			return Constant.WHITE;
		}else if(player==Constant.DOT_W){
			return Constant.DOT_B;
		}else if(player==Constant.DOT_B){
			return Constant.DOT_W;
		}
		return Constant.EMPTY;
	}

	/**
	 * 获取位置棋子
	 *  0 <= row <= 7
	 *  0 <= col <= 7
	 * @return
	 */
	public static byte squareChess(byte row, byte col){
		return (byte) (10 + col + row * 9);
	}

	/**
	 * 获取位置棋子
	 *  0 <= row <= 7
	 *  0 <= col <= 7
	 * @return
	 */
	public static byte squareChess(Move move){
		return moveConstant[move.getRow()][move.getCol()];
	}


	/**
	 * 获取位置棋子
	 *  0 <= row <= 7
	 *  0 <= col <= 7
	 * @return
	 */
	public static Move convertMove(byte cell){
		// 排除边界
		return mapMove.get(cell);
	}

	/**
	 * 转化棋盘数据
	 * @param src
	 * @return
	 */
	public static void convert(Chess[][] src,byte[] desc){
		// 初始化棋子
		for(byte row=0;row<SIZE;row++)
			for(byte col=0;col<SIZE;col++)
				desc[BoardUtil.squareChess(row,col)] = src[row][col].getChess();
	}


	/**
	 * 获得棋子文字描述
	 * @return
	 */
	public static String getChessStr(byte player){
		return player == Constant.WHITE ? "白" : "黑";
	}

	/**
	 * 左移N位 int
	 * @return
	 */
	public static Integer leftShift(byte cell,byte n){
		return ((int)cell) << n;
	}

	/**
	 * 左移N位 long
	 * @return
	 */
	public static Long leftShift(long cell,byte n){
		return (cell) << n;
	}

	/**
	 * 右移N位 int
	 * @return
	 */
	public static byte rightShift(Integer cell,byte n){
		return (byte) (cell >> n);
	}


	/**
	 * 右移N位 long
	 * @return
	 */
	public static byte rightShift(Long cell,byte n){
		return (byte) (cell >> n);
	}


	/**
	 * 小数据
	 * 插入排序
	 */
	public static<T> void insertSort(List<T> list, Comparator<T> co){
		T temp;
		for (int i = 1; i < list.size(); i++) {
			int j = i - 1;
			temp = list.get(i);
			for (; j >= 0 && co.compare(temp,list.get(j)) < 0; j--) {
				// 选取temp放在该放的位置上 这里是temp小于arr[j]时将arr[j]右移
				list.set(j + 1,list.get(j));
			}
			list.set(j + 1,temp);
		}
	}


	/**
	 * 基因组转换
	 *  |x|必须要小于128
	 * @return
	 */
	public static void gensToGrays(byte[] gens,byte[] grays){
		for (int i = 0; i < gens.length; i++) {
			grays[i] = byteToGray(gens[i]);
		}
	}

	/**
	 * 基因组转换
	 *  |x|必须要小于128
	 * @return
	 */
	public static void graysToGens(byte[] grays,byte[] gens){
		for (int i = 0; i < grays.length; i++) {
			gens[i] = grayToByte(grays[i]);
		}
	}



	/**
	 * 二进制转化为格雷编码
	 *  |x|必须要小于128
	 * @return
	 */
	public static byte byteToGray(byte src){
		// 法则是保留二进制码的最高位作为格雷码的最高位，
		// 而次高位格雷码为二进制码的高位与次高位相异或，
		// 而格雷码其余各位与次高位的求法相类似
		byte abs = (byte) Math.abs(src),res = (byte) (abs ^ (abs >> 1));
		return (byte) (src > 0 ? res : -res);
	}


	/**
	 * 格雷编码转化为二进制
	 * 	|x|必须要小于128
	 * @return
	 */
	public static byte grayToByte(byte src){
		// 法则是保留格雷码的最高位作为自然二进制码的最高位，
		// 而次高位自然二进制码为高位自然二进制码与次高位格雷码相异或，
		// 而自然二进制码的其余各位与次高位自然二进制码的求法相类似
		byte abs = (byte) Math.abs(src),res = abs;
 		while((abs >>= 1) >=  1)
			res ^= abs;
  		return (byte) (src > 0 ? res : -res);
	}

	public static void main(String[] args) {
		System.out.println(byteToGray((byte) 1));
		System.out.println(byteToGray((byte) 127));
		System.out.println(byteToGray((byte) -127));

		System.out.println(grayToByte((byte) byteToGray((byte) 127)));
	}

}
