package com.bct2307;

public interface EmployeeServer // extends YourRemote or whatever
{
    void initialise(EmployeeList newList);
    String findByName(String name);
    EmployeeList findAll();
    void printAll();
}

