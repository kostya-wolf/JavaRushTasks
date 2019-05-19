package com.javarush.task.task20.task2006;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* 
Как сериализовать?
*/
public class Solution {
    public static class Human implements Serializable {
        public String name;
        public List<Asset> assets = new ArrayList<>();

        public Human() {
        }

        public Human(String name, Asset... assets) {
            this.name = name;
            if (assets != null) {
                this.assets.addAll(Arrays.asList(assets));
            }
        }
    }

    public static void main(String[] args) {
        Asset asset = new Asset("Car");
        Asset asset2 = new Asset("OneMoreCar");
        asset.setPrice(10000000);
        asset2.setPrice(20000000);
        Human human = new Human("Костя", asset, asset2);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("test.dat");
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(human);
            fileOutputStream.close();
            outputStream.close();

            FileInputStream fileInputStream = new FileInputStream("test.dat");
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            Human human2 = (Human) inputStream.readObject();
            System.out.println("human2: "+human2.name+ " " + human2.assets.get(1).getName()+" " + human2.assets.get(1).getPrice());
            fileInputStream.close();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
