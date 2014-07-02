package com.github.ingarabr.immutable.test;

import com.github.ingarabr.immutable.Immutable;

@Immutable(privateFields = false)
public class DoesNotHavePrivateFieldCompiles {

    final int i = 1;

}
