package bean;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tao
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticalScore {
    /**
     * 分数
     */
    private int score;
    /**
     * 胜利场数
     */
    private int win;
    /**
     * 失败场数
     */
    private int loss;

}
