package org.atlanmod.karadoc.server;

import org.atlanmod.karadoc.core.Filter.FilterID;
import org.atlanmod.karadoc.core.ModelLoader;
import org.atlanmod.karadoc.core.ResourceServer;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KaradocServer implements ResourceServer {

    ClassPathResource metamodel = new ClassPathResource("graph_metamodel.ecore");
    ClassPathResource model = new ClassPathResource("graph_model.graph");

    ModelLoader modelLoader;

    public KaradocServer() {
        try {
            modelLoader = new KaradocModelLoader(metamodel.getFile(), model.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EObject> getAvailableResourced() {
        List<EObject> list =  new ArrayList<>();
        modelLoader.getModel().getAllContents().forEachRemaining(list::add);
        return list;
    }

    @Override
    public EObject getByID(URI id, FilterID filterID) {
        return null;
    }
}
