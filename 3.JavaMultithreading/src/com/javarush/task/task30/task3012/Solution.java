package com.javarush.task.task30.task3012;

/* 
Получи заданное число
*/

import static java.lang.Math.abs;

public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.createExpression(2);
    }

    static final int[] all = {1, 3, 9, 27, 81, 243, 729, 2187};

    public void createExpression(int number) {
        recursion(0, 0, number, "");
    }

    private void recursion (int sum, int i, int number, String s) {
        if (abs(sum) > number) return;
        if (sum == number) {
            System.out.println(number + " =" + s);
        } else {
            if (i < all.length) {
                recursion(sum + all[i], i + 1, number, s + " + " + all[i]);
                recursion(sum - all[i], i + 1, number, s + " - " + all[i]);
                recursion(sum, i + 1, number, s);
            }
        }
    }
}