package com.javarush.task.task20.task2013;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/* 
Externalizable Person
*/
public class Solution {
    public static class Person implements Externalizable {
        private String firstName;
        private String lastName;
        private int age;
        private Person mother;
        private Person father;
        private List<Person> children;

        public Person() {
        }

        public Person(String firstName, String lastName, int age) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
        }

        public void setMother(Person mother) {
            this.mother = mother;
        }

        public void setFather(Person father) {
            this.father = father;
        }

        public void setChildren(List<Person> children) {
            this.children = children;
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject(firstName);
            out.writeObject(lastName);
            out.writeInt(age);
            out.writeObject(mother);
            out.writeObject(father);
            out.writeObject(children);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            firstName = (String)in.readObject();
            lastName = (String)in.readObject();
            age = in.readInt();
            mother = (Person)in.readObject();
            father = (Person)in.readObject();
            Object o = in.readObject();
            if (o == null) o = new ArrayList<Person>();
            if (o instanceof List){
                children = (List<Person>)o;
            }
        }

    }

    public static void main(String[] args) {
        Person human = new Person("Name", "familyName", 45);
        Person father = new Person("Ot", "familyName", 70);
        Person mother = new Person("Mot", "familyName", 70);
        Person child1 = new Person("Son", "familyName", 3);
        Person child2 = new Person("Daught", "familyName", 3);
        List<Person> children = new ArrayList<>();
        children.add(child1);
        children.add(child2);
        human.setChildren(children);
        human.setFather(father);
        human.setMother(mother);


        try {
            FileOutputStream fileOutputStream = new FileOutputStream("test.dat");
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(human);
            fileOutputStream.close();
            outputStream.close();

            FileInputStream fileInputStream = new FileInputStream("test.dat");
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            Person human2 = (Person) inputStream.readObject();
            System.out.println("human2: "+human2.firstName+ " " + human2.lastName+ " " + human2.age);
            System.out.println("human2: "+human2.father.firstName+ " " + human2.father.lastName+ " " + human2.father.age);
            System.out.println("human2: "+human2.mother.firstName+ " " + human2.mother.lastName+ " " + human2.mother.age);
            System.out.println("human2: "+human2.children.get(0).firstName + " " + human2.children.get(1).firstName);
            fileInputStream.close();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
