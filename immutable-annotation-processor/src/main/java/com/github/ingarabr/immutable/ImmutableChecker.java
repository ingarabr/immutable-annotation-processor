package com.github.ingarabr.immutable;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;

import javax.lang.model.element.Element;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
            Map<String, Element> fieldNames = new HashMap<String, Element>();
            for (Element fieldElement : annotatedElement.getFieldElements()) {
                fieldNames.put(fieldElement.toString(), fieldElement);
            }
            for (Element methodElement : annotatedElement.getMethodElements()) {
                String name = methodElement.getSimpleName().toString();
                if (name.startsWith("set") && matchJavaBeanSetter(fieldNames, (Symbol.MethodSymbol) methodElement, name)) {
                    errors.add(String.format(MSG_METHOD_SETTER, name));
                }
            }
            return errors;
        }
        return Collections.emptySet();
    }

    private boolean matchJavaBeanSetter(Map<String, Element> fieldNames, Symbol.MethodSymbol methodElement, String name) {
        String fieldName = name.substring(3, 4).toLowerCase() + name.substring(4);
        if (fieldNames.keySet().contains(fieldName)) {
            com.sun.tools.javac.util.List<Symbol.VarSymbol> methodParameters = methodElement.getParameters();
            if (methodParameters.size() == 1) {
                Type fieldType = ((Symbol.VarSymbol) ((HashMap) fieldNames).get(fieldName)).asType();
                if (methodParameters.get(0).asType().equals(fieldType)) {
                    return true;
                }
            }
        }
        return false;
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
