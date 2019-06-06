package arithmetic.genetic;


import bean.Gameplayer;
import bean.WeightIndividual;
import common.Constant;
import common.WinnerStatus;
import lombok.extern.log4j.Log4j2;
import utils.BoardUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static common.Constant.NULL;

/**
 * 遗传算法
 *  求解最佳权重组合
 * @author Tao
 */
@Log4j2
public class GeneticAlgorithm {
    /**
     * 种群规模 128
     */
    private int entitysize = 2 << 6;
    /**
     * 变异概率
     */
    private double p_mutation = 0.10;
    /**
     * 种群总分
     */
    private double all_score = 0;
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
    private Set<WeightIndividual> newIndividuals;

    /**
     * 初始化种群数据
     */
    private List<WeightIndividual> initIndividuals(){
        weightIndividuals = new ArrayList<>();
        // 加一个人工干扰基因
        weightIndividuals.add(new WeightIndividual(new int[]{11,1,1,5,-2,-1,2,8,1,1,11,-2,-1,5,8,2,2,15,0,2,5},128));
        for (int i = 0; i < (entitysize - 1); i++) {
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
                if (gameplayer.getFirst().equals(individual)){
                    // 先手胜利记2.0分 先手失败0.5记 未知记1.2
                    winners += (status == WinnerStatus.WIN ? 2.0 : (status == WinnerStatus.LOSS ? 0.5 : 1.2));
                }else{
                    // 后手胜利记1.5分 后手失败记0.0分 未知记1.2
                    winners += (status == WinnerStatus.WIN ? 1.5 : (status == WinnerStatus.LOSS ? 0.0 : 1.2));
                }
            }
            // 个人总分
            individual.setFitness(winners);
            all_score += winners;
        }
        this.all_score = all_score;
        // 根据比分排序倒序 保留最优基因
        Collections.sort(this.weightIndividuals, (o1,o2)-> (int) ((o2.getFitness() - o1.getFitness()) * 100));
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
     * 按某个选择概率选择样本,使用轮盘赌选择法
     *  根据幸存程度选择
     */
    private void chooseSample(List<WeightIndividual> weightIndividuals){
        // 保留的下一代种群
        this.newIndividuals = new HashSet<>();
        // 最优基因不进行轮盘 直接保留
        newIndividuals.add(this.weightIndividuals.get(0));
        for (int i = 1; i < weightIndividuals.size(); i++) {
            // 产生0-1的随机数1
            double v = Math.random();
            for (int i1 = 1; i1 < weightIndividuals.size(); i1++) {
                if (weightIndividuals.get(i1 - 1).getClucky() <= v &&
                        weightIndividuals.get(i1).getClucky() > v) {
                    newIndividuals.add(weightIndividuals.get(i1));
                    break;
                }
            }
        }
        log.info("父代选择开始 : " + WeightIndividual.printAllName(this.weightIndividuals));
        // 增加垃圾回收
        this.weightIndividuals.clear();
        this.weightIndividuals = new ArrayList<>(newIndividuals);
        // 根据比分排序倒序 保留最优基因
        Collections.sort(this.weightIndividuals, (o1,o2)-> (int) ((o2.getFitness() - o1.getFitness()) * 100));
        log.info("父代选择结束 剩余: " + WeightIndividual.printAllName(this.weightIndividuals));
    }

    /**
     * 计算幸运度 为轮盘赌做准备
     * @param weightIndividuals
     */
    private void update_lucky(List<WeightIndividual> weightIndividuals) {
        // 总概率为1
        // 累积概率
        double c_lucky = 0.0;
        for (WeightIndividual individual : weightIndividuals) {
            double lucky = individual.getFitness() / this.all_score;
            c_lucky += lucky;
            individual.setLucky(lucky);
            individual.setClucky(c_lucky);
        }
    }

    /**
     * 基因重组
     *  对出现部分基因进行基因交叉运算
     */
    private void recombination(List<WeightIndividual> individuals){
        if (individuals.size() >= entitysize){
            log.info("未产生交叉物种 !");
            return;
        }
        List<WeightIndividual> recom = new LinkedList<>();
        // 标识第一个交叉基因
        int first = -1,size = individuals.size();
        // 最优基因不进行重组 直接保留 // 直到种群数目达到为止
        while ((size + recom.size()) < entitysize){
            int v = (int) (Math.random() * size);
            if (first < 0){
                first = v;
            } else if (v != first){
                ExchangeOver(individuals.get(first),individuals.get(v),recom);
                checkRepeat(individuals,recom);
                first = -1;
            }
        }
        log.info("得到交配物种 ： " + WeightIndividual.printAllName(recom));
        individuals.addAll(recom);
    }

    /**
     * 校验是否产生同样基因的子代
     * @param individuals
     * @param recom
     */
    private void checkRepeat(List<WeightIndividual> individuals, List<WeightIndividual> recom) {
        Iterator<WeightIndividual> iterator = recom.iterator();
        while (iterator.hasNext()) {
            WeightIndividual individual = iterator.next();
            if (individuals.contains(individual)) {
                iterator.remove();
            }
        }
    }

