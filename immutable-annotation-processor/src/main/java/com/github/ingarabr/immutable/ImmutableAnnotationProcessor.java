package com.github.ingarabr.immutable;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;


@SupportedAnnotationTypes(value = "com.github.ingarabr.immutable.Immutable")
public class ImmutableAnnotationProcessor extends AbstractProcessor {

    private static final boolean WILL_CLAIM_ANNOTATIONS = true;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        findImmutableAnnotations(roundEnv);
        return WILL_CLAIM_ANNOTATIONS;
    }

    private void findImmutableAnnotations(RoundEnvironment roundEnv) {
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(Immutable.class);
        ImmutableChecker immutableChecker = new ImmutableChecker();
        for (Element element : elementsAnnotatedWith) {
            immutableChecker.check(new AnnotatedElement(element, element.getAnnotation(Immutable.class)));
        }
        if (immutableChecker.hasErrors()) {
            Messager messager = processingEnv.getMessager();
            for (MutableClass mutableClass : immutableChecker.getMutableClasses()) {
                messager.printMessage(Diagnostic.Kind.ERROR, String.format(Messages.HEAD_ERROR_MSG_FORMAT, mutableClass.getClassName()));
                for (String error : mutableClass.getErrors()) {
                    messager.printMessage(Diagnostic.Kind.ERROR, error);
                }
            }
        }
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
