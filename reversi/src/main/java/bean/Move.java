package bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 标识棋盘中的一个位置
 * @author Tao
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Move {
	private byte row;
	private byte col;
}
