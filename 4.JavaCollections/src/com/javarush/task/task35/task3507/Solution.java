package com.javarush.task.task35.task3507;

import java.io.File;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
        if (!pathToAnimals.endsWith("/")) {
            pathToAnimals += "/";
        }
        File catalog = new File(pathToAnimals);
        String packageName = null;

        Set<Animal> result = new HashSet<>();

        for (File f : catalog.listFiles()) {
            Class clazz = null;
            if (packageName == null) {
                packageName = getPackageName(f.getAbsolutePath());
            }
            try {
                clazz = Class.forName(packageName + f.getName().replace(".class", ""));
                Class[] interfaces = clazz.getInterfaces();
                if (Arrays.asList(interfaces).contains(Animal.class)) {
                    Animal animal = (Animal) clazz.newInstance();
                    result.add(animal);
                }
            } catch (ReflectiveOperationException e) {
            }
        }


        return result;
    }

    private static String getPackageName(String absolutePath) {
        String packageName = "";
        List<String> dirNames = Arrays.asList(absolutePath.split("\\\\"));
        String className = dirNames.get(dirNames.size() - 1).replace(".class", "");
        Class clazz = null;

        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
        }

        int i = dirNames.size() - 2;
        while (clazz == null && i >= 0) {
            try {
                packageName = dirNames.get(i) + "." + packageName;
                clazz = Class.forName(packageName + className);
            } catch (ClassNotFoundException e) {
            }
            i--;
        }

        return packageName;
    }
}
