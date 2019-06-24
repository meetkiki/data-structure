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
        String genes = "[[172, 224, 133, 177, 40, 135, 32, 98, 200, 123, 176, 199, 14, 147, 234, 173, 146, 254, 131, 151, 231], [169, 233, 79, 190, 8, 135, 121, 162, 187, 111, 145, 3, 239, 148, 230, 161, 29, 175, 71, 201, 245], [172, 252, 158, 177, 23, 135, 111, 226, 201, 123, 145, 199, 14, 200, 145, 146, 146, 254, 131, 151, 231], [136, 240, 108, 136, 94, 41, 235, 253, 89, 237, 209, 127, 54, 133, 226, 220, 46, 236, 131, 136, 211], [155, 204, 125, 15, 131, 38, 112, 213, 145, 120, 176, 2, 83, 148, 234, 185, 189, 109, 48, 180, 29], [182, 224, 216, 215, 40, 135, 32, 98, 200, 205, 13, 184, 14, 223, 125, 38, 146, 243, 131, 170, 231], [169, 151, 216, 197, 131, 38, 144, 117, 145, 158, 176, 3, 83, 160, 157, 161, 188, 175, 131, 129, 92], [142, 233, 216, 224, 40, 85, 78, 48, 57, 148, 131, 184, 200, 200, 157, 52, 189, 241, 48, 170, 204], [172, 224, 133, 177, 40, 120, 32, 98, 200, 123, 176, 249, 14, 147, 234, 173, 146, 241, 131, 151, 92], [169, 128, 230, 190, 8, 135, 33, 198, 8, 151, 73, 3, 94, 148, 127, 161, 171, 175, 80, 133, 5], [255, 236, 188, 206, 179, 144, 24, 188, 242, 216, 86, 201, 6, 227, 78, 152, 154, 245, 187, 137, 47], [169, 252, 79, 177, 40, 93, 38, 162, 187, 111, 163, 2, 189, 109, 230, 52, 176, 109, 51, 151, 245], [142, 204, 210, 26, 131, 74, 144, 213, 187, 102, 178, 3, 192, 147, 157, 250, 188, 116, 51, 151, 243], [142, 233, 59, 15, 20, 135, 112, 26, 200, 120, 9, 3, 159, 148, 157, 134, 189, 216, 48, 201, 92], [148, 210, 131, 2, 115, 84, 215, 44, 210, 187, 29, 29, 236, 111, 31, 200, 9, 123, 11, 173, 163], [147, 247, 45, 190, 196, 135, 172, 98, 201, 148, 245, 198, 84, 200, 233, 250, 147, 255, 189, 183, 231], [163, 215, 210, 197, 39, 220, 74, 141, 106, 158, 202, 3, 81, 160, 216, 250, 29, 180, 81, 129, 92], [172, 224, 133, 177, 40, 135, 32, 98, 200, 123, 176, 199, 15, 218, 145, 173, 147, 254, 131, 151, 231], [142, 233, 210, 178, 33, 74, 144, 162, 187, 131, 178, 3, 192, 147, 157, 250, 188, 116, 51, 151, 243], [211, 144, 64, 83, 36, 155, 38, 236, 79, 110, 12, 255, 41, 27, 74, 255, 166, 166, 46, 117, 203], [142, 233, 79, 15, 20, 135, 112, 26, 200, 120, 186, 3, 83, 148, 157, 134, 189, 139, 48, 201, 92], [212, 112, 126, 136, 103, 129, 235, 226, 81, 33, 191, 155, 135, 55, 81, 195, 255, 80, 126, 239, 15], [148, 112, 126, 2, 103, 84, 235, 44, 210, 104, 29, 29, 236, 55, 31, 195, 255, 80, 11, 173, 163], [183, 186, 109, 182, 20, 92, 29, 190, 88, 169, 110, 42, 83, 39, 138, 158, 18, 173, 148, 126, 90], [185, 187, 164, 34, 178, 104, 233, 200, 241, 253, 168, 198, 172, 246, 245, 219, 125, 55, 139, 1, 24], [171, 151, 230, 255, 131, 223, 182, 168, 200, 251, 73, 3, 95, 148, 162, 38, 216, 9, 223, 133, 243], [169, 211, 126, 242, 40, 135, 81, 210, 240, 104, 178, 199, 94, 217, 234, 92, 18, 129, 65, 151, 38], [172, 252, 158, 177, 23, 135, 111, 226, 201, 123, 145, 249, 14, 200, 145, 146, 146, 241, 131, 151, 92], [211, 144, 64, 189, 36, 174, 38, 236, 79, 110, 12, 184, 231, 27, 74, 255, 166, 166, 46, 117, 203], [214, 233, 41, 189, 8, 174, 66, 21, 81, 33, 61, 210, 231, 103, 81, 161, 29, 116, 88, 140, 84], [182, 232, 216, 215, 40, 97, 81, 26, 57, 205, 13, 184, 83, 223, 125, 38, 18, 243, 80, 170, 21], [234, 196, 156, 185, 196, 76, 217, 172, 78, 40, 86, 201, 178, 64, 145, 62, 185, 249, 51, 251, 82], [172, 224, 132, 177, 141, 135, 111, 226, 201, 123, 145, 249, 14, 133, 145, 146, 146, 254, 131, 151, 92], [214, 233, 132, 189, 20, 88, 66, 21, 68, 104, 61, 83, 231, 148, 147, 161, 29, 116, 88, 140, 245], [234, 233, 79, 197, 216, 162, 80, 162, 187, 129, 163, 57, 94, 109, 159, 161, 29, 197, 71, 201, 201], [148, 233, 131, 2, 115, 84, 38, 44, 210, 187, 29, 2, 236, 111, 31, 123, 9, 123, 11, 173, 163], [147, 224, 133, 190, 40, 249, 73, 98, 69, 148, 110, 198, 192, 200, 144, 52, 4, 180, 51, 183, 231], [214, 211, 41, 189, 8, 174, 111, 21, 201, 205, 61, 184, 231, 122, 147, 161, 30, 116, 88, 140, 245], [212, 112, 126, 136, 103, 129, 235, 236, 113, 104, 12, 155, 135, 27, 74, 195, 255, 166, 126, 239, 15], [169, 233, 132, 152, 131, 85, 144, 162, 176, 185, 110, 57, 94, 148, 147, 123, 29, 197, 51, 151, 243], [142, 233, 210, 178, 33, 74, 144, 162, 187, 131, 178, 2, 192, 147, 157, 250, 188, 116, 48, 151, 243], [169, 210, 132, 15, 40, 93, 33, 26, 176, 151, 29, 57, 94, 208, 147, 123, 171, 197, 80, 189, 5], [155, 204, 125, 62, 131, 38, 32, 212, 145, 185, 176, 249, 189, 148, 234, 185, 176, 223, 123, 180, 29], [142, 252, 158, 152, 23, 85, 78, 210, 187, 185, 110, 3, 192, 200, 157, 52, 254, 241, 51, 151, 243], [141, 196, 158, 224, 196, 218, 78, 48, 213, 148, 86, 72, 178, 64, 144, 52, 254, 241, 148, 183, 6], [237, 0, 101, 232, 82, 65, 98, 186, 156, 131, 28, 181, 223, 7, 63, 123, 181, 85, 19, 186, 29], [211, 144, 64, 189, 36, 174, 38, 226, 79, 110, 191, 184, 231, 55, 147, 255, 166, 80, 46, 117, 203], [234, 116, 132, 197, 216, 162, 80, 171, 176, 129, 145, 206, 94, 86, 159, 161, 30, 220, 131, 116, 201], [169, 210, 132, 240, 40, 93, 33, 198, 176, 151, 29, 57, 94, 208, 147, 123, 171, 197, 80, 189, 5], [169, 210, 79, 177, 40, 93, 215, 162, 187, 111, 163, 29, 189, 109, 230, 200, 176, 109, 71, 201, 245], [164, 195, 255, 62, 181, 85, 16, 87, 166, 99, 174, 252, 47, 59, 81, 61, 6, 223, 68, 17, 6], [169, 151, 216, 177, 131, 38, 144, 117, 145, 124, 176, 3, 83, 148, 157, 161, 188, 175, 131, 180, 92], [252, 237, 253, 76, 234, 100, 89, 243, 66, 150, 186, 193, 113, 107, 28, 7, 93, 249, 78, 18, 203], [182, 232, 216, 215, 56, 97, 81, 26, 8, 205, 13, 184, 83, 223, 125, 38, 188, 243, 80, 170, 21], [147, 246, 125, 15, 187, 135, 146, 213, 201, 148, 202, 2, 83, 148, 234, 250, 189, 254, 188, 183, 231], [169, 116, 132, 177, 40, 93, 38, 171, 176, 111, 145, 206, 239, 86, 230, 123, 30, 220, 131, 116, 245], [169, 233, 79, 177, 40, 93, 38, 162, 187, 111, 163, 57, 239, 109, 230, 123, 29, 197, 71, 201, 245], [142, 233, 59, 15, 20, 135, 112, 26, 200, 120, 186, 3, 159, 148, 157, 134, 189, 249, 48, 201, 92], [142, 5, 132, 253, 40, 135, 146, 228, 201, 151, 178, 11, 83, 178, 182, 250, 147, 254, 188, 168, 181], [188, 148, 149, 245, 236, 149, 14, 77, 9, 249, 183, 112, 86, 88, 157, 178, 234, 247, 206, 7, 110], [169, 224, 143, 15, 40, 95, 81, 210, 201, 185, 203, 198, 10, 148, 159, 121, 238, 242, 131, 130, 92], [141, 252, 158, 224, 23, 218, 78, 48, 213, 148, 131, 72, 200, 200, 144, 52, 254, 241, 148, 183, 6], [232, 239, 158, 92, 124, 241, 111, 50, 87, 71, 11, 249, 65, 133, 153, 226, 205, 240, 131, 197, 62], [172, 224, 158, 177, 151, 135, 111, 226, 201, 134, 145, 249, 192, 133, 153, 146, 180, 243, 131, 91, 185], [169, 224, 133, 190, 131, 38, 206, 214, 187, 124, 151, 179, 83, 200, 234, 52, 147, 145, 188, 209, 29], [147, 224, 133, 190, 40, 135, 73, 98, 201, 124, 110, 198, 83, 200, 144, 52, 147, 254, 188, 183, 231], [142, 204, 210, 26, 40, 93, 38, 162, 187, 111, 163, 3, 192, 147, 157, 250, 29, 116, 71, 151, 245], [206, 154, 192, 67, 43, 46, 6, 130, 63, 187, 49, 57, 164, 108, 154, 245, 89, 172, 87, 147, 148], [212, 112, 126, 136, 103, 129, 235, 226, 113, 104, 191, 155, 135, 55, 147, 195, 255, 80, 126, 239, 15], [190, 196, 122, 248, 86, 92, 166, 172, 59, 40, 86, 204, 204, 115, 145, 121, 185, 242, 98, 251, 82], [252, 237, 253, 76, 234, 100, 89, 243, 66, 150, 9, 193, 113, 107, 28, 7, 93, 216, 78, 18, 203], [192, 228, 143, 78, 7, 98, 32, 129, 96, 112, 158, 125, 205, 176, 22, 172, 238, 159, 242, 249, 67], [169, 116, 132, 77, 187, 176, 38, 171, 37, 111, 145, 206, 239, 86, 245, 123, 30, 220, 75, 116, 245], [169, 233, 79, 177, 40, 93, 38, 162, 187, 111, 163, 2, 189, 109, 230, 123, 176, 109, 71, 201, 245], [141, 252, 158, 177, 23, 218, 81, 117, 213, 111, 13, 72, 83, 148, 144, 133, 254, 139, 148, 183, 6], [164, 215, 216, 62, 39, 92, 198, 212, 106, 185, 202, 249, 67, 160, 157, 66, 29, 180, 81, 129, 92], [218, 102, 128, 104, 35, 91, 121, 110, 158, 151, 145, 149, 170, 226, 244, 214, 84, 231, 16, 120, 69], [169, 186, 125, 126, 20, 38, 206, 214, 187, 103, 151, 179, 83, 101, 138, 185, 18, 173, 48, 126, 29], [211, 144, 64, 83, 36, 155, 89, 236, 79, 110, 12, 255, 41, 27, 74, 128, 166, 166, 46, 117, 203], [237, 201, 73, 206, 8, 185, 87, 4, 52, 25, 169, 251, 83, 230, 227, 121, 178, 149, 40, 182, 122], [163, 232, 155, 65, 190, 128, 126, 142, 41, 122, 141, 225, 241, 90, 150, 138, 146, 249, 252, 150, 91], [254, 185, 202, 89, 0, 210, 89, 224, 179, 26, 209, 47, 9, 14, 148, 2, 150, 29, 18, 138, 142], [246, 76, 72, 254, 9, 38, 63, 103, 36, 151, 105, 167, 57, 17, 37, 31, 90, 191, 191, 185, 107], [188, 144, 142, 158, 128, 63, 48, 169, 247, 61, 155, 221, 242, 206, 210, 56, 114, 233, 216, 214, 66], [237, 120, 122, 16, 159, 13, 142, 122, 185, 112, 111, 37, 83, 177, 175, 178, 54, 139, 66, 207, 198], [169, 148, 217, 62, 38, 115, 198, 148, 117, 167, 202, 26, 188, 188, 226, 126, 2, 180, 211, 129, 91], [212, 112, 72, 254, 9, 129, 235, 236, 113, 104, 105, 167, 57, 27, 74, 31, 255, 166, 126, 185, 15], [246, 76, 126, 136, 103, 38, 63, 103, 36, 151, 12, 155, 135, 17, 37, 195, 90, 191, 191, 239, 107], [142, 195, 255, 15, 181, 85, 112, 26, 200, 120, 174, 3, 83, 59, 81, 61, 189, 223, 48, 201, 6], [164, 233, 79, 62, 20, 135, 16, 87, 166, 99, 186, 252, 47, 148, 157, 134, 6, 139, 68, 17, 92], [169, 116, 132, 178, 33, 74, 38, 162, 176, 131, 178, 206, 239, 86, 157, 123, 30, 220, 48, 151, 245], [142, 233, 210, 177, 40, 93, 144, 171, 187, 111, 145, 2, 192, 147, 230, 250, 188, 116, 131, 116, 243], [169, 128, 230, 190, 8, 135, 33, 198, 8, 111, 73, 3, 94, 148, 127, 161, 171, 175, 71, 201, 245], [169, 233, 79, 190, 8, 135, 121, 162, 187, 151, 145, 3, 239, 148, 230, 161, 29, 175, 80, 133, 5], [169, 252, 79, 177, 40, 91, 121, 162, 158, 111, 145, 149, 170, 109, 230, 214, 176, 109, 51, 151, 245], [218, 102, 128, 104, 35, 93, 38, 110, 187, 151, 163, 2, 189, 226, 244, 52, 84, 231, 16, 120, 69], [164, 215, 64, 62, 39, 92, 198, 212, 79, 185, 12, 255, 67, 160, 157, 66, 29, 166, 46, 129, 92], [211, 144, 216, 83, 36, 155, 38, 236, 106, 110, 202, 249, 41, 27, 74, 255, 166, 180, 81, 117, 203], [136, 186, 108, 126, 20, 41, 235, 253, 89, 237, 209, 127, 54, 133, 138, 220, 46, 236, 131, 126, 211], [169, 240, 125, 136, 94, 38, 206, 214, 187, 103, 151, 179, 83, 101, 226, 185, 18, 173, 48, 136, 29], [172, 144, 133, 83, 36, 155, 32, 98, 200, 123, 176, 249, 14, 27, 234, 255, 146, 166, 46, 117, 92], [211, 224, 64, 177, 40, 120, 38, 236, 79, 110, 12, 255, 41, 147, 74, 173, 166, 241, 131, 151, 203], [169, 210, 132, 15, 40, 93, 80, 26, 187, 151, 163, 57, 94, 208, 147, 123, 29, 197, 80, 189, 5], [234, 233, 79, 197, 216, 162, 33, 162, 176, 129, 29, 57, 94, 109, 159, 161, 171, 197, 71, 201, 201], [188, 148, 149, 245, 40, 97, 14, 77, 9, 249, 183, 112, 86, 223, 157, 38, 234, 247, 206, 7, 110], [182, 232, 216, 215, 236, 149, 81, 26, 57, 205, 13, 184, 83, 88, 125, 178, 18, 243, 80, 170, 21], [185, 187, 164, 34, 178, 97, 233, 200, 57, 253, 13, 198, 83, 246, 245, 38, 18, 55, 80, 170, 24], [182, 232, 216, 215, 40, 104, 81, 26, 241, 205, 168, 184, 172, 223, 125, 219, 125, 243, 139, 1, 21], [255, 236, 79, 206, 179, 93, 38, 188, 242, 111, 86, 201, 6, 109, 78, 152, 176, 245, 187, 201, 47], [169, 233, 188, 177, 40, 144, 24, 162, 187, 216, 163, 2, 189, 227, 230, 123, 154, 109, 71, 137, 245], [211, 144, 216, 83, 36, 92, 38, 236, 79, 110, 12, 255, 41, 27, 74, 255, 166, 166, 46, 117, 203], [164, 215, 64, 62, 39, 155, 198, 212, 106, 185, 202, 249, 67, 160, 157, 66, 29, 180, 81, 129, 92], [234, 233, 79, 197, 82, 65, 98, 162, 187, 129, 163, 181, 94, 7, 63, 123, 181, 85, 71, 201, 29], [237, 0, 101, 232, 216, 162, 80, 186, 156, 131, 28, 57, 223, 109, 159, 161, 29, 197, 19, 186, 201], [185, 187, 131, 2, 178, 104, 233, 200, 210, 187, 168, 29, 172, 246, 245, 200, 9, 55, 11, 1, 24], [148, 210, 164, 34, 115, 84, 215, 44, 241, 253, 29, 198, 236, 111, 31, 219, 125, 123, 139, 173, 163], [169, 252, 132, 152, 23, 85, 144, 117, 176, 111, 13, 57, 83, 148, 147, 123, 254, 197, 51, 183, 6], [141, 233, 158, 177, 131, 218, 81, 162, 213, 185, 110, 72, 94, 148, 144, 133, 29, 139, 148, 151, 243], [141, 148, 158, 177, 23, 115, 198, 117, 213, 111, 13, 72, 83, 148, 144, 133, 2, 139, 148, 183, 6], [169, 252, 217, 62, 38, 218, 81, 148, 117, 167, 202, 26, 188, 188, 226, 126, 254, 180, 211, 129, 91], [172, 252, 158, 65, 23, 135, 111, 142, 201, 122, 145, 199, 241, 90, 150, 146, 146, 249, 252, 151, 231], [163, 232, 155, 177, 190, 128, 126, 226, 41, 123, 141, 225, 14, 200, 145, 138, 146, 254, 131, 150, 91], [169, 204, 125, 240, 40, 38, 33, 198, 176, 151, 29, 57, 94, 148, 147, 123, 171, 109, 80, 180, 5], [155, 210, 132, 15, 131, 93, 112, 213, 145, 120, 176, 2, 83, 208, 234, 185, 189, 197, 48, 189, 29], [169, 144, 132, 83, 131, 85, 38, 236, 79, 110, 12, 57, 94, 27, 147, 123, 29, 197, 46, 151, 203], [211, 233, 64, 152, 36, 155, 144, 162, 176, 185, 110, 255, 41, 148, 74, 255, 166, 166, 51, 117, 243], [172, 201, 132, 177, 8, 185, 111, 4, 201, 25, 145, 249, 83, 133, 227, 121, 146, 149, 40, 151, 92], [237, 224, 73, 206, 141, 135, 87, 226, 52, 123, 169, 251, 14, 230, 145, 146, 178, 254, 131, 182, 122]]";
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
