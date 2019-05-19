package bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static common.Constant.coordinateStr;

/**
 * 标识棋盘中的一个位置
 * @author Tao
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Move {
	private byte row;
	private byte col;

	@Override
	public String toString() {
		return "Move{" +
				"coordinateStr = " + coordinateStr[row][col] +
				", row=" + (row + 1) +
				", col=" + (col + 1) +
				'}';
	}
}
