package com.github.ingarabr.immutable.test;

import com.github.ingarabr.immutable.Immutable;

@SuppressWarnings("UnusedDeclaration")
@Immutable(finalFields = false)
public class SetterArgThatDoesNotMachFields {

    private String str;

    public void setStr(int i) {
    }

    public void setStr() {
    }

    public void setStr(String s1, String s2) {
    }

}
