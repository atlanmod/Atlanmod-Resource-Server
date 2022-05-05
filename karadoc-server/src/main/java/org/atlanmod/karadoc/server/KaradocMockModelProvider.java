package org.atlanmod.karadoc.server;

import org.atlanmod.karadoc.core.ModelProvider;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
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

        try {

            //load dummy file
            File metamodel = new ClassPathResource("graph_metamodel.ecore").getFile();
            File model = new ClassPathResource("graph_model.graph").getFile();

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


    //Todo remove this debug method and logger
    private static void logMetamodelInformation(Resource metamodelResource) {
        Iterable<EObject> allContents = metamodelResource::getAllContents;
        for (EObject e : allContents) {
            if (e instanceof EClass) {
                EClass eClass = (EClass) e;
                log.info("EClass {}", eClass.getName());
                eClass.getEAllAttributes()
                        .forEach(eAttribute -> log.info("\tAttribute {} (type={})", eAttribute.getName(),
                                eAttribute.getEType().getName()));
                eClass.getEAllReferences()
                        .forEach(eReference -> log.info("\tReference {} (type={})", eReference.getName(),
                                eReference.getEType().getName()));
            }
        }
    }

    //Todo remove this debug method and logger
    private static void logModelInformation(Resource model) {
        Iterable<EObject> allContents = model::getAllContents;
        for (EObject e : allContents) {
            log.info("Instance of {}", e.eClass().getName());
            e.eClass().getEAllAttributes()
                    .forEach(eAttribute -> log.info("\tAttribute '{}' = {}", eAttribute.getName(), e.eGet(eAttribute)));
            /*
             * Here the logs will probably contain some weird serialization of EMF objects, because we are dealing
             * with references from one object to another.
             */
            e.eClass().getEAllReferences()
                    .forEach(eReference -> log.info("\tReference '{}' = {}", eReference.getName(), e.eGet(eReference)));
        }
    }

}
