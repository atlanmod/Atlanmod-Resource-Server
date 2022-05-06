package org.atlanmod.karadoc.rmi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.atlanmod.karadoc.core.ResourceService;
import org.atlanmod.karadoc.core.filter.FilterID;
import org.eclipse.emf.common.util.URI;
import org.emfjson.jackson.module.EMFModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

@Controller
@ComponentScan(basePackages = "org.atlanmod.karadoc")
public class RmiController extends UnicastRemoteObject implements RmiResourceServer {

    private final ObjectMapper mapper =  EMFModule.setupDefaultMapper();
    private final ResourceService server;
    final Logger log = LoggerFactory.getLogger(RmiController.class);



    @Autowired
    public RmiController(ResourceService server) throws RemoteException {
        this.server = server;
        log.info("created remote object");
        LocateRegistry.createRegistry(1337);

        try {
            Naming.rebind("rmi://localhost:1337"+"/v1",this);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        log.info("Rmi server is now online");

    }


    @Override
    public String getAvailableResourced() throws RemoteException {
        try {
            assert server != null;
            return mapper.writeValueAsString(server.getAvailableResources());
        } catch (JsonProcessingException e) {
            return null;
        }

    }

    @Override
    public String getByID(URI id, FilterID filterUri) throws RemoteException {
        return null;
    }
}
