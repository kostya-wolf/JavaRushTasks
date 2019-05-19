package com.javarush.task.task20.task2015;

import java.io.*;

/* 
Переопределение сериализации
*/
public class Solution implements Serializable, Runnable{
    transient private Thread runner;
    private int speed;

    public Solution() {
    }

    public Solution(int speed) {
        this.speed = speed;
        runner = new Thread(this);
        runner.start();
    }

    public void run() {
        // do something here, does not matter
        int i = 1;
        while (i < 5) {
            System.out.println("12" + "34" + "56");
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("7");
            i++;
        }
    }

    /**
     Переопределяем сериализацию.
     Для этого необходимо объявить методы:
     private void writeObject(ObjectOutputStream out) throws IOException
     private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
     Теперь сериализация/десериализация пойдет по нашему сценарию :)
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        runner = new Thread(this);
        runner.start();
    }

    public static void main(String[] args) {
        Solution sol1 = new Solution(10);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("test.dat");
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);

            outputStream.writeObject(sol1);
            fileOutputStream.close();
            outputStream.close();

            System.out.println("===============================================================================");
            System.out.println("===============================================================================");
            System.out.println("===============================================================================");

            FileInputStream fileInputStream = new FileInputStream("test.dat");
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            Solution sol2 = (Solution) inputStream.readObject();

            fileInputStream.close();
            inputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
