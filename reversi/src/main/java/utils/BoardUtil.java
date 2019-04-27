package utils;

import bean.BoardData;
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
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.IntFunction;

import static common.Constant.DELAY;
import static common.Constant.SIZE;

/**
 * @author Tao
 */
public class BoardUtil {

	/**
	 * 拷贝棋盘二维数组
	 */
	public static void copyBinaryArray(byte[][] src, byte[][] dest) {
		for (int i = 0; i < src.length; i++) {
			System.arraycopy(src[i], 0, dest[i], 0, src[i].length);
		}
	}

	/**
	 * 拷贝泛型二维数组
	 *
	 * @param <T>
	 * @return
	 */
	public static<T extends Serializable> T[][] copyBinary(IntFunction<T[]> desc1,IntFunction<T[][]> desc2,T[][] src){
		T[][] desc = desc2.apply(src.length);
		for (int i = 0; i < src.length; i++) {
			T[] ts = desc1.apply(src[i].length);
			for (int i1 = 0; i1 < src[i].length; i1++) {
				ts[i1] = deepCopy(src[i][i1]);
			}
		}
		return desc;
	}

	/**
	 * 深度复制方法,需要对象及对象所有的对象属性都实现序列化
	 * @param src
	 * @param <T>
	 * @return
	 */
	private static<T extends Serializable> T deepCopy(T src){
		T outer = null;
		try {
			// 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(src);
			// 将流序列化成对象
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			outer = (T) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outer;
	}

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

		System.out.println("===================moves==================");
		List<byte[]> canMoves = data.getCanMoves();
		System.out.println("canMoves :");
		canMoves.forEach((e)-> {
			System.out.print(Arrays.toString(e) + ", ");
		});
	}


}
