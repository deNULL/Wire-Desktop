package ru.denull.wire.tlgen.model;

import java.util.ArrayList;

public class TLConstructor {
    public String id;
    public String name;
    public String className;
    public String fullName;
    public String namespace;
    public ArrayList<TLField> fields;
    public TLType type;
    public boolean isFunction;

    public TLConstructor(String id, String name, String namespace, TLType type, boolean isFunction, String className) {
        this.id = id;
        this.name = name;
        this.className = className.replace(";", "");
        this.fullName = className;
        this.namespace = namespace;
        this.fields = new ArrayList<TLField>();
        this.type = type;
        this.isFunction = isFunction;
    }
}
