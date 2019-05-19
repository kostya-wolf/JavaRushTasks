package com.javarush.task.task21.task2104;

import java.util.HashSet;
import java.util.Set;

/* 
Equals and HashCode
*/
public class Solution {
    private final String first, last;

    public Solution(String first, String last) {
        this.first = first;
        this.last = last;
    }

    public boolean equals(Object n) {
        if (this == n) {
            return true;
        }
        if (n == null) return false;

        Solution n2;
        if (n instanceof Solution) {
            n2 = (Solution) n;
        } else return false;

        if (this.first == null || this.last == null) {
            return n2.first == first && n2.last == last;
        }
        return n2.first.equals(first) && n2.last.equals(last);
    }

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
        s.add(new Solution("Donald", "Duck"));
        s.add(new Solution(null, null));
        System.out.println(s.contains(new Solution("Donald", "Duck")));
        System.out.println(s.contains(new Solution(null, null)));
        System.out.println(s.contains(null));
    }
}
