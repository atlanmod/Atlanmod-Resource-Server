package org.atlanmod.karadoc.websockets.Command;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.atlanmod.karadoc.core.ResourceService;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;

/**
 * Represent the context when executing a websocket command
 */
public class ExecutionContext {
    /**
     * Link to internal server API
     */
    private final ResourceService server;
    /**
     * Emf-json serializer used for serializing server data
     */
    private final ObjectMapper mapper;
    /**
     * List of all user context. Useful for notifying users of an update
     */
    private final Collection<WebSocketSession> users;

    public ExecutionContext(ResourceService server, ObjectMapper mapper, Collection<WebSocketSession> users) {
        this.server = server;
        this.mapper = mapper;
        this.users = users;
    }

    public ResourceService getServer() {
        return server;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public Collection<WebSocketSession> getUsers() {
        return users;
    }
}
