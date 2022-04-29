package org.atlanmod.karadoc.server;

import org.atlanmod.karadoc.core.*;

import java.util.List;

public class KaradocServer implements ResourceServer {
    @Override
    public List<SavedResource> getAvailableResourced() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SavedResource getByID(RessourceID id, FilterID filterID) {
        return null;
    }
}
