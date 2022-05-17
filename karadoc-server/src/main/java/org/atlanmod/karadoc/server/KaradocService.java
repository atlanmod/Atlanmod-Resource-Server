package org.atlanmod.karadoc.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.atlanmod.karadoc.core.ModelProvider;
import org.atlanmod.karadoc.core.ResourceService;
import org.atlanmod.karadoc.core.Response;
import org.atlanmod.karadoc.core.ResponseType;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emfjson.jackson.databind.EMFContext;
import org.emfjson.jackson.module.EMFModule;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Server internal api for endpoint
 */
@Component("DefaultImplementation")
public class KaradocService implements ResourceService {


    private static final String COULD_NOT_RESOLVE_URI = "Could not resolve URI {}";
    private final ModelProvider modelProvider;
    private static final ObjectMapper jsonMapper = EMFModule.setupDefaultMapper();
    private static final Logger log = LoggerFactory.getLogger(KaradocService.class);

    private boolean isModelURI(String uri){
        return modelProvider.getResourceSet().getResource(URI.createURI(uri),false) != null;
    }
    
    @Autowired
    public KaradocService(ModelProvider modelProvider) {
        this.modelProvider = modelProvider;
    }

    @Override
    public Response<Resource> getModel(String modelUri) {
        
        if (!isModelURI(modelUri)){
            log.warn(COULD_NOT_RESOLVE_URI, modelUri);
            return new Response<>(ResponseType.URINOTRESOLVEDERROR, null);
        }
        @NotNull
        Resource resource = modelProvider.getResourceSet().getResource(URI.createURI(modelUri), false);
        return new Response<>(ResponseType.SUCCESS, resource);
    }

    @Override
    public Response<List<Resource>> getAll() {
        return new Response<>(ResponseType.SUCCESS,
                new ArrayList<>(modelProvider.getResourceSet().getResources()));
    }

    @Override
    public Response<List<String>> getModelUris() {

        return new Response<>(ResponseType.SUCCESS,
                modelProvider.getResourceSet().getResources()
                .stream().map(resource -> resource.getURI().toString()).collect(Collectors.toList())
        );
    }

    @Override
    public Response<EObject> getModelElementByName(String modelUri, String elementname) {

        //NOTE: Very slow way to get an instance by name since we enumerate all the attributes
        // in the resource-set.

        for(Resource resource :  modelProvider.getResourceSet().getResources()) {
            Iterable<EObject> allContents = resource::getAllContents;
            for (EObject obj : allContents) {
                for (EAttribute eAttribute : obj.eClass().getEAllAttributes()) {
                    if (eAttribute.getName().equals("name")) {
                        String name = (String) obj.eGet(eAttribute);
                        if (name.equals(elementname))
                            return new Response<>(ResponseType.SUCCESS, obj);
                    }
                }
            }
        }
        return new Response<>(ResponseType.URINOTRESOLVEDERROR, null);
    }
    


    @Override
    public Response<Boolean> close(String modelUri) {

        if (!isModelURI(modelUri)){
            log.warn(COULD_NOT_RESOLVE_URI, modelUri);
            return new Response<>(ResponseType.URINOTRESOLVEDERROR, null);
        }
        @NotNull
        Resource resource = modelProvider.getResourceSet().getResource(URI.createURI(modelUri), false);

        log.info("closing {}", modelUri);

        EList<Resource> resources = modelProvider.getResourceSet().getResources();
        return new Response<>(ResponseType.SUCCESS, resources.remove(resource));
    }

    @Override
    public Response<Boolean> delete(String modelUri) {

        if (!isModelURI(modelUri)){
            log.warn(COULD_NOT_RESOLVE_URI, modelUri);
            return new Response<>(ResponseType.URINOTRESOLVEDERROR, null);
        }

        @NotNull
        Resource resource = modelProvider.getResourceSet().getResource(URI.createURI(modelUri), false);

        log.info("deleting {}", modelUri);

        EList<Resource> resources = modelProvider.getResourceSet().getResources();
        modelProvider.delete(URI.createURI(modelUri));
        return new Response<>(ResponseType.SUCCESS, resources.remove(resource));
    }

    @Override
    public Response<Boolean> create(String modelUri, String modelAsJSON) {

        if(isModelURI(modelUri)){
            log.warn("URI already exist for {}", modelUri);
            return new Response<>(ResponseType.MODELALREADYEXIST);
        }

        try {
            jsonMapper.reader()
                    .withAttribute(EMFContext.Attributes.RESOURCE_SET, this.modelProvider.getResourceSet())
                    .withAttribute(EMFContext.Attributes.RESOURCE_URI, URI.createURI(modelUri))
                    .forType(Resource.class)
                    .readValue(modelAsJSON);
            return new Response<>(ResponseType.SUCCESS, true);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response<>(ResponseType.INVALIDJSONERROR);
        }

    }



    @Override
    public Response<Boolean> update(String modelUri, String updatedModel) {

        if (!isModelURI(modelUri)){
            log.warn(COULD_NOT_RESOLVE_URI, modelUri);
            return new Response<>(ResponseType.URINOTRESOLVEDERROR, null);
        }


        //FIXME: fragile way to reuse code because it does not account
        // for possible error thrown in the called method
        // even though all cases should already been check
        delete(modelUri);
        create(modelUri,updatedModel);
        return new Response<>(ResponseType.SUCCESS);
        
    }

    @Override
    public Response<String> edit(String modelUri, String jsonPatchAsString, String format) {
        return null;
    }

    @Override
    public Response<Boolean> save(String modelUri)  {
        try {
            modelProvider.getResourceSet().getResource(URI.createURI(modelUri), true).save(Collections.emptyMap());
            log.info("saved {}", modelUri);
            return new Response<>(ResponseType.SUCCESS, true);
        }catch (IOException e){
            log.info("could not save {}. See detail below", modelUri);
            e.printStackTrace();
            return new Response<>(ResponseType.FILEACCESSERROR, false);
        }

    }

    @Override
    public Response<Boolean> saveAll() {

            boolean successFlag = true;

            for(Resource res : modelProvider.getResourceSet().getResources()){
                try {
                    res.save(Collections.emptyMap());
                } catch (IOException e) {
                    e.printStackTrace();
                    successFlag = false;
                }
            }

            if(successFlag){
                log.info("saved all resources successfully");
                return new Response<>(ResponseType.SUCCESS, true);
            }else {
                log.info("Save completed with error! See stacktrace above");
                return new Response<>(ResponseType.FILEACCESSERROR, false);
            }
    }

    @Override
    public Response<Boolean> ping() {
        return new Response<>(ResponseType.SUCCESS);
    }

    @Override
    public  Response<Boolean> unsubscribe(String modelUri) {
        return null;
    }

    @Override
    public  Response<Resource> undo(String modelUri) {
        return null;
    }

    @Override
    public Response<Resource> redo(String modelUri) {
        return null;
    }
}
