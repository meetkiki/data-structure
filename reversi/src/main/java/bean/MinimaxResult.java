package bean;


import common.EntryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 记录极小极大算法过程中的数据
 * @author Tao
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MinimaxResult {
	private float mark;
	private int depth;
	private EntryType type;
	private Move move;

	/**
	 * 分数取反
	 * @return
	 */
	public MinimaxResult inverseMark(){
		return inverseMark(1);
	}

	/**
	 * 分数取反
	 * @return
	 */
	public MinimaxResult inverseMark(double count){
		this.mark = (float) - (this.mark * count);
		return this;
	}
}




