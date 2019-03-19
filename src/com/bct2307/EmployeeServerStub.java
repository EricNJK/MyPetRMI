package com.bct2307;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EmployeeServerStub implements EmployeeServer {
    String host;
    int port;

    /*
        The server stub implements the methods differently:
            each method packs the commands and arguments
            and sends the request through yourRMI.sendRequest(String[] x)
     */
    public EmployeeServerStub() {
    }

    @Override
    public void initialise(EmployeeList newList) {
        //Marshal list to string
        String request = EmployeeList.marshalToString(newList);
        Socket socket;
        try {
            socket = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("initialise");
            out.println(request);
            System.out.println(in.readLine());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //request[0] = "initialise";
        //sendRequest(request);
    }

    @Override
    public String findByName(String name) {
        String request = "findByName";
        String response = null;

        try {
            Socket s = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println(request);
            out.println(name);
            response = in.readLine();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public EmployeeList findAll() {
        String request = "findAll";
        String response = null;

        try {
            Socket s = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println(request);
            response = in.readLine();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response == null) {
            System.out.println("Error: Response for findAll() is null!");
            throw new AssertionError();
        }
        return EmployeeList.unMarshalString(response);
    }

    @Override
    public void printAll() {
        String request = "printAll";
        try {
            Socket s = new Socket(host, port);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println(request);
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
