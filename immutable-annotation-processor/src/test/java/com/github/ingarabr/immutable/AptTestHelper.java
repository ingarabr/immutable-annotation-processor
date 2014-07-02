package com.github.ingarabr.immutable;

import com.google.testing.compile.CompileTester;
import com.google.testing.compile.JavaSourcesSubject;

import static com.google.testing.compile.JavaFileObjects.forResource;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static org.truth0.Truth.ASSERT;

public final class AptTestHelper {

    private AptTestHelper() {
    }

    private static JavaSourcesSubject.SingleSourceAdapter assertFrom(Class clazz) {
        String name = clazz.getName().replaceAll("\\.", "/") + ".java";
        System.out.println(name);
        return ASSERT.about(javaSource())
                .that(forResource(name));
    }

    public static void shouldCompile(Class clazz) {
        assertFrom(clazz)
                .processedWith(new ImmutableAnnotationProcessor())
                .compilesWithoutError();
    }

    public static CompileTester.FileClause shouldGiveCompileError(Class clazz) {
        return assertFrom(clazz)
                .processedWith(new ImmutableAnnotationProcessor())
                .failsToCompile()
                .withErrorContaining(String.format(Messages.HEAD_ERROR_MSG_FORMAT,
                        clazz.getName()));
    }

}
