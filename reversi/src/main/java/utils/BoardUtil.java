package utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.function.IntFunction;

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
	
}
