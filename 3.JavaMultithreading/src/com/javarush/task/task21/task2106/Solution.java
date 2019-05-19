package com.javarush.task.task21.task2106;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/* 
Ошибка в equals/hashCode
*/
public class Solution {
    private int anInt;
    private String string;
    private double aDouble;
    private Date date;
    private Solution solution;

    public Solution(int anInt, String string, double aDouble, Date date, Solution solution) {
        this.anInt = anInt;
        this.string = string;
        this.aDouble = aDouble;
        this.date = date;
        this.solution = solution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        Solution solution1;
        if (o instanceof Solution) {
            solution1 = (Solution) o;
        } else return false;

        int result = 0;

        if (this.aDouble != solution1.aDouble) return false;
        if (anInt != solution1.anInt) return false;

        if (date == null || solution1.date == null){
            if (date == solution1.date) result++;
        }
        else if (date.equals(solution1.date)) result++;

        if (string == null || solution1.string == null){
            if (string == solution1.string) result++;
        }
        else if (string.equals(solution1.string)) result++;

        if (solution == null || solution1.solution == null){
            if (solution == solution1.solution) result++;
        }
        else if (solution.equals(solution1.solution)) result++;

        return result==3;
    }

    @Override
    public int hashCode() {
        int a,b,c,d,e;
        a = anInt;
        b = string != null ? string.hashCode() : 0;
        c = Double.hashCode(aDouble);
        d = date != null ? date.hashCode() : 0;
        e = solution != null ? solution.hashCode() : 0;
        int result = a + b + c + d + e;
        return result;
    }

    public static void main(String[] args) {
        Date sameDate = new Date();
        Solution sol0 = new Solution(0, "nol", 0.9, sameDate, null);
        Solution sol1 = new Solution(1, "odin", 1.1, sameDate, sol0);
        Solution sol2 = new Solution(2, "dva", 2.2, sameDate, sol1);
        Solution sol3 = new Solution(2, "dva", 2.2, sameDate, sol1);

        System.out.println(sol1.equals(sol2));
        System.out.println(sol1.hashCode() == sol2.hashCode());

        System.out.println(sol2.equals(sol3));
        System.out.println(sol2.hashCode() == sol3.hashCode());

        Set<Solution> s = new HashSet<>();
        s.add(new Solution(2, "dva", 2.2, sameDate, sol1));
        System.out.println(s.contains(new Solution(2, "dva", 2.2, sameDate, sol1)));
    }
}
