package org.atlanmod.karadoc.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.atlanmod.karadoc.core.ResourceService;
import org.eclipse.emf.ecore.resource.Resource;
import org.emfjson.jackson.module.EMFModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public List<Resource> getAllResources() {
        return server.getAll();
    }

}