    /**
     * 对first和second进行基因重组 基因重组只对src有效 即只交换值的位置 不影响基因
     * @param individualA
     * @param individualB
     */
    private void ExchangeOver(WeightIndividual individualA,WeightIndividual individualB,List<WeightIndividual> recom) {
        int[] srcAs = individualA.getSrcs();
        int[] srcBs = individualB.getSrcs();
        int[] cloneA = srcAs.clone();
        int[] cloneB = srcBs.clone();
        // 对格雷码进行交叉运算
        // 对随机个基因数进行交换 最低一位
        int ecc = (int) (Math.random() * (Constant.DATALENGTH)) + 1;
        for (int i = 0; i < ecc; i++) {
            // 每个位置进行交换的概率也是相同的 最低一位
            int v = (int) (Math.random() * Constant.DATALENGTH - 1) + 1;
            int temp = cloneA[v];
            cloneA[v] = cloneB[v];
            cloneB[v] = temp;
        }
        recom.add(new WeightIndividual(cloneA));
        recom.add(new WeightIndividual(cloneB));
    }

    /**
     * 基因变异运算
     */
    private void mutationGenes(List<WeightIndividual> individuals){
        if (individuals.size() >= entitysize){
            log.info("未产生变异物种 !");
            return;
        }
        List<WeightIndividual> reverse = new ArrayList<>();
        // 同理 保留优秀基因
        for (int exc = 1; exc < individuals.size(); exc++) {
            double p = Math.random();
            if (p < p_mutation){
                reverse.add(reverseGenes(individuals.get(exc)));
            }
        }
        // 为保证种群交配为双数 这里变异可以多产生种群为双数为止
        if (((reverse.size() + individuals.size()) & 1) == 1){
            if (individuals.size() == 1){
                reverse.add(reverseGenes(individuals.get(0)));
            }else{
                int exc = (int) (Math.random() * individuals.size());
                reverse.add(reverseGenes(individuals.get(exc)));
            }
        }
        if (reverse.size() > 0){
            log.info("得到变异物种 ： " + WeightIndividual.printAllName(reverse));
            individuals.addAll(reverse);
        }else {
            log.info("未产生变异物种 !");
        }
    }

    /**
     * 变异处理
     * @param weightIndividual
     */
    private WeightIndividual reverseGenes(WeightIndividual weightIndividual) {
        byte[] grays = weightIndividual.getGrays();
        byte[] reverse = grays.clone();
        // 最低交配一位
        int ecc = (int) (Math.random() * (Constant.GENELENGTH)) + 1;
        for (int i = 0; i < ecc; i++) {
            // 每个位置进行变异的概率也是相同的 最低交配一位
            int v = (int) (Math.random() * Constant.GENELENGTH - 1)  + 1;
            reverse[v] = (byte) (reverse[v] == 1 ? 0 : 1);
        }
        // 变异物种
        return new WeightIndividual(reverse);
    }


    /**
     * 通过格雷基因更新源基因
     * @param individuals
     */
    private void flushsrcGenes(List<WeightIndividual> individuals) {
        log.info("更新种群src基因 当前种群大小 " + individuals.size());
        for (WeightIndividual individual : individuals) {
            byte[] grays = individual.getGrays();
            byte[] genes = individual.getGenes();
            int[] srcs = individual.getSrcs();
            // 通过grays基因组装genes和src基因
            BoardUtil.graysToGens(grays,srcs,genes);
        }
        log.info("当前种群 ： " + WeightIndividual.printAllName(individuals));
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
                this.best_weight = individual;
                best = individual.getFitness();
            }
            if (last > individual.getFitness()){
                last = individual.getFitness();
            }
        }
        best_score = best;
        double v = (best - last) / entitysize;
        log.info("该次迭代最好的基因 : " + Arrays.toString(best_weight.getSrcs()) + " ; 该次迭代的最佳幸存率 : " + best_weight.getLucky());
        log.info("该次迭代最好的分数 : " + best_score);
        log.info("该代的基因收敛率为 ============ " + v);
        if (v < Constant.convergence){
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
        algorithm.initIndividuals();
        log.info("初始种群 ： " + WeightIndividual.printAllName(algorithm.weightIndividuals));
        int it = 1;
        boolean solution;
        do {
            long st = System.currentTimeMillis();
                    // 计算适应度
            algorithm.envaluateFitness(algorithm.weightIndividuals);

            solution = algorithm.chooseBestSolution(algorithm.weightIndividuals);
            // 选择样本
            algorithm.chooseSample(algorithm.weightIndividuals);
            // 优先进行  变异计算
            algorithm.mutationGenes(algorithm.weightIndividuals);
            // 剩下 交叉计算
            algorithm.recombination(algorithm.weightIndividuals);
            // 更新源基因
            algorithm.flushsrcGenes(algorithm.weightIndividuals);

            long ed = System.currentTimeMillis();
            log.info("经过第 " + it++ + "次迭代 , 本次迭代耗时 "+ (ed - st) +" ms, 当前种群最优基因为: " + algorithm.best_weight.getName() + " : " +
                    Arrays.toString(algorithm.best_weight.getSrcs()));
            // 判断是否继续迭代
        } while (solution);

        log.info("迭代结束! ");
        log.info("迭代结束! ");
        log.info(Arrays.toString(algorithm.best_weight.getSrcs()));
    }


}
