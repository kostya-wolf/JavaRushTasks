package com.javarush.task.task29.task2909.human;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class University {
    private List<Student> students = new ArrayList<>();
    private String name;
    private int age;

    public University(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Student getStudentWithAverageGrade(double averageGrade) {
        for (Student s: students) {
            if (s.getAverageGrade() == averageGrade) {
                return s;
            }
        }
        return null;
    }

    public Student getStudentWithMaxAverageGrade() {
        ArrayList<Student> tempStudents = new ArrayList<>(students);
        tempStudents.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return Double.compare(o2.getAverageGrade(), o1.getAverageGrade());
            }
        });
        return tempStudents.get(0);
    }

    public Student getStudentWithMinAverageGrade() {
        ArrayList<Student> tempStudents = new ArrayList<>(students);
        tempStudents.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return Double.compare(o1.getAverageGrade(), o2.getAverageGrade());
            }
        });
        return tempStudents.get(0);
    }

    public void expel(Student student) {
        students.remove(student);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}