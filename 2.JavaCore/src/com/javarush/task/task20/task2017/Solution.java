package com.javarush.task.task20.task2017;

import java.io.*;

/* 
Десериализация
*/
public class Solution {
    public A getOriginalObject(ObjectInputStream objectStream) {
        try {
            Object o = objectStream.readObject();
            if (o instanceof A) {
                A a = (A) o;
                return a;
            }
            else return null;
        }
        catch (Exception e){
            return null;
        }
    }

    public static class A implements Serializable {
        public A() {
            System.out.println("inside A");
        }
    }

    public static class B extends A {
        public B() {
            System.out.println("inside B");
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        //B a = new B();
        String a = "123";
        System.out.println("ser: " +a.toString());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("test.dat");
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(a);
            fileOutputStream.close();
            outputStream.close();

            FileInputStream fileInputStream = new FileInputStream("test.dat");
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            A human2 = sol.getOriginalObject(inputStream);
            System.out.println("des:" +human2);
            fileInputStream.close();
            inputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
