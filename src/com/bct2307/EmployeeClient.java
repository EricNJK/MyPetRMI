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

import java.io.IOException;

public class EmployeeClient {
    // Convert to handle EmployeeList
    // the main takes three arguments:
    // (0) a host. 
    // (1) a port.
    // (2) a service name.
    // (3) a file name as above. // Unused
    public static void main(String[] args)
            throws IOException {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String serviceName = args[2];
        //BufferedReader in = new BufferedReader(new FileReader(args[3]));
        //DecimalFormat df = new DecimalFormat(".##");

        // locate the registry and get ror.
        SimpleRegistry sr =
                LocateSimpleRegistry.getRegistry(host, port);
        RemoteObjectRef ror;// = null;
        if (sr != null) {
            ror = sr.lookup(serviceName);
        } else {
            System.out.println("Server not found!");
            return;
        }

        // get (create) the stub out of ror.
        EmployeeServerStub server = (EmployeeServerStub) ror.localise();
        server.host = ror.IP_adr;
        server.port = ror.Port;
        // reads the data and make a "local" employee list.
        // later this is sent to the server.
        // again no error check!
        EmployeeList l;
        l = new EmployeeList("Jane Doe", 25, "Supervisor", 20000);
        l.next = new EmployeeList("Philip Okeyo", 28, "Manager", 31000);
        l.next.next = new EmployeeList("Alice H", 30, "C.E.O", 45000);
        l.next.next.next = new EmployeeList("Danson M", 22, "Developer", 15000);
        // the final value of list should be the initial head of
        // the list.

        // we print out the local employeeList.
        System.out.println("This is the original list.");
        EmployeeList temp = l;

        while (temp != null) {
            System.out.println
                    ("name: " + temp.name + ", " +
                            "age: " + temp.age + ", " +
                            "designation: " + temp.designation + ", " +
                            "salary: " + temp.salary);
            temp = temp.next;
        }

        // test the initialise.
        server.initialise(l);

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
                            "designation: " + temp.designation + ", " +
                            "salary: " + temp.salary);
            temp = temp.next;
        }

        // test the printAll.
        System.out.println("\n We test the remote site printing.");
        // here is a test.
        server.printAll();

    }
}

	    
			
			
	

