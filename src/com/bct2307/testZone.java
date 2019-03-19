package com.bct2307;

public class testZone {
    public static void main(String[] args) {
        //String source = "Philip Okeyo##28##Manager##31000##";
        //EmployeeList list = EmployeeList.unMarshalString(source);

        RemoteObjectRef ref = new RemoteObjectRef("XPLICIT", 2222, 1, "com.bct2307.EmployeeServerImpl");
        RemoteObjectRef ref2 = new RemoteObjectRef("XPLICIT", 2222, 1, "com.bct2307.EmployeeServerImpl");

        System.out.println(ref.hashCode());
        System.out.println(ref2.hashCode());
        System.out.println(ref.equals(ref2));

        /*EmployeeList l;
        l = new EmployeeList("Jane Doe", 25, "Supervisor", 20000);
        l.next = new EmployeeList("Philip Okeyo", 28, "Manager", 31000);
        l.next.next = new EmployeeList("Alice H", 30, "C.E.O", 45000);
        l.next.next.next = new EmployeeList("Danson M", 22, "Developer", 15000);

        String string = EmployeeList.marshalToString(l);
        l = EmployeeList.unMarshalString(string);*/
        System.out.println("End");
    }

}
