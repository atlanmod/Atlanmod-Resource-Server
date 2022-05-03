package org.atlanmod.karadoc.websockets.Command;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.eclipse.emf.ecore.resource.ResourceSet;

import java.io.IOException;

public class CommandDeserializer extends JsonDeserializer<ModelCommand> {

    private final ResourceSet context;

    public CommandDeserializer(ResourceSet context) {
        this.context = context;
    }

    @Override
    public ModelCommand deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonMappingException {

        // Get reference to ObjectCodec
        ObjectCodec codec = jp.getCodec();

        // Parse "object" node into Jackson's tree model
        JsonNode node = codec.readTree(jp);

        // Get value of the "type" property
        String type = ((CommandWrapper) jp.getParsingContext().getParent()
                .getCurrentValue()).getType();

        // Check the "type" property and map "object" to the suitable class
        switch (type) {

            case "RunMethod":
                ExecuteMethodCommand executeMethodCommand = codec.treeToValue(node, ExecuteMethodCommand.class);
                executeMethodCommand.setContext(context);
            default:
                throw new JsonMappingException(jp,
                        "Invalid value for the \"type\" property");
        }
    }
}
