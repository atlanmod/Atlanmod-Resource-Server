package org.atlanmod.karadoc.websockets.Command;

import org.eclipse.emf.ecore.resource.ResourceSet;

import java.io.Console;

public class ExecuteMethodCommand implements ModelCommand{

    private ResourceSet resourceSet;

    @Override
    public boolean execute() {
         System.out.println(resourceSet.toString());
        return false;
    }

    @Override
    public void setContext(ResourceSet resourceSet) {
        this.resourceSet = resourceSet;
    }
}
