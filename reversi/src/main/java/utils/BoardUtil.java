package utils;

import bean.BoardData;
import bean.Move;
import common.Constant;
import common.ImageConstant;
import game.Chess;
import game.GameContext;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.function.IntFunction;

import static common.Constant.DELAY;
import static common.Constant.SIZE;

/**
 * @author Tao
 */
public class BoardUtil {

	/**
	 * 棋子动画
	 * 		1 --- 6
	 *     白     黑
	 * @param chess
	 * @param //repaint
	 */
	public static void converSion(byte chess, Chess curr){
		Timer timer = new Timer();
		//根据传参的正负判断转变的棋子方向 6 -> 1 表示黑变白
		int tem = chess == Constant.WHITE ? 6 : 1;
		TimerTask task = new TimerTask() {
			private int count = tem;
			@Override
			public void run() {
				if(count > 0 && count <= 6){
					updateImg(count,curr);
					if(chess == Constant.WHITE) count--;
					else count++;
				}else{
					//结束任务
					cancel();
					//修正图标
					curr.setChess(chess);
					curr.repaint();
				}
			}
		};
		timer.schedule(task,0,DELAY);
	}

	/**
	 * 设置翻转图片
	 * @param count
	 * @param curr
	 */
	private static void updateImg(int count, Chess curr) {
		String url = String.format(Constant.OVERTURN, count + "");
		Image image = GameContext.getResources().get(ImageConstant.valueOf(url)).getImage();
		curr.setImage(image);
		curr.repaint();
	}

	/**
	 *  /控制台显示棋盘
	 */
	public static void display(BoardData data){
		System.out.println("===================chess==================");

		Chess[][] chess = data.getChess();
		boolean[][] moves = data.getMoves();
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
				if(moves[row][col] == false){
					System.out.printf(" %d |",chess[row][col].getChess());
				}else
					System.out.print(" . |");
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
	 * 深度拷贝棋盘
	 * @param data
	 * @return
	 */
	public static BoardData copyBoard(BoardData data) {
		return data.cloneData();
	}
}
