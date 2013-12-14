package ru.denull.wire.tlgen.model;

public class TLField {
    public String name;
    public String type;
    public String javaType;
    public String namespace;
    public String note;
    public boolean consistent;

    public TLField(String name, String type, String namespace, String note, String javaType) {
        this.name = name;
        this.type = type;
        this.javaType = javaType;
        this.namespace = namespace;
        this.note = note;
        this.consistent = true;
    }


}
