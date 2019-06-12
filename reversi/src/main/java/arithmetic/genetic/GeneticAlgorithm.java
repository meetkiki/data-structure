package arithmetic.genetic;


import bean.Gameplayer;
import bean.WeightIndividual;
import com.alibaba.fastjson.JSON;
import common.Constant;
import common.WinnerStatus;
import lombok.extern.log4j.Log4j2;
import utils.BoardUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
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
    public static final int entitysize = 2 << 6;
    /**
     * 变异概率
     */
    public static final double p_mutation = 0.10;
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
        String genes = "[[177, 221, 154, 146, 59, 133, 26, 112, 219, 122, 130, 160, 81, 106, 254, 86, 113, 246, 163, 224, 20], [227, 221, 160, 117, 58, 133, 17, 231, 219, 122, 130, 160, 227, 106, 228, 81, 57, 210, 239, 158, 156], [254, 221, 224, 16, 26, 133, 167, 119, 204, 122, 130, 253, 4, 107, 162, 250, 63, 88, 98, 159, 203], [177, 221, 155, 16, 59, 133, 167, 120, 162, 122, 130, 206, 81, 101, 236, 174, 128, 208, 156, 158, 17], [173, 103, 219, 173, 70, 26, 198, 8, 124, 74, 11, 143, 83, 224, 226, 20, 128, 232, 62, 238, 20], [199, 169, 154, 146, 51, 138, 62, 119, 229, 122, 130, 206, 22, 224, 236, 86, 113, 233, 198, 158, 20], [254, 221, 159, 157, 58, 138, 142, 119, 204, 122, 245, 170, 81, 87, 236, 89, 121, 223, 163, 141, 20], [177, 221, 154, 146, 58, 133, 26, 112, 146, 122, 130, 253, 81, 106, 236, 86, 113, 223, 118, 158, 20], [227, 223, 159, 146, 59, 153, 26, 232, 162, 182, 245, 191, 227, 151, 254, 89, 128, 224, 241, 225, 20], [199, 204, 17, 107, 51, 133, 62, 119, 195, 122, 50, 206, 22, 224, 226, 226, 113, 88, 198, 238, 150], [254, 221, 155, 146, 59, 133, 167, 119, 146, 122, 147, 47, 81, 101, 236, 89, 38, 208, 117, 158, 17], [199, 221, 154, 107, 51, 133, 62, 119, 162, 122, 127, 222, 22, 87, 226, 226, 113, 223, 198, 224, 20], [193, 220, 100, 146, 146, 28, 62, 8, 170, 166, 9, 74, 78, 18, 239, 89, 38, 22, 116, 158, 221], [177, 221, 154, 146, 59, 133, 167, 119, 219, 122, 130, 160, 81, 106, 254, 89, 128, 246, 163, 224, 20], [177, 221, 154, 199, 58, 133, 167, 119, 146, 122, 130, 253, 227, 22, 254, 89, 128, 223, 118, 158, 20], [177, 10, 17, 107, 164, 186, 35, 119, 195, 115, 178, 61, 253, 235, 228, 226, 14, 88, 118, 224, 150], [134, 221, 159, 146, 58, 133, 26, 119, 146, 122, 164, 253, 81, 87, 236, 86, 14, 159, 117, 141, 11], [199, 169, 17, 107, 51, 138, 62, 119, 229, 122, 130, 206, 22, 224, 226, 226, 113, 233, 198, 238, 150], [225, 193, 155, 90, 105, 75, 5, 119, 108, 75, 178, 208, 103, 112, 236, 89, 93, 88, 163, 146, 17], [254, 221, 161, 157, 53, 138, 26, 136, 146, 122, 127, 206, 81, 106, 236, 81, 128, 223, 163, 158, 17], [193, 46, 100, 229, 146, 3, 62, 3, 170, 95, 92, 80, 78, 18, 239, 91, 114, 22, 116, 158, 221], [139, 156, 234, 159, 192, 178, 25, 232, 148, 182, 99, 191, 227, 244, 254, 206, 128, 224, 63, 128, 119], [254, 181, 159, 157, 58, 138, 62, 119, 204, 122, 245, 170, 81, 87, 236, 89, 121, 22, 163, 141, 20], [177, 221, 159, 146, 90, 125, 26, 7, 146, 78, 132, 222, 227, 98, 223, 8, 128, 232, 136, 158, 192], [254, 221, 155, 146, 137, 133, 55, 119, 146, 122, 147, 47, 81, 87, 162, 89, 38, 246, 117, 158, 17], [227, 181, 221, 146, 53, 119, 142, 101, 162, 122, 245, 222, 221, 235, 254, 89, 128, 223, 156, 158, 150], [134, 221, 221, 199, 51, 119, 26, 119, 146, 122, 245, 222, 22, 22, 226, 226, 14, 223, 117, 158, 11], [199, 221, 154, 107, 51, 133, 62, 119, 162, 122, 127, 222, 22, 87, 226, 226, 14, 223, 198, 224, 150], [177, 221, 155, 157, 59, 138, 167, 119, 146, 122, 9, 160, 73, 244, 254, 86, 128, 222, 118, 141, 17], [164, 221, 152, 146, 251, 172, 21, 112, 211, 122, 8, 214, 25, 211, 186, 252, 102, 187, 118, 161, 110], [227, 214, 221, 93, 4, 119, 35, 101, 226, 122, 13, 222, 221, 235, 230, 42, 137, 223, 114, 158, 150], [132, 237, 159, 30, 59, 133, 26, 119, 226, 73, 147, 47, 227, 106, 224, 86, 14, 43, 117, 141, 11], [254, 221, 161, 157, 53, 218, 26, 136, 146, 122, 127, 206, 81, 106, 236, 81, 115, 223, 163, 158, 20], [177, 204, 154, 114, 59, 133, 167, 119, 252, 122, 245, 47, 81, 104, 254, 89, 128, 246, 163, 224, 20], [254, 221, 132, 157, 141, 133, 62, 136, 146, 122, 29, 206, 227, 101, 236, 81, 117, 22, 108, 158, 20], [132, 220, 107, 157, 53, 133, 121, 119, 146, 16, 76, 172, 30, 244, 239, 89, 131, 166, 108, 158, 235], [199, 201, 221, 114, 51, 133, 62, 247, 195, 122, 50, 237, 22, 188, 223, 42, 128, 150, 198, 93, 94], [254, 237, 159, 157, 58, 138, 142, 119, 204, 122, 245, 170, 81, 106, 236, 89, 121, 223, 163, 158, 20], [177, 204, 17, 229, 164, 186, 62, 119, 146, 122, 13, 222, 221, 235, 228, 226, 14, 88, 118, 224, 150], [132, 221, 154, 30, 4, 133, 26, 119, 226, 76, 164, 174, 227, 22, 224, 42, 14, 150, 163, 158, 11], [173, 103, 219, 173, 70, 26, 198, 8, 124, 74, 11, 143, 83, 188, 223, 20, 128, 232, 62, 93, 94], [132, 221, 154, 30, 4, 133, 26, 119, 226, 76, 164, 222, 227, 22, 224, 42, 14, 150, 163, 158, 11], [227, 220, 155, 146, 58, 138, 26, 119, 219, 122, 9, 160, 227, 106, 254, 81, 57, 210, 118, 158, 156], [177, 221, 154, 229, 58, 133, 26, 119, 162, 122, 130, 253, 81, 106, 228, 81, 113, 246, 62, 158, 20], [227, 181, 221, 146, 53, 138, 167, 101, 162, 122, 9, 222, 221, 235, 254, 89, 38, 223, 156, 141, 150], [177, 221, 159, 146, 90, 125, 26, 7, 146, 78, 132, 174, 227, 98, 223, 8, 128, 232, 136, 158, 192], [132, 237, 154, 30, 59, 133, 26, 119, 226, 76, 164, 47, 227, 106, 224, 86, 14, 43, 163, 158, 11], [249, 110, 37, 9, 212, 105, 57, 248, 161, 80, 61, 239, 133, 211, 254, 89, 176, 45, 223, 37, 106], [177, 221, 17, 107, 58, 186, 26, 112, 146, 122, 13, 222, 81, 106, 226, 226, 113, 88, 118, 238, 150], [251, 204, 227, 156, 99, 133, 35, 119, 195, 121, 50, 85, 147, 30, 255, 80, 75, 88, 156, 158, 11], [225, 33, 155, 181, 4, 119, 84, 119, 146, 122, 130, 160, 221, 244, 254, 7, 115, 222, 156, 141, 221], [177, 204, 154, 114, 53, 133, 26, 119, 162, 122, 130, 47, 81, 104, 253, 81, 128, 246, 163, 224, 20], [225, 33, 161, 181, 4, 119, 84, 8, 146, 122, 130, 47, 195, 235, 254, 7, 115, 150, 156, 158, 221], [134, 237, 159, 199, 58, 133, 26, 119, 146, 122, 164, 253, 227, 22, 254, 86, 14, 159, 117, 158, 11], [199, 204, 17, 98, 51, 139, 62, 119, 195, 122, 50, 206, 22, 224, 226, 226, 113, 88, 198, 238, 107], [227, 221, 221, 146, 51, 119, 142, 101, 162, 122, 245, 222, 22, 87, 226, 226, 128, 223, 156, 158, 150], [177, 221, 159, 146, 59, 133, 167, 120, 162, 122, 130, 206, 81, 101, 236, 89, 121, 208, 62, 158, 20], [238, 200, 107, 146, 59, 133, 167, 119, 219, 122, 245, 47, 86, 106, 99, 95, 128, 208, 163, 114, 20], [227, 181, 132, 146, 51, 133, 142, 119, 195, 122, 245, 222, 227, 224, 254, 89, 128, 224, 156, 225, 150], [132, 237, 154, 30, 59, 133, 26, 119, 226, 76, 164, 47, 227, 106, 224, 81, 14, 43, 163, 158, 20], [132, 42, 107, 157, 53, 133, 121, 119, 146, 16, 76, 172, 30, 244, 142, 7, 131, 166, 108, 158, 235], [177, 221, 154, 114, 53, 133, 26, 119, 162, 122, 130, 253, 81, 106, 254, 81, 128, 246, 62, 224, 20], [142, 144, 159, 149, 67, 138, 8, 120, 226, 122, 130, 204, 81, 75, 230, 119, 38, 168, 114, 224, 34], [159, 162, 210, 146, 51, 119, 150, 101, 162, 101, 18, 33, 22, 87, 221, 227, 191, 193, 160, 145, 150], [177, 221, 154, 229, 58, 186, 35, 119, 162, 122, 13, 253, 81, 235, 228, 81, 113, 246, 62, 158, 20], [177, 110, 100, 157, 59, 133, 43, 119, 146, 122, 61, 239, 73, 211, 226, 174, 115, 210, 163, 141, 106], [177, 106, 161, 146, 98, 133, 13, 94, 162, 122, 98, 206, 162, 106, 236, 81, 128, 246, 62, 158, 20], [177, 221, 154, 114, 61, 133, 26, 232, 146, 76, 130, 253, 81, 22, 228, 81, 128, 246, 118, 158, 46], [177, 204, 98, 114, 59, 133, 167, 119, 164, 122, 113, 47, 81, 148, 254, 1, 128, 246, 163, 224, 188], [177, 200, 154, 146, 59, 133, 167, 119, 219, 122, 245, 160, 86, 106, 254, 89, 128, 246, 163, 224, 20], [254, 221, 58, 229, 58, 136, 26, 118, 250, 105, 158, 75, 47, 124, 192, 81, 232, 1, 119, 232, 208], [193, 42, 100, 146, 146, 28, 62, 8, 170, 166, 9, 74, 78, 18, 142, 7, 38, 22, 116, 158, 221], [177, 221, 161, 16, 53, 133, 167, 120, 162, 122, 130, 206, 81, 31, 236, 174, 128, 150, 156, 158, 34], [199, 181, 132, 146, 51, 133, 62, 119, 162, 122, 50, 222, 22, 224, 226, 226, 128, 88, 156, 238, 20], [134, 237, 159, 146, 58, 133, 26, 119, 146, 122, 164, 253, 81, 106, 236, 86, 14, 159, 117, 158, 11], [177, 221, 154, 229, 58, 186, 35, 119, 162, 122, 13, 237, 81, 235, 226, 81, 113, 150, 62, 158, 20], [177, 204, 17, 107, 164, 133, 35, 119, 195, 122, 130, 253, 221, 235, 228, 226, 14, 223, 118, 224, 150], [227, 220, 154, 146, 4, 133, 26, 94, 226, 122, 130, 253, 81, 106, 228, 42, 137, 150, 114, 158, 20], [254, 204, 17, 157, 58, 133, 55, 119, 195, 122, 50, 206, 81, 224, 162, 89, 14, 88, 62, 238, 150], [225, 181, 132, 90, 105, 75, 5, 119, 108, 75, 178, 208, 103, 104, 254, 89, 128, 88, 163, 146, 20], [184, 207, 159, 17, 90, 148, 26, 232, 143, 122, 245, 74, 82, 151, 223, 8, 57, 219, 241, 162, 11], [254, 252, 238, 23, 4, 5, 187, 72, 252, 140, 133, 1, 27, 84, 157, 6, 62, 100, 77, 134, 203], [215, 206, 145, 76, 50, 206, 113, 159, 180, 158, 224, 61, 46, 225, 172, 239, 80, 185, 127, 152, 152], [208, 212, 197, 144, 10, 196, 206, 244, 76, 224, 227, 19, 80, 104, 162, 102, 161, 233, 145, 31, 14], [182, 33, 156, 64, 45, 107, 34, 123, 180, 101, 242, 222, 106, 119, 16, 133, 141, 220, 117, 225, 42], [177, 205, 18, 250, 91, 197, 127, 119, 146, 122, 17, 161, 173, 235, 235, 29, 113, 68, 118, 227, 150], [149, 45, 136, 188, 42, 181, 26, 127, 222, 105, 68, 135, 225, 230, 231, 95, 246, 19, 132, 236, 224], [225, 70, 164, 121, 75, 181, 59, 19, 119, 255, 197, 210, 113, 104, 208, 85, 223, 124, 107, 183, 190], [237, 213, 215, 145, 214, 75, 249, 104, 124, 83, 204, 211, 25, 111, 254, 32, 253, 64, 33, 45, 19], [228, 255, 160, 228, 186, 147, 13, 38, 142, 244, 246, 76, 224, 199, 195, 203, 171, 51, 0, 51, 26], [149, 221, 136, 188, 42, 181, 26, 232, 222, 105, 130, 135, 81, 22, 228, 81, 246, 246, 118, 158, 224], [177, 45, 154, 114, 61, 133, 26, 127, 146, 76, 68, 253, 225, 230, 231, 95, 128, 19, 132, 236, 46], [254, 144, 17, 149, 58, 138, 55, 120, 226, 122, 50, 204, 81, 75, 162, 89, 14, 88, 62, 238, 34], [142, 204, 159, 157, 67, 133, 8, 119, 195, 122, 130, 206, 81, 224, 230, 119, 38, 168, 114, 224, 150], [177, 221, 154, 16, 53, 133, 167, 119, 162, 122, 13, 237, 81, 31, 236, 81, 128, 150, 156, 158, 20], [177, 221, 161, 229, 58, 186, 35, 120, 162, 122, 130, 206, 81, 235, 226, 174, 113, 150, 62, 158, 34], [177, 221, 154, 146, 58, 133, 26, 112, 226, 122, 130, 253, 81, 106, 228, 86, 137, 223, 118, 158, 20], [227, 220, 154, 146, 4, 133, 26, 94, 146, 122, 130, 253, 81, 106, 236, 42, 113, 150, 114, 158, 20], [173, 103, 219, 173, 70, 26, 198, 120, 124, 74, 11, 206, 83, 224, 226, 174, 128, 232, 156, 158, 20], [177, 221, 155, 16, 59, 133, 167, 8, 162, 122, 130, 143, 81, 101, 236, 20, 128, 208, 62, 238, 17], [142, 204, 159, 114, 67, 138, 26, 120, 162, 122, 130, 204, 81, 104, 230, 119, 128, 168, 114, 224, 34], [177, 144, 154, 149, 53, 133, 8, 119, 226, 122, 130, 47, 81, 75, 253, 81, 38, 246, 163, 224, 20], [177, 221, 154, 16, 59, 133, 167, 120, 226, 122, 130, 47, 81, 101, 224, 174, 14, 43, 156, 158, 17], [132, 237, 155, 30, 59, 133, 26, 119, 162, 76, 164, 206, 227, 106, 236, 86, 128, 208, 163, 158, 11], [254, 221, 161, 157, 58, 138, 26, 119, 146, 122, 127, 206, 81, 106, 236, 81, 128, 223, 163, 158, 17], [254, 181, 159, 157, 53, 138, 62, 136, 204, 122, 245, 170, 81, 87, 236, 89, 121, 22, 163, 141, 20], [177, 204, 98, 114, 59, 133, 5, 119, 164, 122, 113, 47, 81, 148, 254, 1, 128, 246, 163, 224, 188], [225, 193, 155, 90, 105, 75, 167, 119, 108, 75, 178, 208, 103, 112, 236, 89, 93, 88, 163, 146, 17], [177, 204, 164, 114, 75, 133, 167, 19, 164, 122, 113, 47, 81, 148, 254, 85, 128, 246, 163, 224, 190], [225, 70, 98, 121, 59, 181, 59, 119, 119, 255, 197, 210, 113, 104, 208, 1, 223, 124, 107, 183, 188], [132, 42, 154, 30, 4, 133, 26, 119, 226, 76, 164, 172, 30, 244, 142, 42, 14, 166, 108, 158, 11], [132, 221, 107, 157, 53, 133, 121, 119, 146, 16, 76, 174, 227, 22, 224, 7, 131, 150, 163, 158, 235], [193, 46, 100, 146, 59, 3, 62, 3, 170, 95, 92, 47, 81, 18, 239, 91, 114, 22, 116, 158, 221], [254, 221, 155, 229, 146, 133, 167, 119, 146, 122, 147, 80, 78, 101, 236, 89, 38, 208, 117, 158, 17], [227, 221, 160, 117, 58, 133, 17, 231, 142, 244, 246, 76, 227, 199, 228, 81, 57, 210, 239, 158, 26], [228, 255, 160, 228, 186, 147, 13, 38, 219, 122, 130, 160, 224, 106, 195, 203, 171, 51, 0, 51, 156], [184, 221, 154, 30, 90, 148, 26, 232, 143, 76, 164, 222, 82, 151, 223, 42, 57, 150, 241, 162, 11], [132, 207, 159, 17, 4, 133, 26, 119, 226, 122, 245, 74, 227, 22, 224, 8, 14, 219, 163, 158, 11], [132, 221, 154, 30, 59, 133, 26, 119, 226, 76, 164, 47, 227, 106, 224, 81, 14, 43, 163, 158, 20], [132, 237, 154, 30, 4, 133, 26, 119, 226, 76, 164, 222, 227, 22, 224, 42, 14, 150, 163, 158, 11], [177, 204, 145, 76, 164, 186, 62, 119, 146, 122, 13, 61, 221, 225, 228, 239, 14, 88, 118, 224, 150], [215, 206, 17, 229, 50, 206, 113, 159, 180, 158, 224, 222, 46, 235, 172, 226, 80, 185, 127, 152, 152], [132, 204, 107, 157, 53, 186, 121, 119, 146, 16, 76, 172, 30, 244, 142, 7, 131, 166, 118, 158, 235], [177, 42, 17, 229, 164, 133, 62, 119, 146, 122, 13, 222, 221, 235, 228, 226, 14, 88, 108, 224, 150], [199, 181, 221, 107, 51, 119, 142, 101, 229, 122, 245, 206, 22, 224, 254, 226, 113, 223, 156, 238, 150], [227, 169, 17, 146, 53, 138, 62, 119, 162, 122, 130, 222, 221, 235, 226, 89, 128, 233, 198, 158, 150], [199, 181, 132, 107, 51, 133, 5, 119, 162, 75, 127, 222, 103, 87, 226, 89, 14, 88, 198, 224, 20], [225, 221, 154, 90, 105, 75, 62, 119, 108, 122, 178, 208, 22, 104, 254, 226, 128, 223, 163, 146, 150]]";
        List<int[]> list = JSON.parseArray(genes, int[].class);
        // 加一个人工干扰基因
        weightIndividuals.add(new WeightIndividual(new int[]{11,1,1,5,-2,-1,2,8,1,1,11,-2,-1,5,8,2,2,15,0,2,5},128));
        for (int i = 0; i < list.size() - 1; i++) {
            weightIndividuals.add(new WeightIndividual(list.get(i)));
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
        log.info("正在计算适应度...");
        double all_score = 0.0;
        // 计算总分 及适应度
        for (Map.Entry<WeightIndividual, List<Gameplayer>> entry : listMap.entrySet()) {
            WeightIndividual individual = entry.getKey();
            List<Gameplayer> gameplayers = entry.getValue();
            double winners = 0.0,allcount = 0.0;
            for (Gameplayer gameplayer : gameplayers) {
                allcount += 2.0;
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
            individual.setWinness(winners / allcount);
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
        log.info("正在选择 ...");
        // 保留的下一代种群
        this.newIndividuals = new LinkedHashSet<>();
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
        log.info("正在计算交叉...");
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
        log.info("正在进行变异...");
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
        return v >= Constant.convergence;
    }

    /**
     * 遗传迭代方法
     * @param args
     */
    public static void main(String[] args) {
        GeneticAlgorithm algorithm = new GeneticAlgorithm();
        algorithm.initIndividuals();
        List<String> result = new ArrayList<>();
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
            algorithm.weightIndividuals.stream().forEach((e)-> result.add(Arrays.toString(e.getSrcs())));
            log.info("当前数据 :" + result);
            result.clear();
            // 判断是否继续迭代
        } while (solution);
        log.info("迭代结束! ");
        log.info("迭代结束! ");
        log.info(Arrays.toString(algorithm.best_weight.getSrcs()));
    }


}
