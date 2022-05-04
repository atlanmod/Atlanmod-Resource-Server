package org.atlanmod.karadoc.websockets.Command;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.web.socket.WebSocketSession;

@FunctionalInterface
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PingServer.class, name = "Ping"),
        @JsonSubTypes.Type(value = GetAll.class, name = "GetAll") })
public interface ModelCommand {

    void execute(ExecutionContext ctx, WebSocketSession session);


}
