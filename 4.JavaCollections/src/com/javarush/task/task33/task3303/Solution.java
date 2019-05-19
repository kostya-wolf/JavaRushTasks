package com.javarush.task.task33.task3303;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/* 
Десериализация JSON объекта
*/
public class Solution {
    public static <T> T convertFromJsonToNormal(String fileName, Class<T> clazz) throws IOException {
        File file = new File(fileName);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, clazz);
    }

    public static void main(String[] args) {
        Cat cat = new Cat();
        File file = new File("catInJson.txt");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, cat);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            Cat newCat = convertFromJsonToNormal("catInJson.txt", Cat.class);
            System.out.println(newCat);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

@JsonAutoDetect
class Cat {
    public String name = "Котик";
    public int age = 3;

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
