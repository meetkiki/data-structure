package arithmetic.genetic;


import bean.Gameplayer;
import bean.WeightIndividual;
import common.Constant;
import common.WinnerStatus;
import utils.BoardUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static common.Constant.NULL;

/**
 * 遗传算法
 *  求解最佳权重组合
 */
public class GeneticAlgorithm {
    /**
     * 种群规模 128
     */
    private int entitysize = 2 << 7;
    /**
     * 变异概率
     */
    private double p_mutation = 0.05;
    /**
     * 交配概率
     */
    private double p_mating = 0.8;
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
    private List<WeightIndividual> initIndividuals(){
        weightIndividuals = new ArrayList<>();
        for (int i = 0; i < entitysize; i++) {
            weightIndividuals.add(new WeightIndividual());
        }
        return this.weightIndividuals;
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
    private double envaluateFitness(List<WeightIndividual> individuals){
        // 每一个成员互相对战
        Map<WeightIndividual, List<Gameplayer>> listMap = GameManager.chief_dispatcher(weightIndividuals);
        // 计算每个基因得分
        double fitness = update_fitness(listMap);
        // 计算幸运度 幸存程度，分数越高幸存程度越高，注意归一化,为轮盘赌做准备
        update_lucky(individuals);
        return fitness;
    }

    /**
     * 计算单个基因得分并更新fitness
     * @param listMap
     */
    private double update_fitness(Map<WeightIndividual, List<Gameplayer>> listMap) {
        double all_score = 0.0;
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
        this.all_score = all_score;
        // 根据比分排序倒序 保留最优基因
        Collections.sort(this.weightIndividuals, (o1,o2)->(int) ((o2.getFitness() - o1.getFitness() * 100)));
        return all_score;
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
        double all_lucky = 0.0;
        // 累积概率
        double c_lucky = 0.0;
        for (WeightIndividual individual : weightIndividuals) {
            double lucky = individual.getFitness() / this.all_score;
            c_lucky += lucky;
            individual.setLucky(lucky);
            individual.setClucky(c_lucky);
            all_lucky += lucky;
        }
        this.all_lucky = all_lucky;
    }

    /**
     * 按某个选择概率选择样本,使用轮盘赌选择法
     *  根据幸存程度选择
     */
    private void chooseSample(List<WeightIndividual> weightIndividuals){
        // 保留的下一代种群
        this.newIndividuals = new ArrayList<>();
        // 最优基因不进行轮盘 直接保留
        newIndividuals.add(this.weightIndividuals.get(0));
        for (int i = 1; i < weightIndividuals.size(); i++) {
            // 产生0-1的随机数1
            double v = Math.random();
            if (v < weightIndividuals.get(1).getClucky()){
                newIndividuals.add(weightIndividuals.get(i));
            }else {
                for (int i1 = 2; i1 < weightIndividuals.size(); i1++) {
                    if (weightIndividuals.get(i1 - 1).getClucky() <= v &&
                            weightIndividuals.get(i1).getClucky() > v) {
                        newIndividuals.add(weightIndividuals.get(i1));
                        break;
                    }
                }
            }
        }
        // 增加垃圾回收
        this.weightIndividuals.clear();
        this.weightIndividuals = newIndividuals;
    }

    /**
     * 基因重组
     *  对出现部分基因进行基因交叉运算
     */
    private void recombination(List<WeightIndividual> individuals){
        // 标识第一个交叉基因
        int first = -1;
        // 最优基因不进行重组 直接保留
        for (int exc = 1; exc < individuals.size(); exc++) {
            double v = Math.random();
            if(v <= p_mating){
                if (first < 0){
                    first = exc;
                }else {
                    ExchangeOver(individuals,first,exc);
                    first = -1;
                }
            }
        }
    }

    /**
     * 对first和second进行基因重组
     * @param individuals
     * @param first
     * @param second
     */
    private void ExchangeOver(List<WeightIndividual> individuals, int first, int second) {
        // 对该基因的格雷码进行重组
        WeightIndividual individualA = individuals.get(first);
        WeightIndividual individualB = individuals.get(second);
        byte[] grayAs = individualA.getGrays();
        byte[] grayBs = individualB.getGrays();
        // 对格雷码进行交叉运算
        // 对随机个基因数进行交换
        int ecc = (int) (Math.random() * (Constant.GENELENGTH + 1));
        for (int i = 0; i < ecc; i++) {
            // 每个位置进行交换的概率也是相同的
            int v = (int) (Math.random() * Constant.GENELENGTH);
            int temp = grayAs[v];
            grayAs[v] = grayBs[v];
            grayBs[v] = (byte) temp;
        }
    }

    /**
     * 基因变异运算
     */
    private void mutationGenes(List<WeightIndividual> individuals){
        // 同理 保留优秀基因
        for (int exc = 1; exc < individuals.size(); exc++) {
            double p = Math.random();
            if (p < p_mutation){
                reverseGenes(individuals.get(exc));
            }
        }
    }

    /**
     * 变异处理
     * @param weightIndividual
     */
    private void reverseGenes(WeightIndividual weightIndividual) {
        byte[] grays = weightIndividual.getGrays();
        int ecc = (int) (Math.random() * (Constant.GENELENGTH + 1));
        for (int i = 0; i < ecc; i++) {
            // 每个位置进行变异的概率也是相同的
            int v = (int) (Math.random() * Constant.GENELENGTH);
            grays[v] = (byte) (grays[v] == 1 ? 0 : 1);
        }
    }


    /**
     * 通过格雷基因更新源基因
     * @param individuals
     */
    private void flushsrcGenes(List<WeightIndividual> individuals) {
        for (WeightIndividual individual : individuals) {
            byte[] grays = individual.getGrays();
            byte[] genes = individual.getGenes();
            int[] srcs = individual.getSrcs();
            // 通过grays基因组装genes和src基因
            BoardUtil.graysToGens(grays,srcs,genes);
        }
    }


    /**
     * 判断是否结束迭代
     *  最高分和最低分差小于常量
     * @return
     */
    private boolean chooseBestSolution(List<WeightIndividual> weightIndividuals){
        double best = Double.MIN_VALUE,last = Double.MAX_VALUE;
        WeightIndividual last_weight = null;
        for (WeightIndividual individual : weightIndividuals) {
            if (best < individual.getFitness()) {
                this.best_weight = individual;
                best = individual.getFitness();
            }
            if (last > individual.getFitness()){
                last_weight = individual;
                last = individual.getFitness();
            }
        }
        best_score = best;
        System.out.println("该次迭代最好的权重 : " + best_weight + " ; 该次迭代的最佳幸存率 : " + best_weight.getLucky());
        System.out.println("该次迭代最好的分数 : " + best_score);
        System.out.println("该次迭代最差的权重 : " + last_weight + " ; 该次迭代的最差幸存率 : " + last_weight.getLucky());
        System.out.println("该次迭代最差的分数 : " + last);
        if ((best - last) < Constant.convergence){
            return false;
        }
        return true;
    }

    /**
     * 遗传迭代方法
     * @param args
     */
    public static void main(String[] args) {
        GeneticAlgorithm algorithm = new GeneticAlgorithm();
        List<WeightIndividual> individuals = algorithm.initIndividuals();
        double fitness = algorithm.envaluateFitness(individuals);
        // 判断是否继续迭代
        while (algorithm.chooseBestSolution(individuals)){
            // 计算适应度
            algorithm.envaluateFitness(individuals);
            // 选择样本
            algorithm.chooseSample(individuals);
            // 交叉计算
            algorithm.recombination(individuals);
            // 变异计算
            algorithm.mutationGenes(individuals);
            // 更新源基因
            algorithm.flushsrcGenes(individuals);
        }

        System.out.println(Arrays.toString(algorithm.best_weight.getSrcs()));
    }


}
