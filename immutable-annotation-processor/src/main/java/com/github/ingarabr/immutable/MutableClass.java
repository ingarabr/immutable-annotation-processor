package com.github.ingarabr.immutable;

import java.util.Set;

public class MutableClass {

    private final String className;
    private final Set<String> errors;

    public MutableClass(String className, Set<String> errors) {
        this.className = className;
        this.errors = errors;
    }

    public String getClassName() {
        return className;
    }

    public Set<String> getErrors() {
        return errors;
    }

}
