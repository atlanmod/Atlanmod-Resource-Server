package org.atlanmod.karadoc.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.atlanmod.karadoc.core.Filter.FilterID;
import org.atlanmod.karadoc.core.ResourceService;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.emfjson.jackson.module.EMFModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan(basePackages = "org.atlanmod.karadoc")
public class KaradocRestController {


    private final ResourceService server;
    private final ObjectMapper mapper =  EMFModule.setupDefaultMapper();

    @Autowired
    public KaradocRestController(ResourceService server) {
        this.server = server;
    }

    @GetMapping("v1/getAvailableResources")
    public String getAllResources() throws JsonProcessingException {
        return mapper.writeValueAsString( server.getAvailableResourced());
    }

    @GetMapping("v1/get")
    public EObject get(@RequestParam(value = "id") URI id, @RequestParam(value = "filter") FilterID filterID) {
        return server.getByID(id,filterID);
    }

}
