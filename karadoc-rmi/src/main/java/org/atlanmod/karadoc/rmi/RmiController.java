package org.atlanmod.karadoc.rmi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.atlanmod.karadoc.core.Filter.FilterID;
import org.atlanmod.karadoc.server.KaradocServer;
import org.eclipse.emf.common.util.URI;
import org.emfjson.jackson.module.EMFModule;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiController extends UnicastRemoteObject implements RmiResourceServer {

    private final ObjectMapper mapper =  EMFModule.setupDefaultMapper();
    private final KaradocServer server = new KaradocServer();

    public RmiController() throws RemoteException {
        super();
    }

    @Override
    public String getAvailableResourced() throws RemoteException {
        try {
            return mapper.writeValueAsString(server.getAvailableResourced());
        } catch (JsonProcessingException e) {
            return null;
        }

    }

    @Override
    public String getByID(URI id, FilterID filterUri) throws RemoteException {
        return null;
    }
}
