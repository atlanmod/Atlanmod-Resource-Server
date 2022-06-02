package org.atlanmod.karadoc.websockets.command;

import java.beans.ConstructorProperties;

public class ReplaceCommand implements ModelCommand{

    String newContent;

    @ConstructorProperties({"newModel"})
    public ReplaceCommand(String newModel) {
        this.newContent = newModel;
    }

    @Override
    public void execute(ExecutionContext ctx) {
        //Not implemented
    }
}
