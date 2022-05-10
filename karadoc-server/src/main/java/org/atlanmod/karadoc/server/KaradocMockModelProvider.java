package org.atlanmod.karadoc.server;

import org.atlanmod.karadoc.core.ModelProvider;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;


/**
 * Mock model loader used fur testing purpose
 */
@Component("ModelProvider")
public class KaradocMockModelProvider implements ModelProvider {

    private final static Logger log = LoggerFactory.getLogger(KaradocMockModelProvider.class);
    public static final String GRAPH_METAMODEL_ECORE = "Coffee.ecore";
    public static final String MODEL_GRAPH = "SuperBrewer3000.coffee";


    private final ResourceSet resourceSet;
    private final Resource metamodel;
    Resource model;


    public KaradocMockModelProvider() {

        resourceSet = new ResourceSetImpl();
        resourceSet.getResourceFactoryRegistry()
                .getExtensionToFactoryMap()
                .put("ecore", new XMIResourceFactoryImpl());
        resourceSet.getResourceFactoryRegistry()
                .getExtensionToFactoryMap()
                .put("graph", new XMIResourceFactoryImpl());
        resourceSet.getResourceFactoryRegistry()
                .getExtensionToFactoryMap()
                .put("coffee", new XMIResourceFactoryImpl());

        try {

            //load dummy file
            File metamodel = new ClassPathResource(GRAPH_METAMODEL_ECORE).getFile();
            File model = new ClassPathResource(MODEL_GRAPH).getFile();

            //load metamodel
            this.metamodel = this.resourceSet.createResource(URI.createURI(metamodel.getName()));
            if (this.metamodel == null) {
                throw new RuntimeException("Cannot create the resource for the metamodel");
            }

            FileInputStream inputStream = new FileInputStream(metamodel);
            this.metamodel.load(inputStream, Collections.emptyMap());
            inputStream.close();
            registerEPackages(resourceSet, this.metamodel);

            //then load model based on previously loaded metamodel
            this.model = this.resourceSet.createResource(URI.createURI(model.getName()));
            if (this.model == null) {
                throw new RuntimeException("Cannot create the resource for the metamodel");
            }

            FileInputStream inputStream1 = new FileInputStream(model);
            this.model.load(inputStream1, Collections.emptyMap());
            inputStream1.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ResourceSet getResourceSet() {
        return resourceSet;
    }

    @Override
    public Resource getMetamodel() {
        return metamodel;
    }

    @Override
    public Resource getModel() {
        return model;
    }


    private static void registerEPackages(ResourceSet rSet, Resource metamodel) {
        Iterable<EObject> allContents = metamodel::getAllContents;
        for (EObject e : allContents) {
            if (e instanceof EPackage) {
                EPackage ePackage = (EPackage) e;
                rSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
            }
        }
    }
}
