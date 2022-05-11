package org.atlanmod.karadoc.rest;


import org.atlanmod.karadoc.core.ResourceService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@ComponentScan(basePackages = "org.atlanmod.karadoc")
public class KaradocRestController {


    public static final String BASEPATH = "api/v1/";
    private final ResourceService server;

    @Autowired
    public KaradocRestController(ResourceService server) {
        this.server = server;
    }


    @GetMapping(BASEPATH + "models")
    public List<Resource> getModel(@RequestParam(name = "modeluri", required = false, defaultValue = "undefined") String modelUri) {
        if(modelUri.equals("undefined"))
            return server.getAll();
        ArrayList<Resource> resourcesList = new ArrayList<>();
        resourcesList.add(server.getModel(modelUri));
        return resourcesList;
    }

    @GetMapping(BASEPATH + "modeluris")
    public List<String> getModelUris() {
        return server.getModelUris();
    }

    @GetMapping(BASEPATH + "modelelement")
    public EObject getModelElementByName(@RequestParam(name = "modeluri") String modelUri, @RequestParam(name = "elementname") String elementname) {
        return server.getModelElementByName(modelUri, elementname);
    }

    @GetMapping(BASEPATH + "delete")
    public boolean delete(@RequestParam("modeluri") String modelUri) {
        return server.delete(modelUri);
    }

    public boolean close(String modelUri) {
        return false;
    }

    public boolean create(String modelUri, Resource model) {
        return false;
    }

    public Resource update(String modelUri, Resource updatedModel) {
        return null;
    }

    @GetMapping(BASEPATH + "save")
    public void save(@RequestParam(name = "modeluri") String modelUri) throws IOException {
            server.save(modelUri);
    }

    @GetMapping(BASEPATH + "saveall")
    public boolean saveAll() {
        return server.saveAll();
    }

    @GetMapping(BASEPATH + "server/ping")
    public boolean ping() {
        return server.ping();
    }

    @GetMapping(BASEPATH + "unsubscribe")
    public boolean unsubscribe(String modelUri) {
        return false;
    }

    @GetMapping(BASEPATH + "undo")
    public Resource undo(String modelUri) {
        throw new UnsupportedOperationException("undo is not supported for the REST endpoint." +
                " Consider using websocket or java rmi");
    }

    @GetMapping(BASEPATH + "redo")
    public Resource redo(String modelUri) {
        throw new UnsupportedOperationException("redo is not supported for the REST endpoint." +
                " Consider using websocket or java rmi");
    }
}
