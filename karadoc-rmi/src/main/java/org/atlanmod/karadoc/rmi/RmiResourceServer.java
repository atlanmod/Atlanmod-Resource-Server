package org.atlanmod.karadoc.rmi;

import org.atlanmod.karadoc.core.Filter.FilterID;
import org.eclipse.emf.common.util.URI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiResourceServer extends Remote {
    default String getAvailableResourced() throws RemoteException {
        return null;
    }

    default String getByID(URI id, FilterID filterUri) throws RemoteException {
        return null;
    }
}
