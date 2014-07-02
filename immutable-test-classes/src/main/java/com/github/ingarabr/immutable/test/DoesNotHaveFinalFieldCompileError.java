package com.github.ingarabr.immutable.test;

import com.github.ingarabr.immutable.Immutable;

@Immutable
public class DoesNotHaveFinalFieldCompileError {

    private int i;

    public DoesNotHaveFinalFieldCompileError() {
    }

}
