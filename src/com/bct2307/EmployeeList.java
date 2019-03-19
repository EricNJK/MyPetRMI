package com.bct2307;


public class EmployeeList {//implements Serializable {
    String name;
    int age;
    String designation;
    int salary;
    EmployeeList next;

    static EmployeeList unMarshalString(String source) {
        int nameIndex = 0, ageIndex, designationIndex, salaryIndex, nextEmpIndex;
        EmployeeList result = null, temp = null;
        boolean isFirst = true;

        while (true) {
            String name, designation;
            int age, salary;

            if (!source.contains("##")) {
                break;
            }

            ageIndex = source.indexOf("##") + 2;
            designationIndex = source.indexOf("##", ageIndex) + 2;
            salaryIndex = source.indexOf("##", designationIndex) + 2;
            nextEmpIndex = source.indexOf("##", salaryIndex) + 2;

            name = source.substring(nameIndex, ageIndex - 2);
            age = Integer.parseInt(source.substring(ageIndex, designationIndex - 2));
            designation = source.substring(designationIndex, salaryIndex - 2);
            salary = Integer.valueOf(source.substring(salaryIndex, nextEmpIndex - 2));

            source = source.substring(nextEmpIndex);

            if (isFirst) {
                result = new EmployeeList(name, age, designation, salary);
                temp = result;
                isFirst = false;
            } else {
                temp.next = new EmployeeList(name, age, designation, salary);
                temp = temp.next;
            }
        }
        return result;
    }

    static String marshalToString(EmployeeList source) {
        EmployeeList temp = source;
        String result = "";
        String padding = "##";

        while (temp != null) {
            result = result.concat(temp.name + padding);
            result = result.concat(temp.age + padding);
            result = result.concat(temp.designation + padding);
            result = result.concat(temp.salary + padding);
            temp = temp.next;
        }
        return result;
    }

    EmployeeList(String name, int age, String designation, int salary) {
        this.name = name;
        this.age = age;
        this.designation = designation;
        this.salary = salary;
        next = null;
    }

    @Override
    public String toString() {
        return ("Name: " + name + ",\tAge: " + age + ",\tDesignation: " + designation + ",\tSalary: " + salary);
    }
}
/*
    //Static method to convert list to String array that
    //  can be sent over TCP
    @Deprecated
    public static String[] toStringArray(EmployeeList list) {
        LinkedList<String> data = new LinkedList<String>();
        EmployeeList temp = list;
        do {
            data.add(temp.name);
            data.add(String.valueOf(temp.age));
            data.add(temp.designation);
            data.add(String.valueOf(temp.salary));
            temp = temp.next;
        } while (temp.next != null);
        return data.toArray(new String[0]);
    }

    //Static method to generate EmployeeList from String[]
    @Deprecated
    public static EmployeeList parseStringArray(String[] source) {
        EmployeeList result = null, temp;
        String name, designation;
        int age;
        int salary;
        EmployeeList next;

        boolean isFirst = true;
        if (source != null) {
            int n = 0;
            do {
                name = source[n];
                age = Integer.parseInt(source[n + 1]);
                designation = source[n + 2];
                salary = Integer.parseInt(source[n + 3]);

                if (isFirst) {   //Set first object of linked list
                    result = new EmployeeList(name, age, designation, salary);
                    isFirst = false;
                }
                temp = new EmployeeList(name, age, designation, salary);
                n += 4;  //Go to next employee data
                temp = temp.next;
            } while (n < source.length);
        }
        return result;
    }
*/
        /*
        //method to read file with employee data
        EmployeeList tmp = l;
        boolean flag = true, isFirstNode = true;
        while (flag) {
            String name = in.readLine();
            if (name != null && !name.equals("")) {
                int age = Integer.parseInt(in.readLine());
                String designation = in.readLine();
                int salary = Integer.parseInt(in.readLine());

                if (isFirstNode) {      //Create first node on the list
                    l = new EmployeeList(name.trim(), age, designation.trim(), salary);
                    tmp = l.next;       //tmp = 2nd node
                    isFirstNode = false;//Show that list is not empty, prevent overwriting
                } else {
                    tmp = new EmployeeList(name.trim(), age, designation.trim(), salary);
                    tmp = tmp.next;     //Go to next in list
                }
            } else
                flag = false;
        }*/