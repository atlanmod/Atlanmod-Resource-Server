package org.atlanmod.karadoc.core;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

public interface ModelProvider {
    ResourceSet getResourceSet();

    Resource get(URI uri);

    boolean delete(URI uri);

}
