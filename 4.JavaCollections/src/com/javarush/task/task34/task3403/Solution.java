package com.javarush.task.task34.task3403;

/* 
Разложение на множители с помощью рекурсии
*/
public class Solution {
    public void recurse(int n) {
        for (int i = 2; i <= n; i++) {
            if (i == n) {
                System.out.print(i);
                return;
            }
            if (n % i == 0) {
                System.out.print(i + " ");
                recurse(n / i);
                return;
            }
        }
    }

    public static void main(String[] args) {
        new Solution().recurse(132);
    }
}
