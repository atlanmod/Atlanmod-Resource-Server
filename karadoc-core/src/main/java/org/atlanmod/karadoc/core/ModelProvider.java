package org.atlanmod.karadoc.core;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

public interface ModelProvider {
    ResourceSet getResourceSet();


    Resource getMetamodel();
    Resource getModel();
}
