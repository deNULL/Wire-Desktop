package ru.denull.wire.tlgen.model;

import ru.denull.wire.tlgen.GeneratorConstants;

import java.util.HashMap;

public class TLType {
    public String name;
    public String className;
    public String fullName;
    public String namespace;
    public HashMap<String, TLField> fields;

    public TLType(String name, String namespace, String className) {
        this.name = name;
        this.className = className;
        this.fullName = GeneratorConstants.PACKAGE_NAME + "." + (namespace.length() > 0 ? namespace + "." : "") + className;
        this.namespace = namespace;
        this.fields = new HashMap<String, TLField>();
    }
}
