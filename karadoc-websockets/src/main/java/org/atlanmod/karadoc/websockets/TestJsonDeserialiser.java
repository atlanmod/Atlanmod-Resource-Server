package org.atlanmod.karadoc.websockets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.atlanmod.karadoc.server.KaradocMockModelProvider;
import org.atlanmod.karadoc.websockets.Command.PingServer;
import org.emfjson.jackson.module.EMFModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJsonDeserialiser {

    private final static ObjectMapper mapper = EMFModule.setupDefaultMapper();

    private final static Logger log = LoggerFactory.getLogger(KaradocMockModelProvider.class);

    public static void main(String[] args) throws JsonProcessingException {
         log.info(mapper.writeValueAsString(new PingServer()));
    }

}
