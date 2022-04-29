package org.atlanmod.karadoc.rest;


import org.atlanmod.karadoc.core.Filter.FilterID;
import org.atlanmod.karadoc.server.KaradocServer;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KaradocRestController {
    
    private final KaradocServer server = new KaradocServer();

    @GetMapping("v1/getAvailableResources")
    public List<EObject> getAllResources(){
        return server.getAvailableResourced();
    }

    @GetMapping("v1/get")
    public EObject get(@RequestParam(value = "id") URI id, @RequestParam(value = "filter") FilterID filterID) {
        return server.getByID(id,filterID);
    }

}
