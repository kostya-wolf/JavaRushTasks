package com.javarush.task.task20.task2020;

import java.io.*;
import java.util.logging.Logger;

/* 
Сериализация человека
*/
public class Solution {

    public static class Person implements Serializable {
        String firstName;
        String lastName;
        transient String fullName;
        transient final String greetingString;
        String country;
        Sex sex;
        transient PrintStream outputStream;
        transient Logger logger;

        Person(String firstName, String lastName, String country, Sex sex) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.fullName = String.format("%s, %s", lastName, firstName);
            this.greetingString = "Hello, ";
            this.country = country;
            this.sex = sex;
            this.outputStream = System.out;
            this.logger = Logger.getLogger(String.valueOf(Person.class));
        }
    }

    enum Sex {
        MALE,
        FEMALE
    }

    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
        Person person = new Person("name", "lastname", "RUSSIA", Sex.MALE);

        System.out.println(person.firstName);
        System.out.println(person.lastName);
        System.out.println(person.fullName);
        System.out.println(person.country);
        System.out.println(person.sex);
        System.out.println(person.greetingString);
        System.out.println(person.outputStream);
        System.out.println(person.logger);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("cat.dat"));
        objectOutputStream.writeObject(person);
        objectOutputStream.close();

        System.out.println("===================================================");

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("cat.dat"));
        Person person2 = (Person) objectInputStream.readObject();
        objectInputStream.close();

        System.out.println(person2.firstName);
        System.out.println(person2.lastName);
        System.out.println(person2.fullName);
        System.out.println(person2.country);
        System.out.println(person2.sex);
        System.out.println(person2.greetingString);
        System.out.println(person2.outputStream);
        System.out.println(person2.logger);
    }
}