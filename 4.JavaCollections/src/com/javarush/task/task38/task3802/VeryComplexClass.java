package com.javarush.task.task38.task3802;

/* 
Проверяемые исключения (checked exception)
*/

import java.util.Arrays;

public class VeryComplexClass {
    public void veryComplexMethod() throws Exception {
        //напишите тут ваш код
        getException();
    }

    private void getException() throws Exception {
        throw new Exception();
    }

    public static void main(String[] args) {
        VeryComplexClass veryComplexClass = new VeryComplexClass();
        try {
            veryComplexClass.veryComplexMethod();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
