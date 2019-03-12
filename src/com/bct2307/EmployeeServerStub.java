package com.bct2307;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EmployeeServerStub implements EmployeeServer {
    public String host;
    public int port;

    /*
        The server stub implements the methods differently:
            each method packs the commands and arguments
            and makes a request through yourRMI.sendRequest(String[] x)
     */
    public EmployeeServerStub() {
    }

    @Override
    public void initialise(EmployeeList newList) {
        String[] request = EmployeeList.toStringArray(newList);
        sendRequest(request);
    }

    @Override
    public String findByName(String name) {
        String[] request = {"findByName"};
        String response = null;
        sendRequest(request);
        try {
            Socket s = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            response = in.readLine();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public EmployeeList findAll() {
        String[] request = {"findAll"};
        sendRequest(request);
        String[] response = null;

        try {
            Socket s = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            response = null;
            boolean exitFlag = false;
            int n = 0;
            while (!exitFlag) {
                String str = in.readLine();
                if (str != null) {
                    response[n] = str;
                    n++;
                } else {
                    exitFlag = true;    //no more data
                }
            }
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EmployeeList.parseStringArray(response);
    }

    @Override
    public void printAll() {
        String[] request = {"printAll"};
        sendRequest(request);
    }

    //method to SEND requests only, the caller method listens and parses response from TCP
    private void sendRequest(String[] request) {
        Socket socket;
        try {
            socket = new Socket(host, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            for (String s : request) {
                out.println(s);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
