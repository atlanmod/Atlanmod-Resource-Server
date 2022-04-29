package org.atlanmod.karadoc.core.Filter;

import org.eclipse.emf.ecore.EObject;

public abstract class Filter {
    private final FilterID id;

    protected Filter(FilterID id) {
        this.id = id;
    }

    public abstract boolean predicate(EObject resource);

}
