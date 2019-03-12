package com.bct2307;

import java.text.DecimalFormat;
import java.util.LinkedList;

public class EmployeeList {
    public String name;
    public int age;
    public String designation;
    public float salary;
    public EmployeeList next;

    //Static method to convert list to String array that
    //  can be sent over TCP
    public static String[] toStringArray(EmployeeList list){
        LinkedList<String> data = new LinkedList<>();
        EmployeeList temp = list;
        do {
            data.add(temp.name);
            data.add(String.valueOf(temp.age));
            data.add(temp.designation);
            data.add(String.valueOf(temp.salary));
            temp = temp.next;
        } while (temp.next != null);
        return (String[]) data.toArray();
    }

    //Static method to generate EmployeeList from String[]
    public static EmployeeList parseStringArray(String[] source){
        EmployeeList result = null, temp;
        String name, designation;
        int age;
        float salary;
        EmployeeList next;

        boolean isFirst = true;
        if (source != null){
            int n = 0;
            do {
                name = source[n];
                age = Integer.parseInt(source[n+1]);
                designation = source[n+2];
                salary = Float.parseFloat(source[n+3]);

                if (isFirst){   //Set first object of linked list
                    result = new EmployeeList(name, age, designation, salary);
                    isFirst = false;
                }
                temp = new EmployeeList(name, age, designation, salary);
                n +=4;  //Go to next employee data
                temp = temp.next;
            } while (n < source.length);
        }
        return result;
    }

    public EmployeeList() {
    }

    public EmployeeList(String name, int age, String designation, float salary) {
        this.name = name;
        this.age = age;
        this.designation = designation;
        this.salary = salary;
        next = null;
    }

    public EmployeeList(String name, int age, String designation, float salary, EmployeeList next) {
        this.name = name;
        this.age = age;
        this.designation = designation;
        this.salary = salary;
        this.next = next;
    }

    public String getDetails() {
        DecimalFormat df = new DecimalFormat("##");
        return ("Name: " + name + "\nAge: " + age + "\nDesignation: " + designation + "\nSalary: " + df.format(salary));
    }
}
