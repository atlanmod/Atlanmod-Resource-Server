package org.atlanmod.karadoc.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.atlanmod.karadoc.core.Filter.FilterID;
import org.atlanmod.karadoc.server.KaradocServer;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.emfjson.jackson.module.EMFModule;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KaradocRestController {
    
    private final KaradocServer server = new KaradocServer();
    private final ObjectMapper mapper =  EMFModule.setupDefaultMapper();

    @GetMapping("v1/getAvailableResources")
    public String getAllResources() throws JsonProcessingException {
        return mapper.writeValueAsString( server.getAvailableResourced());
    }

    @GetMapping("v1/get")
    public EObject get(@RequestParam(value = "id") URI id, @RequestParam(value = "filter") FilterID filterID) {
        return server.getByID(id,filterID);
    }

}
