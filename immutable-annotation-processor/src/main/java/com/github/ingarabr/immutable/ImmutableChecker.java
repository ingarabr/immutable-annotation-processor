package com.github.ingarabr.immutable;

import javax.lang.model.element.Element;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.github.ingarabr.immutable.Messages.MSG_FIELD_FINAL;
import static com.github.ingarabr.immutable.Messages.MSG_FIELD_PRIVATE;
import static com.github.ingarabr.immutable.Messages.MSG_METHOD_SETTER;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;

public class ImmutableChecker {

    private Set<MutableClass> mutableClasses = new HashSet<MutableClass>();

    public ImmutableChecker() {
    }

    public void check(AnnotatedElement annotatedElement) {
        Immutable annotation = annotatedElement.getAnnotation();
        HashSet<String> errors = new HashSet<String>();
        errors.addAll(checkField(annotatedElement.getFieldElements(), annotation));
        errors.addAll(checkMethod(annotatedElement, annotation));

        if (!errors.isEmpty()) {
            System.out.println();
            mutableClasses.add(new MutableClass(annotatedElement.getElement().toString(), errors));
        }
    }

    private Set<String> checkMethod(AnnotatedElement annotatedElement, Immutable annotation) {
        if (annotation.setterMethod()) {
            HashSet<String> errors = new HashSet<String>();
            Set<String> fieldNames = new HashSet<String>();
            for (Element fieldElement : annotatedElement.getFieldElements()) {
                fieldNames.add(fieldElement.toString());
            }
            for (Element methodElement : annotatedElement.getMethodElements()) {
                String name = methodElement.getSimpleName().toString();
                if (name.startsWith("set")) {
                    String fieldName = name.substring(3, 4).toLowerCase() + name.substring(4);
                    //todo check method arguments
                    if (fieldNames.contains(fieldName)) {
                        errors.add(String.format(MSG_METHOD_SETTER, name));
                    }
                }
            }
            return errors;
        }
        return Collections.emptySet();
    }

    private Set<String> checkField(List<Element> fields, Immutable annotation) {
        HashSet<String> errors = new HashSet<String>();
        for (Element field : fields) {
            if (annotation.privateFields() && !field.getModifiers().contains(PRIVATE)) {
                errors.add(String.format(MSG_FIELD_PRIVATE, field.toString()));
            }
            if (annotation.finalFields() && !field.getModifiers().contains(FINAL)) {
                errors.add(String.format(MSG_FIELD_FINAL, field.toString()));
            }
        }
        return errors;
    }

    public boolean hasErrors() {
        return !mutableClasses.isEmpty();
    }

    public Set<MutableClass> getMutableClasses() {
        return mutableClasses;
    }

}
