package com.github.ingarabr.immutable;

import com.github.ingarabr.immutable.test.AllCompileErrors;
import com.github.ingarabr.immutable.test.DoesNotHaveFinalFieldCompileError;
import com.github.ingarabr.immutable.test.DoesNotHavePrivateFieldCompileError;
import com.github.ingarabr.immutable.test.WithSetterMethodCompileError;
import com.github.ingarabr.immutable.test.DoesNotHaveFinalFieldCompiles;
import com.github.ingarabr.immutable.test.DoesNotHavePrivateFieldCompiles;
import com.github.ingarabr.immutable.test.WithSetterMethodCompiles;
import org.junit.Test;

import static com.github.ingarabr.immutable.AptTestHelper.shouldCompile;
import static com.github.ingarabr.immutable.AptTestHelper.shouldGiveCompileError;
import static java.lang.String.format;

public class ImmutableAnnotationCompressorTest {

    @Test
    public void shouldShowMoreThanOneCompileError() throws Exception {
        shouldGiveCompileError(AllCompileErrors.class)
                .and().withErrorContaining(format(Messages.MSG_FIELD_FINAL, "tada"))
                .and().withErrorContaining(format(Messages.MSG_FIELD_PRIVATE, "tada"))
                .and().withErrorContaining(format(Messages.MSG_METHOD_SETTER, "setTada"));
    }

    @Test
    public void shouldFailToCompileWhenFinalFieldCheckIsEnabled() throws Exception {
        shouldGiveCompileError(DoesNotHaveFinalFieldCompileError.class)
                .and().withErrorContaining(format(Messages.MSG_FIELD_FINAL, "i"));
    }

    @Test
    public void shouldCompileWhenFinalFieldCheckIsDisabled() throws Exception {
        shouldCompile(DoesNotHaveFinalFieldCompiles.class);
    }

    @Test
    public void shouldFailToCompileWhenPrivateFieldIsEnabled() throws Exception {
        shouldGiveCompileError(DoesNotHavePrivateFieldCompileError.class)
                .and().withErrorContaining(format(Messages.MSG_FIELD_PRIVATE, "i"));
    }

    @Test
    public void shouldCompileWhenPrivateFieldIsDisabled() throws Exception {
        shouldCompile(DoesNotHavePrivateFieldCompiles.class);
    }

    @Test
    public void shouldFailToCompileWhenSetterCheckIsEnabled() throws Exception {
        shouldGiveCompileError(WithSetterMethodCompileError.class)
                .and().withErrorContaining(format(Messages.MSG_METHOD_SETTER, "setI"));
    }

    @Test
    public void shouldCompileWhenSetterMethodCheckIsDisabled() throws Exception {
        shouldCompile(WithSetterMethodCompiles.class);
    }

}