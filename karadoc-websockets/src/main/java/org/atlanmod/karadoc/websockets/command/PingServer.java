package org.atlanmod.karadoc.websockets.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingServer implements ModelCommand{

    private static final Logger log = LoggerFactory.getLogger(PingServer.class);

    @Override
    public void execute(ExecutionContext ctx) {
        ctx.getServer().ping();
    }

}
