package org.atlanmod.karadoc.server;

import org.atlanmod.karadoc.core.ModelProvider;
import org.atlanmod.karadoc.core.ResourceService;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Server internal api for endpoint
 */
@Component("DefaultImplementation")
public class KaradocService implements ResourceService {


    private final ModelProvider modelProvider;
    private final ResourceSet resourceSet;

    @Autowired
    public KaradocService(ModelProvider modelProvider) {
        this.modelProvider = modelProvider;
        resourceSet = modelProvider.getResourceSet();
    }

    @Override
    public Resource getModel(String modelUri) {
        return resourceSet.getResource(URI.createURI(modelUri), true);
    }

    @Override
    public EList<Resource> getAll() {
        return resourceSet.getResources();
    }

    @Override
    public List<String> getModelUris() {
        List<String> list = new ArrayList<>();
        for (Resource res: resourceSet.getResources()) {
            list.add(res.getURI().toString());
        }
        return list;
    }

    @Override
    public Resource getMetaModel() {
        return modelProvider.getMetamodel();
    }

    @Override
    public EObject getModelElementByName(String modelUri, String elementname) {
        return null;
    }

    @Override
    public boolean delete(String modelUri) {
        return resourceSet.getResources().remove(this.getModel(modelUri));
    }

    @Override
    public boolean close(String modelUri) {
        return false;
    }

    @Override
    public boolean create(String modelUri, Resource model) {
        return false;
    }

    @Override
    public Resource update(String modelUri, Resource updatedModel) {
        return null;
    }

    @Override
    public void save(String modelUri) throws IOException {
        resourceSet.getResource(URI.createURI(modelUri), true).save(Collections.emptyMap());
    }

    @Override
    public boolean saveAll() {

            boolean succesFlag = true;

            for(Resource res : resourceSet.getResources()){
                try {
                    res.save(Collections.emptyMap());
                } catch (IOException e) {
                    succesFlag = false;
                }
            }

            return succesFlag;

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
