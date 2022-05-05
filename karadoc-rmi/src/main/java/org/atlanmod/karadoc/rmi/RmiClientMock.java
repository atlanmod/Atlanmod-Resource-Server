package org.atlanmod.karadoc.rmi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.atlanmod.karadoc.core.ModelProvider;
import org.atlanmod.karadoc.server.KaradocMockModelProvider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emfjson.jackson.databind.EMFContext;
import org.emfjson.jackson.module.EMFModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.List;

public class RmiClientMock {

    private final static Logger log = LoggerFactory.getLogger(RmiController.class);
    private static final ObjectMapper mapper =  EMFModule.setupDefaultMapper();






    public static void main(String[] args) throws IOException, NotBoundException {

            ClassPathResource metamodel = new ClassPathResource("graph_metamodel.ecore");
            ClassPathResource model = new ClassPathResource("graph_model.graph");
            //for reading the model
            final ModelProvider modelProvider = new KaradocMockModelProvider();

            RmiResourceServer access = (RmiResourceServer) Naming.lookup("rmi://localhost:1337/v1");
            //answer = access.getAvailableResourced();

//            List<EObject> objects = mapper.reader()
//                    .withAttribute(EMFContext.Attributes.RESOURCE_SET, modelProvider.getModel())
//                    .withAttribute(EMFContext.Attributes.RESOURCE_URI, "src/main/resources/data.json")
//                    .forType(Resource.class)
//                            .readValue(access.getAvailableResourced());

            log.info("Res" + " " + access.getAvailableResourced());
        }

}
