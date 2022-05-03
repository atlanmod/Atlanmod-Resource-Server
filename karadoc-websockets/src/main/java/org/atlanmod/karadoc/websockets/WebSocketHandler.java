package org.atlanmod.karadoc.websockets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.atlanmod.karadoc.server.KaradocModelLoader;
import org.atlanmod.karadoc.server.KaradocServer;
import org.eclipse.emf.ecore.EObject;
import org.emfjson.jackson.module.EMFModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Controller
public class WebSocketHandler extends TextWebSocketHandler {

    private static final KaradocServer karadocServer = new KaradocServer();
    private final static Logger log = LoggerFactory.getLogger(KaradocModelLoader.class);

    private  ObjectMapper mapper =  EMFModule.setupDefaultMapper();

    private final Collection<WebSocketSession> users = new HashSet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info("{} Connected", session.getId());

        users.add(session);

        //send current model
        session.sendMessage(new TextMessage(mapper.writeValueAsString(karadocServer.getAvailableResourced())));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        log.info("{} Disconnected", session.getId());
        users.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        log.info("Server received: {}", message);
        if(message.getPayload().equals("getAll")){
            session.sendMessage(new TextMessage(mapper.writeValueAsString(karadocServer.getAvailableResourced())));
        }
    }
}
