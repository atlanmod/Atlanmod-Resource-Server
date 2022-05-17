package org.atlanmod.karadoc.rest;


import org.atlanmod.karadoc.core.ResourceService;
import org.atlanmod.karadoc.core.Response;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

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
    //TODO: find a better way to return different type in the endpoint
    public Response<?> getModel(@RequestParam(name = "modeluri", required = false) String modelUri) {
        //Get all if no model uri are given
        if(modelUri == null)
            return server.getAll();

        //Or return te resource associated to the uri
        return server.getModel(modelUri);
    }

    @GetMapping(BASEPATH + "modeluris")
    public Response<List<String>> getModelUris() {
        return server.getModelUris();
    }

    @GetMapping(BASEPATH + "modelelement")
    public Response<EObject> getModelElementByName(@RequestParam(name = "modeluri") String modelUri, @RequestParam(name = "elementname") String elementname) {
        return server.getModelElementByName(modelUri, elementname);
    }

    @DeleteMapping(BASEPATH + "models")
    public Response<Boolean> delete(@RequestParam("modeluri") String modelUri) {
        return server.delete(modelUri);
    }

    @PostMapping(BASEPATH + "close")
    public Response<Boolean> close(@RequestParam("modeluri") String modelUri) {
        return server.close(modelUri);
    }

    @PostMapping(BASEPATH + "models")
    public Response<Boolean> create(@RequestParam("modeluri") String modelUri, @RequestParam("updatedModelAsJson") String modelAsJSON) {
        return server.create(modelUri,modelAsJSON);
    }

    @PutMapping(BASEPATH + "models")
    public Response<Boolean> update(@RequestParam("modeluri") String modelUri, @RequestParam("updatedModelAsJson") String updatedModelAsJson) {
        return server.update(modelUri, updatedModelAsJson);
    }

    @GetMapping(BASEPATH + "save")
    public Response<Boolean> save(@RequestParam(name = "modeluri") String modelUri) {return server.save(modelUri);}

    @GetMapping(BASEPATH + "saveall")
    public Response<Boolean> saveAll() {
        return server.saveAll();
    }

    @GetMapping(BASEPATH + "server/ping")
    public Response<Boolean> ping() {
        return server.ping();
    }

    @GetMapping(BASEPATH + "unsubscribe")
    public Response<Boolean> unsubscribe(@RequestParam(name = "modeluri") String modelUri) {
        throw new UnsupportedOperationException("unsubscribe is not supported for the REST endpoint." +
                " Consider using websocket or java rmi");
    }

    @PatchMapping(BASEPATH + "models")
    public Response<Boolean>  executeCommands(@RequestParam(name = "modeluri") String uri){
        return null;
    }

    @GetMapping(BASEPATH + "undo")
    public Response<Resource> undo(@RequestParam(name = "modeluri") String modelUri) {
        throw new UnsupportedOperationException("undo is not supported for the REST endpoint." +
                " Consider using websocket or java rmi");
    }

    @GetMapping(BASEPATH + "redo")
    public  Response<Resource> redo(@RequestParam(name = "modeluri") String modelUri) {
        throw new UnsupportedOperationException("redo is not supported for the REST endpoint." +
                " Consider using websocket or java rmi");
    }
}
