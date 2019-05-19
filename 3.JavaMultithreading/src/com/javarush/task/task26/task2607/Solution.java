package com.javarush.task.task26.task2607;

/* 
Вежливость - это искусственно созданное хорошее настроение
*/
public class Solution {

    public static class IntegerHolder {
        private int value;

        synchronized public int get() {
            return value;
        }

        synchronized public void set(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
    }
}
