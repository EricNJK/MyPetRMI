package com.bct2307;

public class RemoteObjectRef {
    public String IP_adr;
    public int Port;
    public int Obj_Key;
    public String Remote_Interface_Name;

    public RemoteObjectRef(String ip, int port, int obj_key, String rInterfaceName) {
        IP_adr = ip;
        Port = port;
        Obj_Key = obj_key;
        Remote_Interface_Name = rInterfaceName;
    }

    // this method is important, since it is a stub creator.

    public Object localise() {
        //
        //Implement this as you like: essentially you should
        //create a new stub object and returns it.
        //Assume the stub class has the name e.g.
        //
        //Remote_Interface_Name + "_stub".
        //
        //Then you can create a new stub as follows:
        //
        Class c;
        Object o;
        try {
            c = Class.forName(Remote_Interface_Name.replace("Impl", "Stub")); //Remote_Interface_Name + "Stub");
            o = c.newInstance();
            return  o;
        } catch (Exception e) {
            e.printStackTrace();
        }

        //
        //For this to work, your stub should have a constructor without arguments.
        //You know what it does when it is called: it gives communication module
        //all what it got (use CM's static methods), including its method name,
        //arguments etc., in a marshaled form, and CM (yourRMI) sends it out to
        //another place.
        //Here let it return null.
        //
        return null;
    }
}
