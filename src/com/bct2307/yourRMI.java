package com.bct2307;

import com.bct2307.registry.LocateSimpleRegistry;
import com.bct2307.registry.SimpleRegistry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class yourRMI {

    static String host;
    static int port;

    // It will use a hash table, which contains ROR together with
    // reference to the remote object.
    // As you can see, the exception handling is not done at all.
    public static void main(String[] args) throws Exception {
        String InitialClassName = args[0];          //="EmployeeListServerImpl";
        String registryHost = args[1];              //="127.0.0.1";
        int registryPort = Integer.parseInt(args[2]);//=2099;
        String serviceName = args[3];               //Server1

        // it should have its own port. assume you hardwire it.
        host = (InetAddress.getLocalHost()).getHostName();
        port = 2222;

        // it now have two classes from MainClassName:
        // (1) the class itself (say EmployeeServerImpl) and
        // (2) its skeleton (EmployeeServer).
        Class initialclass = Class.forName(InitialClassName);
        Class initialskeleton = Class.forName(InitialClassName.replace("Impl", "Stub"));    //InitialClassName + "Stub");

        // you should also create a remote object table here.
        // it is a table of a ROR and a skeleton.
        // as a hint, I give such a table's interface as RORtbl.java.
        RORtbl tbl = new RORtbl();

        // after that, you create one remote object of initialclass.
        Object listServer = initialclass.newInstance();

        // then register it into the table.
        RemoteObjectRef serverRoR;
        serverRoR = tbl.addObj(host, port, listServer);

        //Register to registry
        SimpleRegistry sr = LocateSimpleRegistry.getRegistry(registryHost, registryPort);
        //RemoteObjectRef ror = new RemoteObjectRef(host, port, 1, InitialClassName);
        sr.rebind(serviceName, serverRoR);         // ror);
        System.out.println("Bound to registry");

        // create a server socket: input and output
        ServerSocket serverSoc = new ServerSocket(port);

        //noinspection InfiniteLoopStatement
        while (true) {
            // (1) receives an invocation request.
            // (2) creates a socket and input/output streams.
            // (3) gets the invocation, in martiallled form.
            // (4) gets the real object reference from tbl.
            // (5) does unmarshalling directly and involkes that
            //         object directly.
            // (6) receives the return value, which (if not marshalled
            //     you should marshal it here) and send it out to the
            //     the source of the invoker.
            // (7) closes the socket.
            Socket newsoc = serverSoc.accept();
            System.out.println("yourRMI accepted the request.\n");
            try {
                // input/output streams (remember, TCP is bidirectional).
                BufferedReader in =
                        new BufferedReader(new InputStreamReader(newsoc.getInputStream()));
                PrintWriter out =
                        new PrintWriter(newsoc.getOutputStream(), true);
                String command = in.readLine();
                System.out.println("\tCommand: " + command);
//TODO: Implement using findObject()
                switch (command) {
                    case "initialise": {
                        EmployeeServerImpl server = (EmployeeServerImpl) tbl.findObj(serverRoR);
                        EmployeeList newList;
                        String data = in.readLine();
                        newList = EmployeeList.unMarshalString(data);
                        server.initialise(newList);
                        System.out.println("List initialised");
                        out.println("Successfully initialised");
                        break;
                    }
                    case "printAll": {
                        EmployeeServerImpl server;// = (EmployeeServerImpl) listServer
                        server = (EmployeeServerImpl) tbl.findObj(serverRoR);
                        server.printAll();
                        break;
                    }
                    case "findByName": {
                        String employeeName = in.readLine();
                        EmployeeServerImpl server = (EmployeeServerImpl) tbl.findObj(serverRoR);
                        String result = server.findByName(employeeName);
                        out.println(result);
                        break;
                    }
                    case "findAll": {
                        EmployeeServerImpl server = (EmployeeServerImpl) tbl.findObj(serverRoR);
                        EmployeeList result = server.findAll();
                        String response = EmployeeList.marshalToString(result);
                        out.println(response);
                        break;
                    }
                    default:
                        System.out.println("404");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
