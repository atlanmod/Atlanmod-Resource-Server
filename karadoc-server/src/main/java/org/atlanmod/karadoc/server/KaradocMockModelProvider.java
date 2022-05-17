package org.atlanmod.karadoc.server;

import org.atlanmod.karadoc.core.ModelProvider;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
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

    public static final String GRAPH_METAMODEL_ECORE = "Coffee.ecore";
    public static final String MODEL_GRAPH = "SuperBrewer3000.coffee";


    private final ResourceSet resourceSet;
    private final Resource metamodel;


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
            File metamodelFile = new ClassPathResource(GRAPH_METAMODEL_ECORE).getFile();
            File modelFile = new ClassPathResource(MODEL_GRAPH).getFile();

            //load metamodel
            this.metamodel = this.resourceSet.createResource(URI.createURI(metamodelFile.getName()));
            if (this.metamodel == null) {
                throw new RuntimeException("Cannot create the resource for the metamodel");
            }

            FileInputStream inputStream = new FileInputStream(metamodelFile);
            this.metamodel.load(inputStream, Collections.emptyMap());
            inputStream.close();
            registerEPackages(resourceSet, this.metamodel);

            //then load model based on previously loaded metamodel
            Resource model = this.resourceSet.createResource(URI.createURI(modelFile.getName()));
            if (model == null) {
                throw new RuntimeException("Cannot create the resource for the metamodel");
            }

            FileInputStream inputStream1 = new FileInputStream(modelFile);
            model.load(inputStream1, Collections.emptyMap());
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
    public Resource get(URI uri) {
        return resourceSet.getResource(uri,false);
    }

    @Override
    public boolean delete(URI uri) {
        //TODO: Implement file remove method
        return false;
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
