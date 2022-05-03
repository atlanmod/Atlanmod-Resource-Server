package org.atlanmod.karadoc.server;

import org.atlanmod.karadoc.core.ModelLoader;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;


public class KaradocModelLoader implements ModelLoader {

    private final static Logger log = LoggerFactory.getLogger(KaradocModelLoader.class);

    ResourceSet resourceSet;
    Resource metamodel;
    Resource model;


    public KaradocModelLoader(File metamodel, File model) {

        resourceSet = new ResourceSetImpl();
        resourceSet.getResourceFactoryRegistry()
                .getExtensionToFactoryMap()
                .put("ecore", new XMIResourceFactoryImpl());
        resourceSet.getResourceFactoryRegistry()
                .getExtensionToFactoryMap()
                .put("graph", new XMIResourceFactoryImpl());

        try {
            loadMetamodel(metamodel);
            loadModel(model);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

       // logMetamodelInformation(this.metamodel);
       // logModelInformation(this.model);

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


    private void loadMetamodel(File file) throws IOException {

        this.metamodel = this.resourceSet.createResource(URI.createURI(file.getName()));
        if (metamodel == null) {
            throw new RuntimeException("Cannot create the resource for the metamodel");
        }

        FileInputStream inputStream = new FileInputStream(file);
        metamodel.load(inputStream, Collections.emptyMap());
        inputStream.close();
        registerEPackages(resourceSet, metamodel);
    }
    private void loadModel(File file) throws IOException {


        this.model = this.resourceSet.createResource(URI.createURI(file.getName()));
        if (model == null) {
            throw new RuntimeException("Cannot create the resource for the metamodel");
        }

        FileInputStream inputStream = new FileInputStream(file);
        model.load(inputStream, Collections.emptyMap());
        inputStream.close();

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
