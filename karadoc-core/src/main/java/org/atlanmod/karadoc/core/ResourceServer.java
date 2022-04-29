package org.atlanmod.karadoc.core;

import java.util.List;

public interface ResourceServer {

    List<SavedResource> getAvailableResourced();
    SavedResource getByID(RessourceID id, FilterID filterUri);
}
