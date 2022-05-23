package org.atlanmod.karadoc.rmi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.atlanmod.karadoc.core.ResourceService;
import org.atlanmod.karadoc.core.Response;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emfjson.jackson.module.EMFModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

@Controller
@ComponentScan(basePackages = "org.atlanmod.karadoc")
public class RmiController extends UnicastRemoteObject implements RmiResourceServer {

    private final ObjectMapper mapper =  EMFModule.setupDefaultMapper();
    private final ResourceService server;
    final Logger log = LoggerFactory.getLogger(RmiController.class);


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
    public Response<Resource> getModel(String modelUri) {
        return server.getModel(modelUri);
    }

    @Override
    public Response<List<Resource>> getAll() {
        return server.getAll();
    }

    @Override
    public Response<List<String>> getModelUris() {
        return server.getModelUris();
    }

    @Override
    public Response<EObject> getModelElementByName(String modelUri, String elementname) {
        return server.getModelElementByName(modelUri, elementname);
    }

    @Override
    public Response<Boolean> delete(String modelUri) {
        return server.delete(modelUri);
    }

    @Override
    public Response<Boolean> close(String modelUri) {
        return server.close(modelUri);
    }

    @Override
    public Response<Boolean> create(String modelUri, String modelAsJSON) {
        return server.create(modelUri, modelAsJSON);
    }

    @Override
    public Response<Boolean> update(String modelUri, String updatedModel) {
        return server.update(modelUri, updatedModel);
    }

    @Override
    public Response<String> edit(String modelUri, String jsonPatchAsString, String format) {
        return server.edit(modelUri, jsonPatchAsString, format);
    }

    @Override
    public Response<Boolean> save(String modelUri) {
        return server.save(modelUri);
    }

    @Override
    public Response<Boolean> saveAll() {
        return server.saveAll();
    }

    @Override
    public Response<Boolean> ping() {
        return server.ping();
    }

    @Override
    public Response<Boolean> unsubscribe(String modelUri) {
        return server.unsubscribe(modelUri);
    }

    @Override
    public Response<Resource> undo(String modelUri) {
        return server.undo(modelUri);
    }

    @Override
    public Response<Resource> redo(String modelUri) {
        return server.redo(modelUri);
    }
}
