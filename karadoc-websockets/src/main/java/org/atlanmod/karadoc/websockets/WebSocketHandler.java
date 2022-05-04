package org.atlanmod.karadoc.websockets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.atlanmod.karadoc.server.KaradocModelLoader;
import org.atlanmod.karadoc.server.KaradocServer;
import org.atlanmod.karadoc.websockets.Command.ExecutionContext;
import org.atlanmod.karadoc.websockets.Command.ModelCommand;
import org.emfjson.jackson.module.EMFModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collection;
import java.util.HashSet;

@Controller
public class WebSocketHandler extends TextWebSocketHandler {

    /**
     * Internal server API
     */
    private static final KaradocServer karadocServer = new KaradocServer();
    private final static Logger log = LoggerFactory.getLogger(KaradocModelLoader.class);
    /**
     * Mapper used for serializing and deserializing EMF objects
     */
    private final ObjectMapper mapper = EMFModule.setupDefaultMapper();
    private final Collection<WebSocketSession> users = new HashSet<>();

    private final ExecutionContext executionContext = new ExecutionContext(karadocServer, mapper, users);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info("{} Connected", session.getId());

        users.add(session);

        //synchronise current model
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

        try {
            ModelCommand command = mapper.readValue(message.getPayload(), ModelCommand.class);
            command.execute(executionContext, session);

        }catch (JsonProcessingException processingException){
            log.error(processingException.getMessage());
        }
    }
}
