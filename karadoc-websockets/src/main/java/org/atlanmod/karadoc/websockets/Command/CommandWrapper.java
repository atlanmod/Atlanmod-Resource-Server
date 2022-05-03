package org.atlanmod.karadoc.websockets.Command;

import org.eclipse.emf.common.command.Command;

public class CommandWrapper {
    private final String type;
    private final Command command;

    public CommandWrapper(String type, Command command) {
        this.type = type;
        this.command = command;
    }

    public String getType() {
        return type;
    }
    public Command getCommand() {
        return command;
    }

}
