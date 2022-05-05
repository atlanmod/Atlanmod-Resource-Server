package org.atlanmod.karadoc.rmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

@SpringBootApplication
public class RmiServer {
    private final static Logger log = LoggerFactory.getLogger(RmiController.class);
    public static void main(String[] args){

            SpringApplication.run(RmiServer.class);

    }
}
