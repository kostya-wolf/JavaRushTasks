package com.javarush.task.task21.task2107;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* 
Глубокое клонирование карты
*/
public class Solution implements Cloneable {

    protected Map<String, User> users = new LinkedHashMap();

    public static class User implements Cloneable {
        int age;
        String name;

        public User(int age, String name) {
            this.age = age;
            this.name = name;
        }

        protected User clone() throws CloneNotSupportedException {
            User result;
            int a = this.age;
            String n = this.name;
            result = new User(a, n);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (this.getClass() != obj.getClass()) return false;
            User user = (User) obj;
            if (this.age != user.age) return false;
            if (this.name == null || user.name == null)
                return this.name == user.name;
            else return this.name.equals(user.name);
        }

        @Override
        public int hashCode() {
            if (name == null) return age;
            else return age+name.hashCode();
        }
    }

    @Override
    protected Solution clone() throws CloneNotSupportedException {
        Solution result = new Solution();
        result.users = new LinkedHashMap<>();
        Iterator<String> iter = users.keySet().iterator();
        while (iter.hasNext()){
            String key = iter.next();
            User value = (User) users.get(key).clone();
            result.users.put(key, value);
        }
        return result;
    }

    @Override
    public int hashCode() {
        if (this.users == null) return 0;
        else return this.users.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Solution sol = (Solution) obj;
        if (this.users == null || sol.users == null)
            return this.users == sol.users;
        else return this.users.equals(sol.users);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.users.put("Hubert", new User(172, "Hubert"));
        solution.users.put("Zapp", new User(41, "Zapp"));
        Solution clone = null;
        try {
            clone = (Solution) solution.clone();
            System.out.println(solution);
            System.out.println(clone);
            System.out.println("solution == clone is "+ (solution == clone));

            System.out.println(solution.users.hashCode());
            System.out.println(clone.users.hashCode());

            System.out.println(solution.users.equals(clone.users));
            System.out.println(solution.users == clone.users);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace(System.err);
        }
    }
}
