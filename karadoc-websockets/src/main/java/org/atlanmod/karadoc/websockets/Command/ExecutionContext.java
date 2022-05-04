package org.atlanmod.karadoc.websockets.Command;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.atlanmod.karadoc.server.KaradocServer;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;

public class ExecutionContext {
    private final KaradocServer server;
    private final ObjectMapper mapper;
    private final Collection<WebSocketSession> users;

    public ExecutionContext(KaradocServer server, ObjectMapper mapper, Collection<WebSocketSession> users) {
        this.server = server;
        this.mapper = mapper;
        this.users = users;
    }

    public KaradocServer getServer() {
        return server;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public Collection<WebSocketSession> getUsers() {
        return users;
    }
}
