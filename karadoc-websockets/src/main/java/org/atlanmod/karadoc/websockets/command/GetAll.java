package org.atlanmod.karadoc.websockets.command;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class GetAll{

    public void execute(ExecutionContext ctx, WebSocketSession session) {

        try {
            String payload = ctx.getMapper().writeValueAsString(ctx.getServer().getAll());
            session.sendMessage(new TextMessage(payload));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
