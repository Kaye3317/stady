package controller;

import java.util.*;

/**
 * 别名抽样算法
 *
 * @author yk
 * 抽样工具
 */
public class SamplingUtil {

    private SamplingUtil() {

    }

    /**
     * Alias Method抽样
     *
     * @param probabilities 样本的概率集合
     * @return 返回抽中的事件下标（为概率的下标）
     */
    public static int sampling(List<Double> probabilities) {
        if (probabilities == null || probabilities.size() == 0) {
            throw new IllegalArgumentException("没有事件概率");
        }
        int length = probabilities.size();
        // 构建Alias Table
        // probabilitie用于保存归一后的事件概率
        double[] probability = new double[length];
        // alias用于保存填充的事件概率（这里使用填充事件的下标）
        int[] alias = new int[length];
        // probabilities替换为有序的ArrayList
        probabilities = new ArrayList<>(probabilities);
        // 按照其均值归一化 获取区间平均值
        double average = 1.0 / length;
        // 构建队列 用于计算并填充区间
        // small用于保存小于均值的事件概率的下标
        Deque<Integer> small = new ArrayDeque<>();
        // large用于保存大于等于均值的事件概率的下标
        Deque<Integer> large = new ArrayDeque<>();

        // 分类事件 分别放入small large
        for (int i = 0; i < length; i++) {
            if (probabilities.get(i) < average) {
                small.add(i);
            } else {
                large.add(i);
            }
        }
        //循环填充Alias Table
        while (!small.isEmpty() && !large.isEmpty()) {
            //1.找出其中面积小于等于1的列,如less列,这些列说明其一定要被别的事件矩形填上
            int less = small.removeLast();
            //2.然后从面积大于1的列中，选出一个，比如greater列
            int greater = large.removeLast();
            //3.所以在probability[less]中填上其归一后的面积
            probability[less] = probabilities.get(less) * length;
            //4.用greater列将第less列填满，用这个Alias[less] = greater表示
            alias[less] = greater;
            //5.第greater列面积变更为减去填充用掉面积后的面积。
            probabilities.set(greater, probabilities.get(greater) + probabilities.get(less) - average);
            //6.判断greater列是否还有多余的面积分给其他列
            if (probabilities.get(greater) >= average) {
                large.add(greater);
            } else {
                small.add(greater);
            }
        }
        //填充完后 AliasTable列如果只有一个事件那么它在该列中的概率为100%
        while (!small.isEmpty()) {
            probability[small.removeLast()] = 1.0;
        }
        while (!large.isEmpty()) {
            probability[large.removeLast()] = 1.0;
        }
        //抽样
        Random random = new Random();
        // 第一次  选取AliasTable列
        int colunm = random.nextInt(probability.length);
        // 第二次  选取该列的事件
        return random.nextDouble() < probability[colunm] ? colunm : alias[colunm];
    }
}
