package org.atlanmod.karadoc.websockets.command;

import org.atlanmod.karadoc.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingServer implements ModelCommand{

    private static final Logger log = LoggerFactory.getLogger(PingServer.class);

    @Override
    public Response<Boolean> execute(ExecutionContext ctx) {
         return ctx.getServer().ping();
    }

}
