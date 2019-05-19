package com.javarush.task.task20.task2022;

import java.io.*;

/* 
Переопределение сериализации в потоке
*/
public class Solution implements Serializable, AutoCloseable {
    transient private FileOutputStream stream;
    public String fileName;

    public Solution(String fileName) throws FileNotFoundException {
        this.stream = new FileOutputStream(fileName);
        this.fileName = fileName;
    }

    public void writeObject(String string) throws IOException {
        stream.write(string.getBytes());
        stream.write("\n".getBytes());
        stream.flush();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.stream = new FileOutputStream(fileName, true);
    }

    @Override
    public void close() throws Exception {
        System.out.println("Closing everything!");
        stream.close();
    }

    public static void main(String[] args) {

        try {
            Solution sol1 = new Solution("test.dat");
            sol1.writeObject("All other data");

            FileOutputStream fileOutputStream = new FileOutputStream("sol.dat");
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(sol1);
            fileOutputStream.close();
            outputStream.close();

            System.out.println("===============================================================================");
            System.out.println("===============================================================================");
            System.out.println("===============================================================================");

            FileInputStream fileInputStream = new FileInputStream("sol.dat");
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            Solution sol2 = (Solution) inputStream.readObject();
            fileInputStream.close();
            inputStream.close();

            sol2.writeObject("one more data");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
