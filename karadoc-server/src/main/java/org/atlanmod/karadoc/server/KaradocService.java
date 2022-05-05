package org.atlanmod.karadoc.server;

import org.atlanmod.karadoc.core.filter.FilterID;
import org.atlanmod.karadoc.core.ModelProvider;
import org.atlanmod.karadoc.core.ResourceService;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Server internal api for endpoint
 */
@Component("DefaultImplementation")
public class KaradocService implements ResourceService {


    private final ModelProvider modelProvider;

    @Autowired
    public KaradocService(ModelProvider modelProvider) {
        this.modelProvider = modelProvider;
    }

    @Override
    public List<EObject> getAvailableResourced() {
        List<EObject> list =  new ArrayList<>();
        modelProvider.getModel().getAllContents().forEachRemaining(list::add);
        return list;
    }

    @Override
    public EObject getByID(URI id, FilterID filterID) {
        return null;
    }
}
