package com.javarush.task.task20.task2014;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/* 
Serializable Solution
*/
public class Solution implements Serializable {
    public static void main(String[] args) {
        Solution human = new Solution(4);
        System.out.println(human);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("test.dat");
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(human);
            fileOutputStream.close();
            outputStream.close();

            FileInputStream fileInputStream = new FileInputStream("test.dat");
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            Solution human2 = (Solution) inputStream.readObject();
            System.out.println(human2);
            fileInputStream.close();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    transient private final String pattern = "dd MMMM yyyy, EEEE";
    transient private Date currentDate;
    transient private int temperature;
    String string;

    public Solution() {
    }

    public Solution(int temperature) {
        this.currentDate = new Date();
        this.temperature = temperature;

        string = "Today is %s, and current temperature is %s C";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        this.string = String.format(string, format.format(currentDate), temperature);
    }

    @Override
    public String toString() {
        return this.string;
    }
}
