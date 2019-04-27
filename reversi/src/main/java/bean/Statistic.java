package bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 玩家的棋子个数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Statistic {
	private byte PLAYER;
	private int count;
}
