package org.atlanmod.karadoc.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.atlanmod.karadoc.core.ModelProvider;
import org.atlanmod.karadoc.core.ResourceService;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emfjson.jackson.databind.EMFContext;
import org.emfjson.jackson.module.EMFModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Server internal api for endpoint
 */
@Component("DefaultImplementation")
public class KaradocService implements ResourceService {


    private final ModelProvider modelProvider;
    private static final ObjectMapper jsonMapper = EMFModule.setupDefaultMapper();
    private static final Logger log = LoggerFactory.getLogger(KaradocService.class);

    @Autowired
    public KaradocService(ModelProvider modelProvider) {
        this.modelProvider = modelProvider;
    }

    @Override
    public Resource getModel(String modelUri) {
        return modelProvider.getResourceSet().getResource(URI.createURI(modelUri), true);
    }

    @Override
    public EList<Resource> getAll() {
        return modelProvider.getResourceSet().getResources();
    }

    @Override
    public List<String> getModelUris() {
        List<String> list = new ArrayList<>();
        for (Resource res: modelProvider.getResourceSet().getResources()) {
            list.add(res.getURI().toString());
        }
        return list;
    }

    @Override
    public EObject getModelElementByName(String modelUri, String elementname) {

        //NOTE: Very slow way to get an instance by name since we enumerate all the attributes
        // in the resource-set.

        for(Resource resource :  modelProvider.getResourceSet().getResources()) {
            Iterable<EObject> allContents = resource::getAllContents;
            for (EObject obj : allContents) {
                for (EAttribute eAttribute : obj.eClass().getEAllAttributes()) {
                    if (eAttribute.getName().equals("name")) {
                        String name = (String) obj.eGet(eAttribute);
                        if (name.equals(elementname))
                            return obj;
                    }
                }
            }
        }
        throw new NoSuchElementException("no element named " + elementname + " found");
    }

    @Override
    public boolean delete(String modelUri) {
        log.info("deleting {}", modelUri);
        return modelProvider.getResourceSet().getResources().remove(this.getModel(modelUri));
    }

    @Override
    public boolean close(String modelUri) {
        return false;
    }

    @Override
    public boolean create(String modelUri, String modelAsJSON) {
        try {
            jsonMapper.reader()
                    .withAttribute(EMFContext.Attributes.RESOURCE_SET, this.modelProvider.getResourceSet())
                    .withAttribute(EMFContext.Attributes.RESOURCE_URI, URI.createURI(modelUri))
                    .forType(Resource.class)
                    .readValue(modelAsJSON);
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;

        }

    }

    @Override
    public Resource update(String modelUri, Resource updatedModel) {
        return null;
    }

    @Override
    public void save(String modelUri) throws IOException {
        modelProvider.getResourceSet().getResource(URI.createURI(modelUri), true).save(Collections.emptyMap());
        log.info("saved {}", modelUri);
    }

    @Override
    public boolean saveAll() {

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
            }else {
                log.info("Save completed with error!");
            }

            return successFlag;

    }

    @Override
    public boolean ping() {
        return true;
    }

    @Override
    public boolean unsubscribe(String modelUri) {
        return false;
    }

    @Override
    public Resource undo(String modelUri) {
        return null;
    }

    @Override
    public Resource redo(String modelUri) {
        return null;
    }
}
