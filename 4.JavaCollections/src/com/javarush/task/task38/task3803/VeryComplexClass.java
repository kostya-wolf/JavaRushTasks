package com.javarush.task.task38.task3803;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* 
Runtime исключения (unchecked exception)
*/

public class VeryComplexClass {
    public void methodThrowsClassCastException() {
        Object s = "null";
        VeryComplexClass veryComplexClass = (VeryComplexClass) s;
    }

    public void methodThrowsNullPointerException() {
        String nullStr = null;
        System.out.println(nullStr.equals("null"));
    }

    public static void main(String[] args) {
        VeryComplexClass veryComplexClass = new VeryComplexClass();
        veryComplexClass.methodThrowsClassCastException();
    }
}
