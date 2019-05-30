package arithmetic.genetic;


import bean.WeightIndividual;

import java.util.Set;

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
     * 种群
     */
    private Set<WeightIndividual> weightIndividuals;

    /**
     * 计算适应度 适应度越高则胜率越高
     *  让个体之间不断组织循环赛 一共有N个体，
     *  就需要组织N(N-1)组比赛，比赛分为2局，分为A和B各先，
     *      如果A两局胜 A强于B
     *      如果A两局皆输 可知A弱于B
     *      如果A先行胜 A后行负 根据后行占优势，可知A稍微弱于B
     *      除此之外 暂不知A是否强于B
     *   分数计算方式为胜方计算胜利多少子 如果没有胜利方 则为0分
     * @return  分数
     */
    private int envaluateFitness(){
        all_score = 0.0;
        for (WeightIndividual weightIndividual : weightIndividuals) {
            // 每一个成员互相对战


        }
        return 0;
    }



}
