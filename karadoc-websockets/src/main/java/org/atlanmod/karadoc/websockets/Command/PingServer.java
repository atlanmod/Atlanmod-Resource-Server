package org.atlanmod.karadoc.websockets.Command;

import org.atlanmod.karadoc.server.KaradocModelLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

public class PingServer implements ModelCommand{

    private final static Logger log = LoggerFactory.getLogger(KaradocModelLoader.class);

    @Override
    public void execute(ExecutionContext ctx, WebSocketSession session) {
         log.info("ping");
    }

}