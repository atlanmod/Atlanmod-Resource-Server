package org.atlanmod.karadoc.rmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RmiServer {
    private final static Logger log = LoggerFactory.getLogger(RmiController.class);
    public static void main(String[] args){
        try {
            RmiResourceServer rmiController = new RmiController();
            log.info("created remote object");
            LocateRegistry.createRegistry(1337);

            Naming.rebind("rmi://localhost:1337"+"/v1",rmiController);
            log.info("Rmi server is now online");

        } catch (RemoteException | MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }
}
