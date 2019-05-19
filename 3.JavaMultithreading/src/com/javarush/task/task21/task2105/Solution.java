package com.javarush.task.task21.task2105;

import java.util.HashSet;
import java.util.Set;

/* 
Исправить ошибку. Сравнение объектов
*/
public class Solution {
    private final String first, last;

    public Solution(String first, String last) {
        this.first = first;
        this.last = last;
    }

    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Solution))
            return false;

        Solution n = (Solution) o;

        if (this.first == null || this.last == null) {
            return n.first == first && n.last == last;
        }

        return n.first.equals(first) && n.last.equals(last);
    }

    @Override
    public int hashCode() {
        int a, b;
        if (this.first == null) {
            a = 0;
        } else {
            a = first.hashCode();
        }
        if (this.last == null) {
            b = 0;
        } else {
            b = last.hashCode();
        }
        return 31 * a + b;
    }

    public static void main(String[] args) {
        Set<Solution> s = new HashSet<>();
        s.add(new Solution("Mickey", "Mouse"));
        System.out.println(s.contains(new Solution("Mickey", "Mouse")));
    }
}
