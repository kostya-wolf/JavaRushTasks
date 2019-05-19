package com.javarush.task.task26.task2601;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.abs;

/*
Почитать в инете про медиану выборки
*/
public class Solution {

    public static void main(String[] args) {
        Integer[] mas = {13, 8, 15, 20, 5, 17, 14};
        Solution.sort(mas);
    }

    public static Integer[] sort(Integer[] array) {
        Collections.sort(Arrays.asList(array));
        int mediana;
        if (array.length % 2 != 0) {
            mediana = array[array.length / 2];
        } else {
            mediana = (array[array.length / 2] + array[(array.length / 2) - 1]) / 2;
        }

        Comparator<Integer> compareByMediana = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int result = abs(o1 - mediana) - abs(o2 - mediana);
                if (result == 0) {
                    if (o1 < o2) return -1;
                    else return 1;
                }
                return result;
            }
        };

        List<Integer> mas = Arrays.asList(array);
        Collections.sort(mas, compareByMediana);
        return array;
    }
}
