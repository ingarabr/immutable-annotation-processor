package com.github.ingarabr.immutable.test;

import com.github.ingarabr.immutable.Immutable;

@Immutable(setterMethod = false)
public class WithSetterMethodCompiles {
    private final int i = 1;

    public int getI() {
        return i;
    }

    public void setI(int i) {
//        this.i = i;
    }

}
