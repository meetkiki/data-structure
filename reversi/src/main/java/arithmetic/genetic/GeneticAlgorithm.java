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
        String genes = "[[250, 248, 156, 12, 255, 130, 163, 4, 245, 128, 255, 141, 147, 128, 219, 202, 148, 162, 133, 244, 110], [239, 248, 145, 155, 192, 130, 156, 88, 251, 127, 240, 149, 129, 125, 184, 23, 33, 184, 134, 148, 177], [172, 223, 23, 139, 32, 123, 236, 150, 244, 130, 146, 126, 165, 233, 250, 1, 234, 239, 242, 252, 132], [232, 252, 158, 12, 190, 130, 148, 88, 245, 128, 209, 15, 129, 224, 201, 128, 106, 146, 105, 190, 132], [234, 167, 19, 23, 158, 56, 97, 238, 17, 139, 227, 179, 158, 44, 216, 121, 106, 161, 43, 223, 59], [191, 201, 91, 139, 190, 189, 236, 136, 74, 130, 209, 85, 227, 231, 250, 207, 213, 169, 209, 140, 175], [156, 155, 253, 12, 128, 130, 148, 216, 209, 127, 240, 141, 95, 128, 40, 246, 106, 239, 30, 244, 103], [156, 254, 194, 209, 128, 147, 16, 123, 244, 108, 26, 219, 237, 224, 83, 1, 88, 230, 29, 148, 110], [250, 248, 88, 156, 255, 142, 163, 4, 136, 128, 209, 219, 145, 224, 219, 23, 148, 162, 184, 244, 175], [156, 223, 253, 206, 128, 130, 63, 216, 251, 127, 240, 149, 95, 125, 46, 246, 33, 239, 91, 244, 177], [239, 248, 94, 155, 94, 130, 16, 88, 19, 172, 209, 144, 129, 224, 250, 128, 158, 169, 209, 252, 175], [239, 222, 94, 155, 94, 130, 16, 88, 19, 172, 209, 144, 129, 224, 250, 128, 158, 169, 209, 148, 175], [225, 203, 111, 27, 207, 130, 16, 88, 132, 191, 207, 245, 155, 224, 191, 246, 106, 136, 89, 252, 46], [146, 50, 210, 106, 89, 140, 235, 39, 121, 165, 77, 223, 120, 186, 133, 108, 118, 31, 232, 178, 38], [163, 248, 36, 76, 192, 249, 218, 163, 244, 128, 243, 245, 129, 58, 208, 254, 148, 162, 88, 215, 110], [176, 7, 138, 160, 87, 230, 8, 21, 215, 111, 208, 240, 181, 125, 46, 246, 223, 237, 172, 115, 247], [236, 206, 131, 221, 195, 196, 190, 89, 91, 103, 207, 210, 97, 1, 41, 45, 146, 162, 196, 197, 78], [211, 248, 36, 238, 165, 249, 231, 163, 244, 162, 82, 101, 174, 128, 207, 254, 7, 162, 88, 215, 254], [172, 104, 238, 79, 165, 117, 39, 2, 244, 82, 82, 122, 9, 157, 116, 240, 100, 242, 246, 157, 54], [156, 223, 253, 206, 128, 130, 63, 216, 251, 127, 240, 149, 95, 125, 40, 246, 33, 239, 91, 244, 80], [159, 248, 145, 238, 190, 105, 157, 216, 132, 129, 207, 149, 129, 224, 201, 207, 213, 162, 134, 244, 177], [163, 203, 158, 76, 207, 11, 193, 150, 244, 128, 243, 245, 129, 128, 208, 246, 106, 162, 88, 252, 110], [237, 223, 172, 206, 190, 130, 63, 88, 251, 127, 209, 149, 226, 125, 255, 207, 106, 184, 91, 252, 80], [215, 252, 238, 76, 202, 11, 153, 153, 213, 82, 15, 72, 9, 128, 116, 246, 224, 249, 30, 157, 54], [237, 248, 172, 155, 207, 11, 16, 88, 244, 208, 255, 141, 147, 128, 199, 207, 88, 184, 30, 252, 175], [156, 155, 253, 238, 128, 130, 231, 216, 209, 127, 82, 141, 95, 128, 40, 254, 106, 162, 88, 244, 254], [191, 149, 172, 36, 190, 189, 109, 136, 74, 130, 54, 85, 227, 231, 250, 207, 213, 169, 209, 244, 132], [239, 248, 194, 155, 94, 130, 16, 88, 244, 172, 209, 144, 129, 224, 250, 128, 158, 169, 209, 252, 175], [215, 104, 238, 47, 202, 117, 153, 2, 213, 82, 15, 72, 9, 157, 116, 240, 224, 249, 246, 157, 54], [191, 248, 212, 12, 255, 99, 148, 233, 251, 129, 246, 108, 130, 73, 201, 207, 101, 236, 242, 154, 177], [237, 252, 95, 81, 255, 11, 148, 88, 244, 128, 240, 219, 130, 224, 201, 126, 33, 234, 30, 252, 132], [163, 202, 145, 76, 192, 130, 218, 153, 243, 128, 243, 245, 129, 58, 208, 53, 148, 162, 164, 252, 110], [156, 149, 154, 178, 74, 105, 63, 216, 251, 191, 54, 144, 186, 224, 250, 246, 149, 239, 190, 244, 132], [236, 216, 158, 76, 127, 129, 32, 88, 244, 128, 243, 245, 88, 69, 102, 43, 46, 162, 99, 252, 78], [171, 248, 162, 76, 134, 130, 223, 201, 244, 128, 203, 108, 150, 97, 208, 57, 101, 184, 187, 163, 110], [156, 248, 145, 227, 79, 104, 156, 216, 244, 127, 246, 149, 156, 223, 208, 246, 149, 184, 91, 244, 80], [237, 230, 172, 12, 190, 186, 148, 47, 251, 188, 142, 141, 147, 128, 201, 191, 157, 173, 137, 252, 132], [206, 230, 93, 147, 70, 186, 144, 47, 20, 188, 142, 136, 134, 184, 249, 191, 157, 173, 137, 227, 174], [234, 173, 172, 124, 128, 11, 55, 215, 244, 186, 117, 117, 159, 135, 245, 113, 88, 162, 151, 173, 222], [250, 149, 95, 67, 128, 130, 163, 5, 46, 127, 209, 241, 237, 224, 219, 202, 100, 244, 171, 34, 110], [211, 201, 23, 139, 32, 189, 236, 169, 34, 130, 209, 144, 129, 231, 250, 157, 134, 169, 209, 140, 175], [237, 252, 172, 12, 190, 11, 148, 93, 251, 110, 255, 141, 147, 128, 201, 207, 106, 184, 30, 252, 132], [131, 222, 254, 15, 190, 158, 58, 168, 220, 201, 145, 168, 81, 3, 237, 49, 211, 206, 36, 226, 53], [237, 252, 172, 81, 255, 11, 148, 88, 244, 128, 209, 219, 130, 224, 201, 207, 106, 184, 30, 252, 132], [237, 252, 172, 12, 190, 130, 148, 93, 251, 110, 255, 141, 95, 128, 40, 207, 106, 184, 30, 252, 177], [248, 199, 167, 36, 145, 157, 109, 46, 149, 72, 120, 52, 72, 225, 194, 106, 18, 182, 188, 116, 210], [172, 199, 174, 109, 8, 153, 109, 93, 225, 176, 120, 52, 185, 225, 165, 246, 18, 182, 249, 126, 210], [250, 248, 156, 12, 190, 130, 163, 4, 245, 128, 255, 141, 147, 128, 219, 202, 148, 162, 133, 244, 110], [215, 241, 36, 47, 202, 198, 153, 172, 213, 162, 15, 72, 169, 128, 207, 254, 224, 249, 91, 215, 1], [159, 248, 145, 238, 190, 105, 157, 93, 251, 129, 54, 149, 72, 224, 201, 207, 213, 239, 110, 244, 177], [227, 198, 234, 27, 195, 157, 109, 109, 139, 192, 195, 153, 79, 67, 87, 17, 154, 184, 130, 112, 200], [219, 251, 212, 209, 163, 130, 208, 39, 245, 128, 179, 227, 130, 30, 40, 202, 33, 93, 133, 148, 110], [237, 252, 172, 12, 190, 11, 148, 88, 244, 128, 255, 141, 147, 128, 201, 207, 106, 184, 30, 252, 132], [237, 223, 95, 206, 190, 130, 63, 88, 251, 127, 240, 149, 226, 125, 255, 126, 33, 234, 91, 252, 80], [163, 104, 145, 47, 207, 117, 218, 2, 244, 128, 243, 245, 129, 157, 208, 240, 106, 184, 246, 252, 132], [156, 248, 105, 244, 73, 43, 224, 40, 235, 128, 209, 123, 237, 232, 208, 202, 230, 181, 242, 244, 132], [156, 202, 129, 206, 73, 130, 63, 88, 244, 128, 126, 149, 95, 125, 201, 86, 41, 162, 242, 159, 195], [239, 248, 94, 155, 94, 130, 16, 88, 19, 172, 209, 144, 165, 224, 250, 128, 158, 169, 209, 252, 175], [232, 248, 158, 155, 124, 130, 16, 88, 191, 208, 209, 145, 26, 224, 199, 128, 88, 146, 105, 190, 175], [250, 248, 95, 81, 255, 130, 163, 4, 245, 128, 209, 241, 130, 224, 219, 202, 100, 162, 133, 244, 110], [156, 223, 253, 206, 128, 130, 63, 216, 251, 1, 240, 149, 95, 97, 124, 222, 33, 202, 91, 244, 177], [156, 60, 253, 238, 128, 130, 63, 88, 244, 128, 207, 149, 95, 125, 201, 66, 148, 162, 91, 252, 177], [219, 251, 88, 156, 128, 142, 208, 39, 136, 191, 179, 227, 145, 30, 40, 23, 33, 93, 184, 148, 175], [225, 223, 111, 139, 207, 130, 236, 88, 132, 130, 207, 126, 165, 224, 250, 246, 106, 136, 242, 252, 46], [250, 248, 156, 81, 190, 130, 163, 4, 245, 128, 209, 219, 147, 224, 219, 202, 148, 162, 133, 244, 110], [156, 155, 253, 12, 128, 130, 148, 216, 209, 127, 240, 141, 95, 128, 40, 246, 33, 234, 30, 244, 103], [247, 255, 198, 88, 86, 145, 3, 83, 4, 208, 5, 55, 132, 94, 236, 169, 136, 182, 68, 146, 121], [239, 179, 214, 33, 191, 234, 221, 233, 243, 255, 181, 102, 227, 64, 184, 188, 106, 255, 224, 148, 175], [240, 179, 214, 33, 191, 234, 221, 233, 252, 255, 181, 102, 227, 64, 234, 188, 198, 255, 224, 152, 69], [156, 201, 91, 139, 74, 11, 236, 93, 251, 191, 209, 144, 72, 128, 201, 207, 149, 239, 110, 140, 175], [176, 248, 88, 160, 87, 230, 163, 21, 136, 111, 208, 240, 181, 97, 124, 222, 148, 237, 172, 115, 247], [239, 248, 145, 155, 192, 130, 156, 88, 243, 191, 240, 132, 129, 106, 184, 53, 106, 184, 134, 148, 175], [152, 185, 66, 111, 92, 239, 32, 238, 118, 66, 126, 123, 204, 155, 208, 23, 165, 240, 188, 226, 148], [229, 248, 156, 81, 128, 133, 18, 4, 136, 191, 1, 219, 19, 224, 173, 57, 148, 239, 168, 149, 110], [156, 149, 95, 67, 128, 130, 16, 5, 46, 127, 234, 123, 237, 86, 255, 7, 88, 244, 171, 34, 104], [172, 241, 36, 79, 165, 198, 39, 172, 244, 162, 82, 122, 169, 128, 207, 254, 100, 242, 91, 215, 1], [163, 252, 145, 76, 207, 11, 218, 153, 244, 128, 243, 245, 129, 128, 208, 246, 106, 184, 30, 252, 132], [152, 248, 145, 227, 79, 104, 32, 216, 244, 128, 240, 132, 204, 223, 208, 126, 149, 234, 242, 252, 80], [151, 249, 169, 196, 68, 209, 156, 136, 195, 91, 35, 235, 33, 61, 167, 82, 95, 145, 11, 21, 222], [232, 145, 164, 239, 130, 198, 111, 237, 9, 151, 132, 139, 227, 35, 141, 6, 106, 165, 43, 173, 60], [158, 197, 107, 4, 206, 130, 16, 88, 122, 191, 193, 235, 227, 224, 160, 246, 104, 136, 101, 134, 38], [141, 29, 1, 31, 237, 246, 238, 182, 164, 231, 176, 96, 149, 218, 182, 230, 84, 97, 163, 1, 160], [163, 213, 153, 178, 112, 48, 119, 216, 182, 191, 73, 252, 218, 249, 234, 137, 157, 254, 158, 244, 123], [128, 146, 1, 30, 189, 225, 73, 191, 221, 253, 78, 180, 86, 4, 146, 168, 50, 211, 43, 146, 170], [160, 198, 20, 24, 195, 156, 98, 109, 173, 63, 252, 150, 50, 44, 87, 19, 102, 70, 125, 15, 200], [156, 5, 17, 116, 214, 163, 239, 90, 120, 15, 142, 203, 238, 12, 86, 77, 6, 14, 242, 46, 87], [194, 38, 69, 227, 217, 225, 211, 58, 120, 148, 223, 194, 68, 101, 4, 105, 227, 10, 146, 125, 29], [254, 46, 138, 233, 159, 138, 53, 209, 71, 179, 108, 151, 223, 217, 182, 42, 193, 109, 129, 108, 156], [189, 103, 179, 97, 114, 88, 151, 186, 142, 75, 225, 139, 8, 142, 212, 45, 77, 239, 155, 113, 129], [175, 105, 238, 81, 94, 21, 68, 253, 136, 45, 154, 69, 137, 229, 11, 232, 4, 243, 22, 157, 117], [141, 29, 1, 31, 237, 246, 238, 88, 164, 231, 176, 96, 149, 218, 182, 230, 84, 184, 163, 1, 160], [239, 248, 145, 155, 192, 130, 156, 182, 243, 191, 240, 132, 129, 106, 184, 53, 106, 97, 134, 148, 175], [156, 201, 91, 139, 74, 11, 236, 4, 251, 128, 209, 144, 147, 128, 201, 207, 148, 239, 110, 244, 175], [250, 248, 156, 12, 255, 130, 163, 93, 245, 191, 255, 141, 72, 128, 219, 202, 149, 162, 133, 140, 110], [156, 201, 253, 12, 74, 11, 236, 93, 209, 191, 209, 144, 72, 128, 201, 207, 106, 239, 110, 140, 103], [156, 155, 91, 139, 128, 130, 148, 216, 251, 127, 240, 141, 95, 128, 40, 246, 149, 239, 30, 244, 175], [239, 223, 94, 206, 128, 130, 16, 88, 19, 172, 209, 144, 165, 125, 250, 246, 158, 169, 209, 244, 175], [156, 248, 253, 155, 94, 130, 63, 216, 251, 127, 240, 149, 95, 224, 40, 128, 33, 239, 91, 252, 80], [172, 241, 36, 79, 94, 130, 39, 172, 244, 162, 82, 122, 129, 128, 207, 254, 100, 169, 91, 148, 1], [239, 222, 94, 155, 165, 198, 16, 88, 19, 172, 209, 144, 169, 224, 250, 128, 158, 242, 209, 215, 175], [232, 202, 164, 239, 192, 130, 111, 237, 9, 151, 132, 245, 129, 35, 208, 53, 106, 165, 43, 173, 110], [163, 145, 145, 76, 130, 198, 218, 153, 243, 128, 243, 139, 227, 58, 141, 6, 148, 162, 164, 252, 60], [219, 251, 158, 156, 190, 142, 148, 39, 245, 191, 179, 227, 145, 30, 40, 128, 33, 146, 105, 148, 175], [232, 252, 88, 12, 128, 130, 208, 88, 136, 128, 209, 15, 129, 224, 201, 23, 106, 93, 184, 190, 132], [247, 255, 198, 88, 86, 145, 3, 2, 4, 208, 5, 55, 132, 94, 116, 169, 136, 182, 68, 146, 54], [172, 104, 238, 79, 165, 117, 39, 83, 244, 82, 82, 122, 9, 157, 236, 240, 100, 242, 246, 157, 121], [156, 155, 253, 238, 128, 130, 231, 216, 209, 127, 208, 141, 95, 128, 40, 222, 106, 162, 88, 244, 254], [176, 248, 88, 160, 87, 230, 163, 21, 136, 111, 82, 240, 181, 97, 124, 254, 148, 237, 172, 115, 247], [131, 199, 254, 15, 190, 153, 58, 93, 220, 201, 120, 52, 81, 3, 165, 49, 18, 206, 36, 226, 53], [172, 222, 174, 109, 8, 158, 109, 168, 225, 176, 145, 168, 185, 225, 237, 246, 211, 182, 249, 126, 210], [232, 145, 164, 239, 130, 198, 236, 237, 34, 151, 132, 139, 129, 35, 141, 6, 106, 165, 43, 173, 60], [211, 201, 23, 139, 32, 189, 111, 169, 9, 130, 209, 144, 227, 231, 250, 157, 134, 169, 209, 140, 175], [156, 155, 253, 238, 128, 130, 39, 216, 209, 127, 82, 122, 169, 128, 40, 254, 106, 162, 88, 215, 254], [172, 241, 36, 79, 165, 198, 231, 172, 244, 162, 82, 141, 95, 128, 207, 254, 100, 242, 91, 244, 1], [194, 38, 69, 227, 130, 198, 211, 58, 9, 151, 223, 194, 227, 101, 4, 105, 106, 10, 43, 125, 29], [232, 145, 164, 239, 217, 225, 111, 237, 120, 148, 132, 139, 68, 35, 141, 6, 227, 165, 146, 173, 60], [250, 241, 156, 12, 255, 198, 163, 4, 245, 128, 255, 141, 147, 128, 219, 254, 148, 242, 91, 244, 110], [172, 248, 36, 79, 165, 130, 39, 172, 244, 162, 82, 122, 169, 128, 207, 202, 100, 162, 133, 215, 1], [156, 60, 253, 139, 128, 130, 63, 88, 244, 128, 207, 149, 165, 125, 201, 66, 148, 162, 91, 252, 177], [225, 223, 111, 238, 207, 130, 236, 88, 132, 130, 207, 126, 95, 224, 250, 246, 106, 136, 242, 252, 46], [163, 203, 194, 76, 207, 11, 193, 150, 244, 128, 26, 219, 237, 224, 83, 246, 106, 230, 88, 252, 110], [156, 254, 158, 209, 128, 147, 16, 123, 244, 108, 243, 245, 129, 128, 208, 1, 88, 162, 29, 148, 110], [172, 241, 179, 79, 165, 88, 39, 172, 244, 75, 225, 122, 169, 142, 212, 45, 77, 239, 155, 113, 129], [189, 103, 36, 97, 114, 198, 151, 186, 142, 162, 82, 139, 8, 128, 207, 254, 100, 242, 91, 215, 1], [219, 185, 88, 111, 128, 239, 208, 39, 136, 191, 126, 227, 204, 30, 40, 23, 165, 93, 184, 226, 175], [152, 251, 66, 156, 92, 142, 32, 238, 118, 66, 179, 123, 145, 155, 208, 23, 33, 240, 188, 148, 148], [239, 248, 94, 155, 94, 99, 16, 88, 251, 172, 246, 108, 130, 73, 201, 207, 158, 236, 209, 148, 175], [191, 222, 212, 12, 255, 130, 148, 233, 19, 129, 209, 144, 129, 224, 250, 128, 101, 169, 242, 154, 177]]";
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
