package com.github.ingarabr.immutable.test;

import com.github.ingarabr.immutable.Immutable;

@SuppressWarnings("UnusedDeclaration")
@Immutable(privateFields = false)
public class DoesNotHavePrivateFieldCompiles {

    final int i = 1;

}
