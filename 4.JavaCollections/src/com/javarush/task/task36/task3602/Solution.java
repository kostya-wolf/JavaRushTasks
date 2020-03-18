package com.javarush.task.task36.task3602;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;

/*
Найти класс по описанию
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {
        Class<?>[] classes = Collections.class.getDeclaredClasses();
        for (Class<?> clazz : classes) {
            if (List.class.isAssignableFrom(clazz)
                    && isPrivateStatic(clazz.getModifiers())
                    && isThrowingIndexOfBoundsException(clazz)) {
                return clazz;
            }
        }
        return null;
    }

    private static boolean isPrivateStatic(int modifiers) {
        return Modifier.isPrivate(modifiers) && Modifier.isStatic(modifiers);
    }

    private static boolean isThrowingIndexOfBoundsException(Class<?> clazz) {
        try {
            Method getMethod = clazz.getMethod("get", int.class);
            getMethod.setAccessible(true);
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            getMethod.invoke(constructor.newInstance(), 0);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            return false;
        } catch (InvocationTargetException e) {
            if (e.getCause().toString().contains("IndexOutOfBoundsException")) {
                return true;
            }
        }
        return false;
    }

}
