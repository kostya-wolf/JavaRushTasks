package com.javarush.task.task35.task3507;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/* 
ClassLoader - что это такое?
*/
public class Solution extends ClassLoader {
    public static void main(String[] args) throws Exception {
        String pathToAnimals = URLDecoder.decode(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8").substring(1);
        Set<? extends Animal> allAnimals = getAllAnimals(pathToAnimals + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {
        Set<Animal> result = new HashSet<>();
        File[] listFiles = new File(pathToAnimals).listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                Solution loader = new Solution();
                try {
                    Class<?> clazz = loader.loadClass(file.toPath());
                    if (Animal.class.isAssignableFrom(clazz)) {
                        result.add((Animal) clazz.newInstance());
                    }
                } catch (ReflectiveOperationException e) {
                }
            }
        }
        return result;
    }

    public Class<?> loadClass(Path path) {
        try {
            byte[] classBytes = Files.readAllBytes(path);
            return defineClass(null, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            return null;
        }
    }
}
