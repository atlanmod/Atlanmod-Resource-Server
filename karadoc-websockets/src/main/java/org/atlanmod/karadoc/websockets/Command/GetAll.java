package org.atlanmod.karadoc.websockets.Command;

import org.eclipse.emf.ecore.EObject;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

public class GetAll implements ModelCommand{

    @Override
    public void execute(ExecutionContext ctx, WebSocketSession session) {
        try {
            List<EObject> availableResource = ctx.getServer().getAvailableResourced();
            String payload = ctx.getMapper().writeValueAsString(availableResource);
            session.sendMessage(new TextMessage(payload));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}