package org.atlanmod.karadoc.websockets.command;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@FunctionalInterface
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PingServer.class, name = "Ping"),
        @JsonSubTypes.Type(value = GetAll.class, name = "GetAll"),
        @JsonSubTypes.Type(value = ReplaceCommand.class, name = "Replace")})
public interface ModelCommand {

    void execute(ExecutionContext ctx);


}
