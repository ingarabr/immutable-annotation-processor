package com.github.ingarabr.immutable.test;

import com.github.ingarabr.immutable.Immutable;

@Immutable(finalFields = false)
public class DoesNotHaveFinalFieldCompiles {

    private int i;

    public DoesNotHaveFinalFieldCompiles() {
    }

}
