package org.atlanmod.karadoc.core.filter;

import org.eclipse.emf.ecore.EObject;

public abstract class Filter {
    private final FilterID id;

    protected Filter(FilterID id) {
        this.id = id;
    }

    public FilterID getId() {
        return id;
    }

    public abstract boolean predicate(EObject resource);

}
