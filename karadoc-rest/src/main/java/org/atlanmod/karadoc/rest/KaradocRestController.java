package org.atlanmod.karadoc.rest;


import org.atlanmod.karadoc.core.Filter;
import org.atlanmod.karadoc.core.FilterID;
import org.atlanmod.karadoc.core.RessourceID;
import org.atlanmod.karadoc.core.SavedResource;
import org.atlanmod.karadoc.server.KaradocServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KaradocRestController {

    private final KaradocServer server = new KaradocServer();

    @GetMapping("/getAvailableResources")
    public List<SavedResource> getAllResssources(){
        return server.getAvailableResourced();
    }

    @GetMapping("/get")
    public SavedResource get(@RequestParam(value = "id") RessourceID id, @RequestParam(value = "filter") FilterID filterID) {
        return server.getByID(id,filterID);
    }

}
