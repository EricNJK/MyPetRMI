package com.bct2307;
// a client for EmployeeServer.
// it uses EmployeeServer as an interface, and tests all methods.

// It reads data from a file containing the service name and city-zip 
// pairs in the following way:
//   name1
//   age1
//   designation1
//   salary1
//	 ...
//   end.

import com.bct2307.registry.LocateSimpleRegistry;
import com.bct2307.registry.SimpleRegistry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

public class EmployeeClient {
    //TODO: Convert to handle EmployeeList
    // the main takes three arguments:
    // (0) a host. 
    // (1) a port.
    // (2) a service name.
    // (3) a file name as above. 
    public static void main(String[] args)
            throws IOException {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String serviceName = args[2];
        BufferedReader in = new BufferedReader(new FileReader(args[3]));
        DecimalFormat df = new DecimalFormat("##");

        // locate the registry and get ror.
        SimpleRegistry sr =
                LocateSimpleRegistry.getRegistry(host, port);
        RemoteObjectRef ror = sr.lookup(serviceName);

        // get (create) the stub out of ror.
        EmployeeServerStub server = (EmployeeServerStub) ror.localise();
        server.host = ror.IP_adr;
        server.port = ror.Port;
        // reads the data and make a "local" employee list.
        // later this is sent to the server.
        // again no error check!
        EmployeeList l = null;
        EmployeeList tmp;
        boolean flag = true, isFirstNode = true;
        while (flag) {
            String name = in.readLine();
            int age = Integer.parseInt(in.readLine().trim());
            String designation = in.readLine();
            float salary = Float.parseFloat(in.readLine().trim());

            if (name == null)
                flag = false;    //There is no valid employee
            else        //Data is valid
                if (isFirstNode) {      //Create first node on the list
                    l = new EmployeeList(name.trim(), age, designation.trim(), salary);
                    tmp = l.next;       //tmp = 2nd node
                    isFirstNode = false;//Show that list is not empty, prevent overwriting
                } else {
                    tmp = new EmployeeList(name.trim(), age, designation.trim(), salary);
                    tmp = tmp.next;     //Go to next in list
                }
        }
        // the final value of list should be the initial head of
        // the list.

        // we print out the local employeeList.
        System.out.println("This is the original list.");
        EmployeeList temp = l;

        while (temp != null) {
            System.out.println
                    ("name: " + temp.name + ", " +
                            "age: " + temp.age + ", " +
                            "designation: " + temp.age + ", " +
                            "salary: " + df.format(temp.salary));
            temp = temp.next;
        }

        // test the initialise.
        server.initialise(l);
        System.out.println("\n Server initialised.");

        // test the findByName.
        System.out.println("\n This is the remote list given by findByName.");
        temp = l;
        while (temp != null) {
            // here is a test.
            String details = server.findByName(temp.name);
            System.out.println(details);
            temp = temp.next;
        }

        // test findAll.
        System.out.println("\n This is the remote list given by findAll().");
        temp = server.findAll();
        while (temp != null) {
            System.out.println
                    ("name: " + temp.name + ", " +
                            "age: " + temp.age + ", " +
                            "designation: " + temp.age + ", " +
                            "salary: " + df.format(temp.salary));
            temp = temp.next;
        }

        // test the printAll.
        System.out.println("\n We test the remote site printing.");
        // here is a test.
        server.printAll();
    }
}

	    
			
			
	

