package com.javarush.task.task35.task3507;

import java.io.File;
import java.net.URLDecoder;
import java.util.Set;

/* 
ClassLoader - что это такое?
*/
public class Solution {
    public static void main(String[] args) throws Exception {
        String pathToAnimals = URLDecoder.decode(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8").substring(1);
        Set<? extends Animal> allAnimals = getAllAnimals(pathToAnimals + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {
        File catalog = new File(pathToAnimals);
        try {
            Class clazz2 = Class.forName("com\\javarush\\task\\task35\\task3507\\data\\Cat.class".replace("\\", ".").replace(".class", ""));
            System.out.println("clazz2 = " + clazz2.getName());
            for (File f : catalog.listFiles()) {
                Class clazz = Class.forName(f.getName().replace(".class", ""));
                System.out.println("clazz = " + clazz.getName());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Класс не найден: " + e.getMessage());
        }

        return null;
    }
}
