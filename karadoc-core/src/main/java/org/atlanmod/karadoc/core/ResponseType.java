package org.atlanmod.karadoc.core;

import com.fasterxml.jackson.annotation.JsonValue;


public enum ResponseType {


    SUCCESS("success"),
    URINOTRESOLVEDERROR("URI not found"),
    INVALIDJSONERROR("Cannot parse JSON"),
    FILEACCESSERROR("Could not save file. See server log"),
    MODELALREADYEXIST("There is already a model with this uri"),
    PLACEHOLDER("place holder for work in progress type");

    private String name;

    ResponseType(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }


}
