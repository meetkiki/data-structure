package arithmetic.genetic;


import bean.Gameplayer;
import bean.WeightIndividual;
import common.Constant;
import common.WinnerStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static common.Constant.NULL;

/**
 * 遗传算法
 *  求解最佳权重组合
 */
public class GeneticAlgorithm {
    /**
     * 种群规模
     */
    private int entitysize = 100;
    /**
     * 变异概率
     */
    private double p_bianyi = 0.05;
    /**
     * 交配概率
     */
    private double p_jiaopei = 0.8;
    /**
     * 种群总分
     */
    private double all_score = 0;
    /**
     * 种群幸运度
     */
    private double all_lucky = 0;
    /**
     * 最佳数据
     */
    private double best_score;
    private WeightIndividual best_weight;
    /**
     * 最差数据
     */
    private double last_score;
    private WeightIndividual last_weight;
    /**
     * 种群
     */
    private List<WeightIndividual> weightIndividuals;
    /**
     * 下一代种群
     */
    private List<WeightIndividual> newIndividuals;

    /**
     * 初始化种群数据
     */
    void initIndividuals(){
        weightIndividuals = new ArrayList<>();
        for (int i = 0; i < entitysize; i++) {
            weightIndividuals.add(new WeightIndividual());
        }
        newIndividuals = new ArrayList<>();
    }

    /**
     * 计算适应度 适应度越高则胜率越高
     *  让个体之间不断组织循环赛 一共有N个体，
     *  就需要组织N(N-1)组比赛，比赛分为2局，分为A和B各先，
     *      如果A两局胜 A强于B
     *      如果A两局皆输 可知A弱于B
     *      如果A先行胜 A后行负 根据后行占优势，可知A稍微弱于B
     *      除此之外 暂不知A是否强于B
     *   分数计算方式为胜方计算胜利多少子 如果没有胜利方 则为0分
     * @return  总分数
     */
    private double envaluateFitness(){
        // 每一个成员互相对战
        Map<WeightIndividual, List<Gameplayer>> listMap = GameManager.chief_dispatcher(weightIndividuals);
        // 计算每个基因得分
        update_fitness(listMap);
        // 计算幸运度 幸存程度，分数越高幸存程度越高，注意归一化,为轮盘赌做准备
        update_lucky(weightIndividuals);
        return all_score;
    }

    /**
     * 计算单个基因得分并更新fitness
     * @param listMap
     */
    private void update_fitness(Map<WeightIndividual, List<Gameplayer>> listMap) {
        all_score = 0.0;
        // 计算总分 及适应度
        for (Map.Entry<WeightIndividual, List<Gameplayer>> entry : listMap.entrySet()) {
            WeightIndividual individual = entry.getKey();
            List<Gameplayer> gameplayers = entry.getValue();
            double winners = 0.0;
            for (Gameplayer gameplayer : gameplayers) {
                WinnerStatus status = acquireStatus(individual,gameplayer);
                if (gameplayer.isFirst()){
                    // 先手胜利记1.0分 先手失败-0.5记 未知记0.2
                    winners += (status == WinnerStatus.WIN ? 1.0 : (status == WinnerStatus.LOSS ? -0.5 : 0.2));
                }else{
                    // 后手胜利记0.5分 后手失败记-1.0分 未知记0.2
                    winners += (status == WinnerStatus.WIN ? 0.5 : (status == WinnerStatus.LOSS ? -1.0 : 0.2));
                }
            }
            // 个人总分
            individual.setFitness(winners);
            all_score += winners;
        }
    }

    /**
     * 获取状态
     * @param individual
     * @param gameplayer
     * @return
     */
    private WinnerStatus acquireStatus(WeightIndividual individual, Gameplayer gameplayer) {
        WeightIndividual winner = gameplayer.getWinner();
        if (NULL == winner){
            return WinnerStatus.NONE;
        }
        return individual.equals(winner) ? WinnerStatus.WIN : WinnerStatus.LOSS;
    }

    /**
     * 计算幸运度 为轮盘赌做准备
     * @param weightIndividuals
     */
    private void update_lucky(List<WeightIndividual> weightIndividuals) {
        // 总概率
        all_lucky = 0.0;
        // 累积概率
        double c_lucky = 0.0;
        for (WeightIndividual individual : weightIndividuals) {
            double lucky = individual.getFitness() / all_score;
            c_lucky += lucky;
            individual.setLucky(lucky);
            individual.setClucky(c_lucky);
            all_lucky += lucky;
        }
    }

    /**
     * 按某个选择概率选择样本,使用轮盘赌选择法
     *  根据幸存程度选择
     */
    private void chooseSample(List<WeightIndividual> weightIndividuals){
        // 使用map去重
        Set<WeightIndividual> set = new HashSet<>();
        for (int i = 0; i < weightIndividuals.size(); i++) {
            // 产生0-1的随机数
            double v = Math.random();
            if (v < weightIndividuals.get(0).getClucky()){
                set.add(weightIndividuals.get(i));
            }else {
                for (int i1 = 1; i1 < weightIndividuals.size(); i1++) {
                    if (weightIndividuals.get(i1 - 1).getClucky() <= v &&
                            weightIndividuals.get(i1).getClucky() > v) {
                        set.add(weightIndividuals.get(i1));
                        break;
                    }
                }
            }
        }
        // 下一代种群
        newIndividuals = new ArrayList<>(set);
    }


    /**
     * 判断是否结束迭代
     *  最高分和最低分差小于常量
     * @return
     */
    private boolean chooseBestSolution(List<WeightIndividual> weightIndividuals){
        double best = Double.MIN_VALUE,last = Double.MAX_VALUE;
        for (WeightIndividual individual : weightIndividuals) {
            if (best < individual.getFitness()) {
                best_weight = individual;
                best = individual.getFitness();
            }
            if (last > individual.getFitness()){
                last_weight = individual;
                last = individual.getFitness();
            }
        }
        best_score = best;
        last_score = last;
        System.out.println("该次迭代最好的权重 : " + best_weight + " ; 该次迭代的最佳幸存率 : " + best_weight.getLucky());
        System.out.println("该次迭代最好的分数 : " + best_score);
        System.out.println("该次迭代最差的权重 : " + last_weight + " ; 该次迭代的最差幸存率 : " + last_weight.getLucky());
        System.out.println("该次迭代最差的分数 : " + last_score);
        if ((best - last) < Constant.convergence){
            return true;
        }
        return false;
    }

}
