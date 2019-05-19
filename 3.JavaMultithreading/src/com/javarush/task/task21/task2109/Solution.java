package com.javarush.task.task21.task2109;

/* 
Запретить клонирование
*/
public class Solution {
    public static class A implements Cloneable {
        private int i;
        private int j;

        public A(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }

        @Override
        protected A clone() throws CloneNotSupportedException {
            return new A(i,j);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            A a = (A) o;

            if (i != a.i) return false;
            return j == a.j;

        }

        @Override
        public int hashCode() {
            int result = i;
            result = 31 * result + j;
            return result;
        }
    }

    public static class B extends A {
        private String name;

        public B(int i, int j, String name) {
            super(i, j);
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        protected B clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException();
        }
    }

    public static class C extends B {
        public C(int i, int j, String name) {
            super(i, j, name);
        }

        @Override
        protected C clone() throws CloneNotSupportedException {
            return new C(this.getI(), this.getJ(), this.getName());
        }
    }

    public static void main(String[] args) throws Exception {
        A a = new A(5, 7);
        A a2 = a.clone();
        System.out.println(a);
        System.out.println(a2);
        System.out.println(a.equals(a2));
        System.out.println(a == a2);
        System.out.println(a.hashCode());
        System.out.println(a2.hashCode());
        System.out.println("*************************************************************");
        System.out.println("*************************************************************");
        C c = new C(5, 7, "c");
        C c2 = c.clone();
        System.out.println(c);
        System.out.println(c2);
        System.out.println(c.equals(c2));
        System.out.println(c == c2);
        System.out.println(c.hashCode());
        System.out.println(c2.hashCode());
    }
}
