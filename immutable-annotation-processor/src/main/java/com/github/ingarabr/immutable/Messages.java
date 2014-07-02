package com.github.ingarabr.immutable;

public class Messages {

    public static final String HEAD_ERROR_MSG_FORMAT = "Class is mutable: %s";

    public static final String PREFIX = "    | ";

    public static final String MSG_FIELD_PRIVATE = PREFIX + "Field [%s] does not have private modifier";
    public static final String MSG_FIELD_FINAL = PREFIX + "Field [%s] does not have final modifier";

    public static final String MSG_METHOD_SETTER = PREFIX + "Setter method [%s] does have a matching field";

}
