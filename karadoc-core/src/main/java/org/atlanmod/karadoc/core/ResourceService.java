package org.atlanmod.karadoc.core;

import org.atlanmod.karadoc.core.Filter.FilterID;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import java.util.List;

public interface ResourceService {

    List<EObject> getAvailableResourced();
    EObject getByID(URI id, FilterID filterUri);
}
