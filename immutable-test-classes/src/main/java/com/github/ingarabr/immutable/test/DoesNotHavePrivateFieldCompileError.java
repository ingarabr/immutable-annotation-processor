package com.github.ingarabr.immutable.test;

import com.github.ingarabr.immutable.Immutable;

@Immutable
public class DoesNotHavePrivateFieldCompileError {

    final int i = 1;

}
