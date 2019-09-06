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
        String genes = "[[223, 244, 161, 93, 27, 132, 241, 188, 174, 133, 221, 158, 166, 68, 248, 133, 76, 203, 114, 243, 221], [231, 244, 244, 28, 24, 156, 1, 205, 29, 203, 98, 48, 50, 181, 248, 2, 77, 246, 250, 166, 12], [146, 139, 197, 90, 38, 188, 113, 248, 168, 215, 205, 50, 210, 65, 152, 93, 117, 87, 177, 157, 112], [196, 172, 139, 221, 80, 1, 217, 142, 64, 142, 193, 162, 21, 88, 248, 3, 77, 171, 107, 215, 144], [196, 172, 139, 221, 80, 1, 217, 142, 29, 217, 193, 162, 252, 88, 248, 3, 77, 212, 107, 215, 144], [209, 245, 163, 189, 27, 101, 49, 188, 2, 132, 98, 254, 47, 43, 254, 200, 183, 246, 23, 156, 178], [231, 245, 253, 109, 24, 109, 241, 205, 155, 133, 62, 47, 9, 124, 218, 240, 87, 224, 180, 156, 180], [223, 244, 161, 93, 27, 132, 241, 188, 174, 217, 221, 158, 166, 68, 248, 133, 76, 203, 114, 243, 221], [208, 176, 196, 65, 214, 101, 112, 111, 146, 203, 98, 5, 47, 128, 254, 69, 171, 246, 117, 197, 77], [225, 251, 109, 198, 27, 156, 152, 111, 246, 84, 98, 211, 67, 84, 201, 117, 66, 212, 114, 173, 240], [196, 172, 156, 221, 80, 1, 92, 142, 36, 142, 193, 162, 21, 88, 253, 3, 77, 171, 107, 215, 242], [223, 245, 163, 66, 31, 16, 49, 67, 175, 186, 98, 254, 134, 43, 254, 132, 183, 171, 23, 82, 18], [223, 244, 188, 93, 27, 132, 196, 97, 175, 133, 221, 158, 117, 68, 60, 0, 77, 212, 114, 243, 106], [208, 245, 185, 255, 27, 101, 39, 111, 246, 105, 240, 5, 47, 128, 254, 69, 171, 246, 117, 197, 143], [213, 234, 227, 77, 229, 101, 160, 117, 163, 246, 222, 158, 167, 68, 248, 10, 179, 212, 114, 154, 224], [223, 244, 163, 93, 27, 101, 160, 188, 175, 133, 221, 158, 67, 68, 248, 133, 77, 212, 114, 173, 18], [226, 201, 131, 165, 110, 172, 205, 144, 39, 68, 185, 174, 134, 83, 189, 6, 110, 241, 118, 108, 21], [223, 244, 217, 93, 27, 132, 241, 94, 174, 153, 221, 158, 207, 68, 248, 133, 76, 203, 114, 243, 221], [209, 238, 227, 77, 21, 101, 152, 117, 29, 147, 74, 158, 252, 84, 18, 156, 162, 212, 114, 154, 224], [209, 10, 154, 203, 21, 156, 152, 111, 29, 217, 240, 48, 252, 84, 218, 156, 162, 212, 114, 33, 240], [209, 10, 154, 255, 107, 156, 241, 111, 246, 153, 221, 158, 252, 84, 218, 156, 177, 212, 114, 33, 240], [223, 243, 253, 51, 27, 58, 160, 45, 4, 133, 209, 194, 46, 68, 192, 200, 248, 212, 103, 156, 18], [231, 245, 194, 203, 21, 109, 152, 14, 229, 217, 127, 47, 8, 112, 218, 172, 162, 224, 180, 166, 159], [223, 251, 161, 93, 27, 132, 22, 111, 246, 133, 221, 158, 67, 68, 201, 133, 76, 203, 114, 173, 221], [202, 10, 163, 28, 40, 2, 217, 245, 163, 217, 240, 95, 2, 181, 248, 2, 78, 255, 250, 33, 18], [223, 234, 244, 51, 27, 58, 22, 45, 4, 133, 209, 158, 46, 68, 192, 200, 248, 212, 103, 173, 18], [245, 252, 104, 56, 88, 82, 172, 94, 30, 225, 205, 189, 133, 220, 198, 124, 138, 167, 29, 218, 106], [193, 141, 44, 126, 122, 165, 49, 174, 79, 93, 231, 33, 146, 90, 40, 69, 73, 54, 114, 206, 16], [209, 238, 154, 77, 21, 101, 152, 117, 29, 147, 74, 158, 252, 84, 18, 156, 162, 212, 114, 154, 224], [196, 171, 156, 109, 88, 0, 92, 142, 155, 203, 98, 93, 20, 88, 254, 3, 171, 224, 117, 215, 242], [154, 245, 50, 198, 35, 101, 22, 98, 58, 133, 98, 211, 67, 105, 134, 117, 77, 233, 114, 173, 255], [231, 244, 185, 51, 19, 156, 253, 96, 197, 105, 127, 48, 50, 75, 174, 244, 241, 207, 69, 166, 12], [248, 244, 161, 59, 27, 16, 244, 64, 98, 133, 221, 20, 134, 56, 198, 27, 143, 162, 114, 243, 223], [223, 238, 163, 68, 44, 156, 22, 61, 117, 133, 222, 211, 54, 105, 195, 240, 42, 246, 125, 184, 192], [209, 172, 156, 221, 21, 1, 92, 142, 29, 142, 240, 162, 21, 68, 253, 156, 248, 212, 107, 33, 242], [231, 234, 253, 109, 27, 109, 241, 84, 155, 213, 13, 47, 136, 112, 218, 240, 87, 224, 180, 33, 180], [225, 251, 226, 198, 27, 101, 160, 111, 117, 133, 98, 211, 76, 105, 134, 117, 77, 233, 113, 156, 41], [202, 10, 163, 28, 40, 2, 217, 245, 163, 133, 240, 95, 2, 181, 248, 2, 78, 255, 250, 33, 18], [231, 176, 196, 65, 214, 109, 172, 111, 146, 153, 127, 47, 47, 220, 248, 244, 87, 246, 18, 197, 106], [223, 245, 163, 109, 27, 101, 160, 188, 175, 133, 221, 47, 9, 68, 218, 240, 77, 212, 180, 173, 18], [223, 244, 163, 59, 78, 101, 244, 188, 175, 133, 221, 158, 67, 68, 198, 133, 77, 212, 171, 139, 223], [223, 5, 135, 93, 78, 132, 241, 188, 174, 131, 188, 158, 166, 68, 248, 133, 76, 203, 171, 139, 221], [196, 171, 156, 157, 80, 0, 92, 142, 36, 142, 193, 93, 20, 88, 254, 3, 78, 168, 107, 215, 242], [202, 169, 176, 72, 40, 2, 217, 45, 163, 217, 240, 19, 83, 208, 9, 2, 78, 101, 250, 33, 205], [209, 245, 188, 93, 74, 230, 155, 84, 239, 213, 247, 39, 47, 82, 165, 169, 191, 216, 117, 76, 7], [231, 185, 203, 109, 88, 109, 172, 94, 155, 153, 127, 47, 103, 220, 248, 244, 87, 224, 18, 218, 106], [132, 243, 87, 173, 232, 110, 75, 167, 150, 127, 4, 190, 229, 33, 212, 124, 139, 91, 99, 200, 106], [210, 73, 142, 52, 148, 37, 254, 51, 225, 102, 52, 111, 76, 47, 248, 11, 42, 106, 129, 66, 205], [224, 233, 164, 51, 24, 69, 8, 45, 229, 86, 127, 158, 46, 68, 246, 200, 87, 241, 91, 166, 51], [202, 238, 163, 28, 40, 101, 217, 245, 163, 147, 74, 95, 2, 181, 248, 2, 78, 255, 250, 154, 18], [223, 245, 200, 56, 27, 109, 160, 50, 170, 165, 221, 158, 1, 124, 248, 179, 40, 207, 114, 58, 73], [178, 10, 163, 28, 148, 42, 215, 245, 1, 114, 53, 95, 2, 181, 248, 11, 42, 255, 125, 58, 18], [209, 244, 163, 51, 31, 101, 49, 96, 196, 147, 127, 158, 112, 75, 246, 78, 77, 171, 110, 82, 18], [187, 207, 175, 62, 51, 226, 190, 128, 41, 5, 218, 195, 64, 123, 159, 238, 80, 211, 66, 249, 14], [245, 245, 104, 203, 88, 82, 172, 94, 30, 225, 205, 189, 133, 43, 198, 172, 138, 167, 29, 218, 106], [231, 244, 253, 109, 24, 109, 241, 205, 29, 213, 13, 48, 136, 112, 248, 2, 87, 246, 250, 33, 180], [196, 243, 227, 221, 229, 1, 217, 117, 64, 97, 222, 111, 167, 88, 248, 3, 77, 171, 107, 215, 144], [208, 185, 203, 157, 80, 101, 112, 94, 36, 142, 193, 5, 103, 128, 254, 69, 78, 168, 107, 218, 77], [213, 243, 227, 77, 229, 101, 160, 117, 163, 97, 222, 111, 167, 68, 248, 10, 179, 212, 114, 154, 224], [209, 244, 185, 229, 24, 101, 49, 205, 196, 133, 221, 158, 112, 104, 248, 133, 215, 212, 100, 156, 18], [174, 244, 184, 12, 40, 100, 49, 96, 185, 140, 135, 97, 112, 187, 246, 78, 111, 171, 69, 156, 18], [209, 138, 188, 93, 53, 230, 155, 84, 239, 213, 247, 39, 136, 82, 165, 169, 191, 216, 117, 33, 7], [209, 138, 188, 229, 53, 230, 155, 68, 239, 153, 247, 39, 103, 82, 165, 169, 191, 216, 117, 156, 7], [223, 245, 163, 93, 27, 109, 160, 188, 175, 133, 221, 158, 1, 124, 248, 133, 40, 212, 114, 243, 73], [152, 192, 219, 252, 119, 25, 25, 111, 36, 189, 98, 43, 67, 84, 55, 118, 43, 154, 114, 173, 240], [231, 234, 244, 28, 27, 156, 1, 84, 155, 203, 98, 47, 50, 181, 218, 240, 77, 224, 180, 166, 12], [128, 225, 131, 101, 83, 91, 40, 113, 234, 131, 201, 212, 103, 18, 187, 230, 5, 111, 193, 164, 146], [248, 5, 135, 59, 78, 154, 244, 64, 98, 131, 188, 20, 92, 68, 198, 27, 143, 162, 171, 139, 223], [226, 204, 2, 78, 27, 76, 223, 44, 69, 132, 178, 131, 1, 134, 156, 207, 199, 210, 28, 173, 18], [209, 245, 109, 189, 24, 109, 49, 68, 12, 153, 62, 47, 9, 124, 248, 244, 42, 246, 114, 110, 159], [209, 10, 154, 203, 21, 156, 152, 111, 229, 217, 221, 158, 252, 84, 218, 156, 162, 212, 114, 33, 240], [223, 244, 163, 229, 27, 109, 160, 188, 175, 133, 62, 163, 67, 68, 248, 240, 77, 171, 114, 173, 240], [223, 244, 163, 51, 27, 101, 49, 188, 175, 147, 221, 158, 112, 75, 248, 133, 77, 212, 114, 173, 18], [232, 245, 244, 255, 27, 101, 40, 80, 246, 203, 98, 5, 47, 132, 193, 69, 171, 246, 117, 197, 143], [164, 209, 136, 238, 83, 1, 37, 159, 92, 177, 254, 162, 26, 88, 152, 34, 77, 176, 149, 214, 173], [208, 185, 203, 109, 88, 101, 112, 94, 155, 203, 98, 5, 103, 128, 254, 69, 171, 224, 117, 218, 77], [223, 244, 185, 66, 40, 16, 63, 67, 175, 186, 158, 158, 134, 56, 3, 132, 87, 212, 69, 156, 18], [209, 244, 163, 93, 31, 101, 160, 96, 196, 133, 127, 158, 67, 68, 246, 78, 77, 171, 110, 82, 18], [196, 234, 154, 203, 80, 58, 22, 111, 36, 217, 193, 48, 46, 88, 218, 3, 77, 171, 114, 215, 240], [209, 234, 154, 203, 21, 58, 22, 111, 29, 217, 240, 48, 46, 68, 218, 156, 248, 212, 114, 33, 240], [213, 243, 154, 77, 229, 101, 160, 117, 163, 97, 222, 111, 167, 68, 248, 10, 179, 212, 114, 154, 224], [209, 244, 198, 10, 40, 101, 242, 205, 196, 124, 221, 158, 112, 51, 248, 247, 77, 212, 250, 156, 18], [209, 244, 185, 93, 24, 101, 49, 205, 196, 133, 221, 158, 112, 104, 248, 133, 215, 212, 100, 156, 18], [152, 192, 219, 252, 119, 25, 25, 119, 36, 189, 163, 43, 152, 161, 55, 118, 43, 154, 86, 139, 196], [164, 15, 136, 238, 83, 109, 37, 159, 92, 177, 254, 162, 26, 88, 152, 34, 77, 176, 149, 214, 106], [209, 130, 21, 176, 114, 28, 81, 4, 9, 185, 133, 212, 217, 15, 218, 106, 197, 229, 46, 91, 199], [223, 245, 194, 203, 21, 109, 160, 14, 175, 133, 221, 47, 9, 68, 218, 240, 162, 212, 180, 173, 18], [154, 245, 50, 243, 35, 2, 1, 98, 58, 246, 7, 211, 114, 119, 197, 236, 80, 217, 202, 189, 255], [209, 244, 163, 51, 31, 101, 49, 96, 196, 147, 127, 145, 112, 180, 246, 78, 77, 164, 110, 77, 18], [202, 252, 252, 197, 229, 2, 66, 118, 63, 25, 33, 147, 140, 184, 208, 90, 97, 85, 236, 204, 145], [208, 222, 43, 144, 120, 159, 126, 174, 199, 93, 251, 135, 134, 112, 186, 120, 184, 100, 232, 218, 121], [239, 211, 184, 3, 36, 229, 1, 28, 217, 12, 152, 97, 120, 75, 54, 74, 174, 229, 189, 141, 92], [223, 253, 188, 93, 26, 82, 95, 195, 175, 138, 221, 96, 2, 124, 248, 133, 55, 212, 114, 243, 86], [209, 250, 106, 189, 24, 109, 206, 68, 12, 153, 62, 47, 9, 124, 252, 244, 42, 246, 114, 110, 159], [217, 44, 97, 79, 182, 120, 160, 193, 144, 222, 221, 149, 194, 45, 7, 249, 58, 229, 51, 207, 115], [160, 138, 68, 202, 21, 109, 191, 14, 215, 186, 34, 47, 115, 57, 196, 239, 32, 214, 138, 173, 19], [226, 5, 131, 59, 78, 172, 244, 64, 39, 68, 188, 20, 134, 68, 189, 6, 110, 162, 171, 139, 21], [248, 201, 135, 165, 110, 154, 205, 144, 98, 131, 185, 174, 92, 83, 198, 27, 143, 241, 118, 108, 223], [223, 251, 161, 203, 27, 132, 22, 111, 246, 133, 221, 158, 46, 88, 201, 133, 76, 171, 114, 215, 240], [196, 234, 154, 93, 80, 58, 22, 111, 36, 217, 193, 48, 67, 68, 218, 3, 77, 203, 114, 173, 221], [223, 245, 163, 66, 21, 58, 49, 67, 175, 217, 98, 254, 134, 68, 254, 156, 183, 212, 23, 82, 240], [209, 234, 154, 203, 31, 16, 22, 111, 29, 186, 240, 48, 46, 43, 218, 132, 248, 171, 114, 33, 18], [209, 244, 163, 93, 31, 101, 160, 96, 175, 133, 127, 158, 67, 68, 246, 133, 77, 212, 110, 173, 18], [223, 244, 163, 93, 27, 101, 160, 188, 196, 133, 221, 158, 67, 68, 248, 78, 77, 171, 114, 82, 18], [209, 245, 163, 189, 31, 109, 49, 68, 12, 153, 98, 47, 9, 124, 248, 244, 42, 246, 114, 110, 18], [223, 245, 109, 66, 24, 16, 49, 67, 175, 186, 62, 254, 134, 43, 254, 132, 183, 171, 23, 82, 159], [209, 245, 154, 203, 21, 156, 152, 111, 30, 217, 240, 189, 133, 84, 218, 156, 138, 167, 29, 218, 240], [245, 10, 104, 203, 88, 82, 172, 94, 29, 225, 205, 48, 252, 43, 198, 172, 162, 212, 114, 33, 106], [223, 245, 188, 93, 27, 101, 196, 97, 175, 105, 240, 158, 117, 68, 254, 0, 171, 246, 117, 243, 106], [208, 244, 185, 255, 27, 132, 39, 111, 246, 133, 221, 5, 47, 128, 60, 69, 77, 212, 114, 197, 143], [226, 204, 163, 78, 31, 76, 223, 96, 69, 132, 178, 145, 1, 180, 156, 207, 199, 210, 28, 173, 18], [209, 244, 2, 51, 27, 101, 49, 44, 196, 147, 127, 131, 112, 134, 246, 78, 77, 164, 110, 77, 18], [231, 244, 185, 51, 19, 156, 253, 96, 92, 105, 254, 162, 26, 75, 152, 244, 77, 207, 149, 166, 12], [164, 15, 136, 238, 83, 109, 37, 159, 197, 177, 127, 48, 50, 88, 174, 34, 241, 176, 69, 214, 106], [164, 15, 136, 238, 83, 109, 217, 245, 92, 177, 254, 95, 26, 88, 248, 2, 77, 176, 250, 214, 106], [202, 10, 163, 28, 40, 2, 37, 159, 163, 133, 240, 162, 2, 181, 152, 34, 78, 255, 149, 33, 18], [202, 10, 163, 101, 40, 2, 40, 245, 163, 133, 240, 95, 2, 181, 248, 2, 78, 111, 250, 164, 146], [128, 225, 131, 28, 83, 91, 217, 113, 234, 131, 201, 212, 103, 18, 187, 230, 5, 255, 193, 33, 18], [196, 245, 244, 221, 27, 101, 40, 80, 246, 97, 98, 111, 167, 132, 248, 3, 77, 171, 117, 197, 144], [232, 243, 227, 255, 229, 1, 217, 117, 64, 203, 222, 5, 47, 88, 193, 69, 171, 246, 107, 215, 143], [223, 245, 252, 197, 31, 16, 49, 118, 63, 25, 98, 254, 134, 43, 208, 90, 97, 85, 23, 82, 18], [202, 252, 163, 66, 229, 2, 66, 67, 175, 186, 33, 147, 140, 184, 254, 132, 183, 171, 236, 204, 145], [208, 222, 43, 144, 53, 230, 126, 174, 199, 153, 251, 39, 134, 112, 186, 120, 184, 100, 232, 218, 121], [209, 138, 188, 229, 120, 159, 155, 68, 239, 93, 247, 135, 103, 82, 165, 169, 191, 216, 117, 156, 7], [209, 244, 163, 51, 31, 2, 49, 96, 196, 147, 127, 19, 83, 180, 246, 78, 78, 101, 110, 33, 18], [202, 169, 176, 72, 40, 101, 217, 45, 163, 217, 240, 145, 112, 208, 9, 2, 77, 164, 250, 77, 205], [209, 245, 163, 189, 27, 101, 49, 188, 2, 132, 98, 254, 47, 43, 254, 200, 183, 246, 23, 157, 178], [146, 139, 197, 90, 38, 188, 113, 248, 168, 215, 205, 50, 210, 65, 152, 93, 117, 87, 177, 156, 112]]";
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
        log.info("正在计算选择 ...");
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
        log.info("正在计算幸运度...");
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
        log.info("正在计算变异...");
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
            algorithm.weightIndividuals.forEach((e)-> result.add(Arrays.toString(e.getSrcs())));
            log.info("当前数据 :" + result);//
            result.clear();
            // 判断是否继续迭代
        } while (solution);
        log.info("迭代结束! ");
        log.info(Arrays.toString(algorithm.best_weight.getSrcs()));
    }


}
