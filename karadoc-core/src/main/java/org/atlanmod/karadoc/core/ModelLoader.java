package org.atlanmod.karadoc.core;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import java.io.InputStream;

public interface ModelLoader {
    ResourceSet getResourceSet();


    Resource getMetamodel();
    Resource getModel();
}
