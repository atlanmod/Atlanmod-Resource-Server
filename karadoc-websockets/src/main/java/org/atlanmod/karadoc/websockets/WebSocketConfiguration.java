package org.atlanmod.karadoc.websockets;

import org.atlanmod.karadoc.core.ResourceService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Controller
@Configurable
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final WebSocketEndpoint webSocketEndpoint;

    public WebSocketConfiguration(ResourceService server) {
        webSocketEndpoint = new WebSocketEndpoint(server);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(webSocketEndpoint, "websocket/v1");
    }
}
