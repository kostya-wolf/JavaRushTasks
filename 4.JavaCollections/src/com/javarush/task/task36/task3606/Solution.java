package com.javarush.task.task36.task3606;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/* 
Осваиваем ClassLoader и Reflection
*/
public class Solution extends ClassLoader {
    private List<Class> hiddenClasses = new ArrayList<>();
    private String packageName;

    public Solution(String packageName) {
        this.packageName = packageName;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Solution solution = new Solution(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "com/javarush/task/task36/task3606/data/second");
        solution.scanFileSystem();
        System.out.println(solution.getHiddenClassObjectByKey("secondhiddenclassimpl"));
        System.out.println(solution.getHiddenClassObjectByKey("firsthiddenclassimpl"));
        System.out.println(solution.getHiddenClassObjectByKey("packa"));
    }

    public void scanFileSystem() throws ClassNotFoundException {
        String pathname = packageName.replaceAll("/", Matcher.quoteReplacement(File.separator));
        File[] classes = new File(pathname).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".class");
            }
        });

        if (classes != null) {
            for (File file : classes) {
                Class<?> clazz = loadClass(file.toPath());
                if (clazz != null
                        && HiddenClass.class.isAssignableFrom(clazz)
                        && hasDefaultConstructor(clazz)) {
                    hiddenClasses.add(clazz);
                }
            }
        }
    }

    private boolean hasDefaultConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterTypes().length == 0) {
                return true;
            }
        }
        return false;
    }

    public HiddenClass getHiddenClassObjectByKey(String key) {
        for (Class<?> clazz : hiddenClasses) {
            if (clazz.getSimpleName().toLowerCase().startsWith(key.toLowerCase())) {
                try {
                    Constructor<?> constructor = clazz.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    return (HiddenClass) constructor.newInstance();
                } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                    return null;
                }
            }
        }
        return null;
    }

    public Class<?> loadClass(Path path) throws ClassNotFoundException {
        try {
            byte[] classBytes = Files.readAllBytes(path);
            return defineClass(null, classBytes, 0, classBytes.length);
        } catch (IOException | ClassFormatError e) {
            throw new ClassNotFoundException();
        }
    }
}

