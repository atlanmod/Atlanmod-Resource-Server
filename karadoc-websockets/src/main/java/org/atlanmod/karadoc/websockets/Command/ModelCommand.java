package org.atlanmod.karadoc.websockets.Command;

import org.eclipse.emf.ecore.resource.ResourceSet;

public interface ModelCommand {

    boolean execute();

    void setContext(ResourceSet resourceSet);

}
