package org.atlanmod.karadoc.websockets.command;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.atlanmod.karadoc.core.Response;

@FunctionalInterface
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PingServer.class, name = "Ping"),
        @JsonSubTypes.Type(value = GetAll.class, name = "GetAll"),
        @JsonSubTypes.Type(value = GetResource.class, name = "GetResource") })
public interface ModelCommand {

    Response<?> execute(ExecutionContext ctx);


}
