
package com.oahcfly.chgame.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @author caohao
 * @Description 概率助手 ：对均匀概率分布做简单的封装处理，修改为支持概率的非均匀分布 </br> {支持一次性随机出多个结果集}
 * @date 2012-11-30 下午6:45:14
 */
public class RandomHelper {
    // 概率的分界点数组
    private int[] probData;

    // 概率值总和
    private int sum = 0;

    protected Random random = new Random();;

    /**
     * 例："20:20:50:30:20:20:20:20" 物品对应概率值的字符串
     * 
     * @param probdata
     */
    public RandomHelper(String probdata) {
        String[] probDataStr = probdata.split(":");
        int[] tempArray = new int[probDataStr.length];
        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = Integer.valueOf(probDataStr[i]);
        }
        getRandomPoints(tempArray);
    }

    /**
     * 例：概率值数组[20,20,30,40,50,40,40,40]
     * 
     * @param probdata
     */
    public RandomHelper(int[] probdata) {
        getRandomPoints(probdata);
    }

    public RandomHelper(ArrayList<Integer> probList) {
        int size = probList.size();
        int[] probdata = new int[size];
        for (int i = 0; i < size; i++) {
            probdata[i] = probList.get(i);
        }
        getRandomPoints(probdata);
    }

    /**
     * 获取随机出来的单个结果索引
     * 
     * @return 例：0 代表概率随机的结果是第一个
     */
    public int getSingleResultIndex() {
        int randomValue = random.nextInt(sum) + 1;

        int[] cloneProbData = Arrays.copyOf(probData, probData.length);
        cloneProbData[cloneProbData.length - 1] = randomValue;

        Arrays.sort(cloneProbData);
        // 获得 randomValue在数组probData索引位置
        int index = Arrays.binarySearch(cloneProbData, randomValue);
        return index;
    }

    /**
     * 一次性随机randomNum个结果索引,并且互不相同
     * 
     * @param randomNum 4
     * @return 结果格式-字符串 例：1.2.3.4
     */
    public String getMultiResultIndexStr(int randomNum) {
        StringBuffer sb = new StringBuffer();

        if (randomNum == 0 || randomNum > (probData.length - 1)) {
            return null;
        }

        for (int i = 0; i < randomNum; i++) {
            int randomValue = getSingleResultIndex();
            int step = 0;
            while (sb.toString().contains(String.valueOf(randomValue))) {
                if (step > 200) {
                    // 避免死循环
                    break;
                }
                randomValue = getSingleResultIndex();
                step++;
            }
            sb.append(randomValue + ".");
        }

        return sb.toString();
    }

    /**
     * 一次性随机randomNum个结果索引,并且互不相同
     * 
     * @param randomNum 4
     * @return 结果格式-List
     */
    public ArrayList<Integer> getMultiUniqResultIndexList(int randomNum) {
        if (randomNum == 0 || randomNum > (probData.length - 1)) {
            return null;
        }

        ArrayList<Integer> itemsList = new ArrayList<Integer>();

        for (int i = 0; i < randomNum; i++) {
            int randomValue = getSingleResultIndex();
            int step = 0;
            while (itemsList.contains(randomValue)) {
                if (step > 200) {
                    // 避免死循环
                    break;
                }
                randomValue = getSingleResultIndex();
                step++;
            }
            itemsList.add(randomValue);
        }

        return itemsList;
    }
    
    /**
     * <pre>
     *  一次性随机randomNum个结果索引,可以相同
     * 
     * date: 2014年8月15日
     * </pre>
     * @author yandeli
     * @param randomNum
     * @return
     */
    public ArrayList<Integer> getMultiResultIndexList(int randomNum) {
        if (randomNum == 0 || randomNum > (probData.length - 1)) {
            return null;
        }

        ArrayList<Integer> itemsList = new ArrayList<Integer>();

        for (int i = 0; i < randomNum; i++) {
            itemsList.add(getSingleResultIndex());
        }

        return itemsList;
    }

    private void getRandomPoints(int[] probDataArr) {
        int length = probDataArr.length;
        probData = new int[length + 1];
        for (int i = 0; i < length; i++) {
            sum += Integer.valueOf(probDataArr[i]);
            int temp = 0;
            for (int j = i; j >= 0; j--) {
                int value = Integer.valueOf(probDataArr[j]);
                temp = temp + value;
            }
            probData[i] = temp;
        }
    }

    /**
     * DEBUG
     * 
     * @param args
     */
    public static void main(String[] args) {
        // RandomHelper rh = new RandomHelper(new int[] {
        // 2, 2, 5, 3, 2, 2, 2, 2
        // });
        RandomHelper rh2 = new RandomHelper("1:1:1:1:1:1:1:1:1:1:10000:17990:18000:18000:18000:18000");

        for (int i = 0; i < 100000; i++) {
            //System.out.println("-" + rh2.getMultiResultIndexList(1).toString());
            int idx = rh2.getSingleResultIndex();
            if (idx < 5) {
                System.out.println("bingo_" + idx);
            }
        }

        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(15);
        list.add(13);
        list.add(72);

        RandomHelper thHelper = new RandomHelper(list);
        for (int i = 0; i < 10; i++) {
            System.out.println("list:" + thHelper.getSingleResultIndex());
        }
 
    }

}
