package com.bct2307;

// This is simple. ROR needs a new object key for each remote object (or its skeleton).
// This can be done easily, for example by using a counter.
// We also assume a remote object implements only one interface, which is a remote interface.

import java.util.Hashtable;

public class RORtbl {
    // I omit all instance variables. you can use hash table, for example.
    // The table would have a key by ROR.
    private Hashtable<RemoteObjectRef, Object> table = new Hashtable<>();
    private int rorCount = 1;

    // make a new table.
    public RORtbl() {
    }

    // add a remote object to the table.
    // Given an object, you can get its class, hence its remote interface.
    // Using it, you can construct a ROR.
    // The host and port are not used unless it is exported outside.
    // In any way, it is better to have it for uniformity.
    public RemoteObjectRef addObj(String host, int port, Object o) {
        String interfaceName = o.getClass().getName();
        RemoteObjectRef ref = new RemoteObjectRef(host, port, rorCount, interfaceName);
        table.put(ref, o);
        rorCount++;
        return ref;
    }

    // given ror, findByName the corresponding object.
    public Object findObj(RemoteObjectRef ror) throws Exception {
        // if you use a hash table this is easy.

        if (table.containsKey(ror))
            return table.get(ror);
        else
            throw new Exception("Remote Object not registered");
    }
}
