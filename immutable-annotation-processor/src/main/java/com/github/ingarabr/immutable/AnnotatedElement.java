package com.github.ingarabr.immutable;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import java.util.ArrayList;
import java.util.List;

public class AnnotatedElement {

    private final Element element;
    private final Immutable annotation;
    private final List<Element> fieldElements = new ArrayList<Element>();
    private final List<Element> methodElements = new ArrayList<Element>();

    public AnnotatedElement(Element element, Immutable annotation) {
        if (!element.getKind().isClass()) {
            throw new RuntimeException(element.toString() + " isn't a class");
        }
        this.element = element;
        this.annotation = annotation;
        for (Element enclosedElement : element.getEnclosedElements()) {
            ElementKind kind = enclosedElement.getKind();
            if (kind.isField()) {
                fieldElements.add(enclosedElement);
            } else if (kind.equals(ElementKind.METHOD)) {
                methodElements.add(enclosedElement);
            }
        }
    }

    public Element getElement() {
        return element;
    }

    public Immutable getAnnotation() {
        return annotation;
    }

    public List<Element> getFieldElements() {
        return fieldElements;
    }

    public List<Element> getMethodElements() {
        return methodElements;
    }

}
