package com.github.ingarabr.immutable.test;

import com.github.ingarabr.immutable.Immutable;

@SuppressWarnings("UnusedDeclaration")
@Immutable(finalFields = false)
public class WithSetterMethodCompileError {
    private int i;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

}
