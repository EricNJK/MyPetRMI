package com.bct2307;

// in implementation, you do not have to extend this as in Java RMI.
// in your design, however, you can do so.
// it is assumed that this is not directly called but as in:
//
//   java yourRMI EmployeeServerImpl registryhost resigstryport servicename
//
// therefore it does not contain main: new object creation, binding etc. is 
// done via your RMI.

public class EmployeeServerImpl implements EmployeeServer {
    EmployeeList list;

    // this is a constructor.
    public EmployeeServerImpl() {
        list = null;
    }

    // when this is called, marshalled data
    // should be sent to this remote object,
    // and reconstructed.
    public void initialise(EmployeeList newList) {
        list = newList;
    }

    // basic function: gets a city name, returns the zip code.
    public String findByName(String name) {
        // search the list.
        EmployeeList temp = list;
        while (temp != null && !temp.name.equals(name))
            temp = temp.next;

        // the result is either null or we found the match.
        if (temp == null)
            return null;
        else
            return temp.getDetails();
    }

    // this very short method should send the marshalled 
    // whole list to the client.
    public EmployeeList findAll() {
        return list;
    }

    // this method does printing in the remote site, not locally.
    public void printAll() {
        EmployeeList temp = list;
        while (temp != null) {
            System.out.println(temp.getDetails());
            temp = temp.next;
        }
    }
}
