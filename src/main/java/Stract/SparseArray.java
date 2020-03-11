package Stract;

import com.alibaba.fastjson.JSON;

public class SparseArray {
    public static void main(String[] args) {
        int[][] array = new int[210][320];
        array[1][2] = 1;
        array[2][3] = 2;
        array[5][8] = 1;
        System.out.println(JSON.toJSONString(array));

        //二维数组转稀疏数组
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j] != 0) {
                    sum++;
                }
            }
        }

        //初始化稀疏数组
        int[][] sparseArray = new int[sum + 1][3];
        sparseArray[0][0] = array.length;
        sparseArray[0][1] = array[0].length;
        sparseArray[0][2] = sum;

        int count = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j] != 0) {
                    count++;
                    sparseArray[count][0] = i;
                    sparseArray[count][1] = j;
                    sparseArray[count][2] = array[i][j];
                }
            }
        }
        System.out.println(JSON.toJSONString(sparseArray));

    }
}
