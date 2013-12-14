package ru.denull.wire.tlgen;

import error.FatalError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.denull.wire.tlgen.model.TLConstructor;
import ru.denull.wire.tlgen.model.TLField;
import ru.denull.wire.tlgen.model.TLType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;


public class TlStubGenerator {

    private HashMap<String, TLType> types = new HashMap<String, TLType>();
    private HashMap<String, TLConstructor> constrs = new HashMap<String, TLConstructor>();

    private final Logger LOG = LoggerFactory.getLogger(TlStubGenerator.class);

    private String typeTemplate;
    private String constructorTemplate;
    private List<String> protocolLines;
    private String outputDirPath;

    private TlStubGenerator(String typeTemplate,
                            String constructorTemplate,
                            List<String> protocolLines,
                            String outputDirPath) {
        this.typeTemplate = typeTemplate;
        this.constructorTemplate = constructorTemplate;
        this.protocolLines = protocolLines;
        this.outputDirPath = outputDirPath;
    }

    public static TlStubGenerator createInstance(String typeTemplate,
                                                 String constructorTemplate,
                                                 List<String> protocolLines,
                                                 String outputDirPath) {
        TlStubGenerator generator = new TlStubGenerator(typeTemplate,
                constructorTemplate,
                protocolLines,
                outputDirPath);

        generator.checkInputParams();

        return generator;
    }


    private void checkInputParams() {

        LOG.debug("check input params");

        if (typeTemplate == null || typeTemplate.length() == 0)
            throw new FatalError("typeTemplate parameter is empty");

        if (constructorTemplate == null || constructorTemplate.length() == 0)
            throw new FatalError("constructorTemplate parameter is empty");

        if (protocolLines == null || protocolLines.size() == 0)
            throw new FatalError("protocolLines parameter is empty");

        if (outputDirPath == null || outputDirPath.length() == 0)
            throw new FatalError("outputDirPath parameter is empty");

        boolean markered = false;
        for (String line : protocolLines) {
            if (line.contains(GeneratorConstants.FUNCTIONS_MARKER)) {
                markered = true;
                break;
            }
        }

        if (!markered)
            throw new FatalError("protocol doesn't contains '" + GeneratorConstants.FUNCTIONS_MARKER + "'. " +
                    "Please place this marker before the functions definitions.");

        File outputDir = new File(outputDirPath);

        if (!outputDir.exists()) {
            if (!outputDir.mkdirs())
                throw new FatalError("output dir '" + outputDir + "' doesn't exists");
        }


        if (!outputDir.isDirectory())
            throw new FatalError("output dir '" + outputDir + "' isn't a directory");

        if (!outputDir.canWrite())
            throw new FatalError("output dir '" + outputDir + "' isn't writable");

        LOG.debug("input params are fine");
    }

    public void generate() {

        LOG.info("start Tl Stub Generator");

        parseProtocol();

        for (TLType type : types.values()) {
            generateTLTypeStubFile(type);
        }

        for (TLConstructor constr : constrs.values()) {
            generateTLConstructorStubFile(constr);
        }

    }

    private void parseProtocol() {
        boolean functions = false;
        for (String line : protocolLines) {

            if (line.trim().length() == 0) continue;
            if (line.contains(GeneratorConstants.FUNCTIONS_MARKER)) {
                functions = true;
                continue;
            }
            if (line.endsWith(";")) line = line.substring(0, line.length() - 1).trim();

            String[] parts = line.split("=");

            // TYPE
            String type_name = parts[1].trim();
            String type_namespace = "";
            if (type_name.indexOf(".") > 0) {
                String[] tmp = type_name.split("\\.");
                type_namespace = tmp[0];
                type_name = tmp[1];

                (new File(outputDirPath + "/" + GeneratorConstants.PACKAGE_NAME.replace(".", "/") + "/" + type_namespace)).mkdirs();
            }
            TLType type = new TLType(type_name, type_namespace, "T" + prettifyClassName(type_name));
            if (!functions) {
                String typekey = type.namespace + "." + type.fullName;
                if (types.containsKey(typekey)) {
                    type = types.get(typekey);
                } else {
                    types.put(typekey, type);
                }
            }

            // CONSTRUCTOR
            StringTokenizer tok = new StringTokenizer(parts[0]);
            parts = tok.nextToken().split("#");
            String constr_name = parts[0];
            String hexCode = parts[1];
            String namespace = "";
            if (constr_name.indexOf(".") > 0) {
                parts = constr_name.split("\\.");
                namespace = parts[0];
                constr_name = parts[1];

                (new File(outputDirPath + "/" + GeneratorConstants.PACKAGE_NAME.replace(".", "/") + "/" + namespace)).mkdirs();
            }
            TLConstructor constr = new TLConstructor(hexCode, constr_name, namespace, type, functions, prettifyClassName(constr_name));
            constrs.put(namespace + "." + constr.fullName, constr);

            if (!hexCode.equals("0")) {
                LOG.info("case 0x" + hexCode + ": return new " + constr.fullName + "(buffer);\n");
            }

            // ARGUMENTS
            while (tok.hasMoreTokens()) {
                parts = tok.nextToken().split(":");
                String arg_name, arg_type, arg_note;
                if (parts.length == 1) {
                    arg_name = "arg" + (constr.fields.size() + 1);
                    arg_type = parts[0];
                } else {
                    arg_name = parts[0];
                    arg_type = parts[1];
                }

                if (arg_type.toLowerCase().equals("vector")) { // plus one arg
                    arg_type += " " + tok.nextToken();
                }

                if (arg_type.endsWith(")")) {
                    arg_note = arg_type.substring(arg_type.indexOf("(") + 1, arg_type.length() - 1);
                    arg_type = arg_type.substring(0, arg_type.indexOf("("));
                } else {
                    arg_note = "";
                }

                String arg_namespace = "";
                if (!(arg_type.toLowerCase().startsWith("vector ") || arg_type.toLowerCase().startsWith("vector<")) && arg_type.indexOf(".") > -1) {
                    parts = arg_type.split("\\.");
                    arg_namespace = parts[0];
                    arg_type = parts[1];
                }
                TLField field = new TLField(arg_name, arg_type, arg_namespace, arg_note, getJavaType(arg_type, arg_namespace, arg_note));
                if (type.fullName.equals(GeneratorConstants.TL_OBJECT_TYPE_FULL_NAME)
                        || type.fullName.equals(GeneratorConstants.TL_FUNCTION_TYPE_FULL_NAME)) {
                    field.consistent = false;
                }
                if (type.fields.containsKey(arg_name)) {
                    TLField afield = type.fields.get(arg_name);
                    if (!afield.javaType.equals(field.javaType)) {
                        afield.consistent = false;
                    }
                    constr.fields.add(afield);
                } else {
                    type.fields.put(arg_name, field);
                    constr.fields.add(field);
                }
            }
        }

    }

    private void generateTLTypeStubFile(TLType tlType) {
        if (tlType.className.equals(GeneratorConstants.TL_OBJECT_TYPE_NAME))
            return;

        try {

            File file = new File(outputDirPath + "/" + GeneratorConstants.PACKAGE_NAME.replace(".", "/")
                    + "/" + (tlType.namespace.length() == 0 ? "" : (tlType.namespace + "/")) + tlType.className.replace(";", "") + ".java");
            file.createNewFile();

            FileWriter typeFile = new FileWriter(file);

            String fields = "";
            for (TLField field : tlType.fields.values()) {
                if (field.consistent) { // Only fields that have same type (or missing) in all constructors are declared in the parent (type) class
                    if (fields.length() > 0) {
                        fields += "\n";
                    }
                    fields += "  public " + field.javaType + " " + field.name + ";";
                }
            }
            String typeDeclaration = typeTemplate
                    .replaceAll("\\/\\*\\*namespace\\*\\*\\/", tlType.namespace.length() == 0 ? "" : ("." + tlType.namespace))
                    .replaceAll("\\/\\*\\*fields\\*\\*\\/", fields)
                    .replaceAll("\\/\\*\\*type\\*\\*\\/", tlType.className.replace(";", ""));

            typeFile.write(typeDeclaration);
            typeFile.close();
        } catch (IOException e) {
            throw new FatalError("IO error.", e);
        }
    }

    private void generateTLConstructorStubFile(TLConstructor constr) {
        try {
            String fields = "";
            String params = "";
            String length = "";
            String string = "";
            String parse_body = "";
            String init_body = "";
            String write_body = "";

            write_body += "    if (boxed) {\n" +
                    "      buffer.putInt(0x" + constr.id + ");\n" +
                    "    }\n";

            int extraSize = 0;
            boolean requireBigInt = false;
            for (TLField field : constr.fields) {
                if (fields.length() > 0 && (constr.isFunction || !field.consistent)) {
                    fields += "\n";
                }
                if (params.length() > 0) {
                    params += ", ";

                    parse_body += "\n";
                    init_body += "\n";
                    write_body += "\n";
                }

                if (field.note.equals("num") || field.note.equals("unum")) requireBigInt = true;
                if (field.type.equals("int128") || field.type.equals("int256")) requireBigInt = true;

                if (constr.isFunction || !field.consistent) {
                    fields += "  public " + field.javaType + " " + field.name + ";";
                }
                params += field.javaType + " " + field.name;
                extraSize += getExtraSize(field.type);
                String sz = getSize(field.type, field.name, field.note);
                if (sz != null) {
                    if (length.length() > 0) length += " + ";
                    length += sz;
                }
                if (string.length() > 0) {
                    string += " + \" " + field.name + ":\" + ";
                } else {
                    string += " " + field.name + ":\" + ";
                }
                // TODO: make proper toString generation
                string += getString(field.type, field.name, field.note);

                parse_body += "    " + getParsing(field.type, field.namespace, field.name, field.note) + ";";
                init_body += "    this." + field.name + " = " + field.name + ";";
                write_body += "    " + getWriting(field.type, field.name, field.note) + ";";
            }
            if (extraSize > 0 && length.length() > 0) {
                length = extraSize + " + " + length;
            } else if (length.length() == 0) {
                length = extraSize + "";
            }

            string = "\"(" + (constr.namespace.length() > 0 ? constr.namespace + "." : "") + constr.name + (string.length() == 0 ? ")\"" : (string + " + \")\""));

            FileWriter constructorFile = new FileWriter(outputDirPath + "/" + GeneratorConstants.PACKAGE_NAME.replace(".", "/") + "/" + (constr.namespace.length() == 0 ? "" : (constr.namespace + "/")) + constr.className + ".java");
            String constructorDeclaration = constructorTemplate
                    .replaceAll("\\/\\*\\*type\\*\\*\\/", constr.isFunction ? GeneratorConstants.TL_FUNCTION_TYPE_FULL_NAME : constr.type.fullName.replace(";", ""))
                    .replaceAll("\\/\\*\\*constructor\\*\\*\\/", constr.className)
                    .replaceAll("\\/\\*\\*namespace\\*\\*\\/", constr.namespace.length() == 0 ? "" : ("." + constr.namespace))
                    .replaceAll("\\/\\*\\*import\\*\\*\\/",
                            (constr.namespace.length() == 0 ? "" : "\nimport " + GeneratorConstants.PACKAGE_NAME + ".TL;")/* + (requireBigInt ? "\nimport java.math.BigInteger;" : "")*/)
                    .replaceAll("\\/\\*\\*fields\\*\\*\\/", fields)
                    .replaceAll("\\/\\*\\*params\\*\\*\\/", params)
                    .replaceAll("\\/\\*\\*length\\*\\*\\/", length)
                    .replaceAll("\\/\\*\\*string\\*\\*\\/", string)
                    .replaceAll("\\/\\*\\*parse_body\\*\\*\\/", parse_body)
                    .replaceAll("\\/\\*\\*init_body\\*\\*\\/", init_body)
                    .replaceAll("\\/\\*\\*write_body\\*\\*\\/", write_body);

            constructorFile.write(constructorDeclaration);
            constructorFile.close();
        } catch (IOException e) {
            throw new FatalError("IO error.", e);
        }
    }


    private String prettifyClassName(String name) {
        if (name.equals("Object") || name.equals("!X") || name.equals("X")) return "LObject";

        String[] parts = name.split("_");
        String res = "";
        for (int i = 0; i < parts.length; i++) {
            res += parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1);
        }
        return res;
    }

    private String getJavaType(String tlType, String namespace, String note) {
        if (tlType.toLowerCase().startsWith("vector ") || tlType.toLowerCase().startsWith("vector<")) {
            String subtype = tlType.replaceAll("<", " ").replaceAll(">", "").split(" ")[1].trim();
            String sub_namespace = "";
            if (subtype.indexOf(".") > -1) {
                String[] parts = subtype.split("\\.");
                sub_namespace = parts[0];
                subtype = parts[1];
            }
            return getJavaType(subtype, sub_namespace, note) + "[]";
        }

        if (tlType.equals("int") || tlType.equals("long") || tlType.equals("double")) {
            return tlType;
        } else if (tlType.equals("int128") || tlType.equals("int256") || tlType.equals("bytes")) {
            return "byte[]";
        } else if (tlType.equals("string")) {
            if (note.equals("num") || note.equals("unum")) {
                return "java.math.BigInteger";
            } else {
                return "String";
            }
        } else if (tlType.equals("Bool")) {
            return "boolean";
        } else {
            return GeneratorConstants.PACKAGE_NAME + "." + (namespace.length() > 0 ? namespace + "." : "") + "T" + prettifyClassName(tlType);
        }
    }

    private int getExtraSize(String tlType) {
        if (tlType.startsWith("Vector ") || tlType.startsWith("Vector<")) {
            return 8;
        } else if (tlType.startsWith("vector ") || tlType.startsWith("vector<")) {
            return 4;
        }

        if (tlType.equals("int")) {
            return 4;
        } else if (tlType.equals("long") || tlType.equals("double")) {
            return 8;
        } else if (tlType.equals("int128")) {
            return 16;
        } else if (tlType.equals("int256")) {
            return 32;
        } else if (tlType.equals("string") || tlType.equals("bytes")) {
            return 0;
        } else {
            return 4;
        }
    }

    private String stringBin(String note) {
        if (note.equals("num") || note.equals("unum")) {
            return ".toByteArray()";
        }

        return ".getBytes(\"UTF8\")";
    }

    private String getSize(String tlType, String var, String note) {
        if (tlType.toLowerCase().startsWith("vector ") || tlType.toLowerCase().startsWith("vector<")) {
            String subtype = tlType.toLowerCase().replaceAll("<", " ").replaceAll(">", "").split(" ")[1].trim();
            if (subtype.equals("int")) {
                return var + ".length * 4";
            } else if (subtype.equals("long") || subtype.equals("double")) {
                return var + ".length * 8";
            } else if (subtype.equals("int128")) {
                return var + ".length * 16";
            } else if (subtype.equals("int256")) {
                return var + ".length * 8";
            } else if (subtype.equals("string")) {
                return "TL.length(" + var/*+ stringBin(note)*/ + ")";
            } else {
                return "TL.length(" + var + ")";
            }
        }

        if (tlType.equals("int") || tlType.equals("long") || tlType.equals("double") || tlType.equals("int128") || tlType.equals("int256") || tlType.equals("Bool")) {
            return null;
        } else if (tlType.equals("bytes")) {
            return "TL.length(" + var + ")";
        } else if (tlType.equals("string")) {
            return "TL.length(" + var + stringBin(note) + ")";
        } else {
            return var + ".length()";
        }
    }

    private String getParsing(String tlType, String namespace, String var, String note) {
        if (tlType.toLowerCase().startsWith("vector ") || tlType.toLowerCase().startsWith("vector<")) {
            String subtype = tlType.replaceAll("<", " ").replaceAll(">", "").split(" ")[1].trim();
            String sub_namespace = "";
            if (subtype.indexOf(".") > -1) {
                String[] parts = subtype.split("\\.");
                sub_namespace = parts[0];
                subtype = parts[1];
            }
            if (subtype.equals("int") || subtype.equals("long") || subtype.equals("double") || subtype.equals("int128") || subtype.equals("int256") || subtype.equals("string") || subtype.equals("bytes")) {
                return var + " = TL.readVector" + subtype.substring(0, 1).toUpperCase() + subtype.substring(1) + "(buffer, " + (tlType.startsWith("Vector") ? "true" : "false") + ")";
            } else {
                if (tlType.equals("vector transport_message")) {
                    return var + " = TL.readVectorMessage(buffer, false);";
                }
                return var + " = TL.readVector(buffer, " + (tlType.startsWith("Vector") ? "true" : "false") + ", new " + getJavaType(subtype, sub_namespace, note) + "[0])";
            }
        }

        if (tlType.equals("int")) {
            return var + " = buffer.getInt()";
        } else if (tlType.equals("long")) {
            return var + " = buffer.getLong()";
        } else if (tlType.equals("double")) {
            return var + " = buffer.getDouble()";
        } else if (tlType.equals("int128")) {
            return var + " = TL.readInt128(buffer)";
        } else if (tlType.equals("int256")) {
            return var + " = TL.readInt256(buffer)";
        } else if (tlType.equals("bytes")) {
            return var + " = TL.readString(buffer)";
        } else if (tlType.equals("string")) {
            if (note.equals("num") || note.equals("unum")) {
                return var + " = new java.math.BigInteger(" + (note.equals("unum") ? "1, " : "") + "TL.readString(buffer))";
            } else {
                return var + " = new String(TL.readString(buffer), \"UTF8\")";
            }
        } else if (tlType.equals("Bool")) {
            return var + " = (buffer.getInt() == 0x997275b5)";
        } else if (tlType.substring(0, 1).toUpperCase().equals(tlType.substring(0, 1))) {
            return var + " = (" + getJavaType(tlType, namespace, note) + ") TL.read(buffer)";
        } else {
            return var + " = new " + getJavaType(tlType, namespace, note) + "(buffer)";
        }
    }

    private String getWriting(String tlType, String var, String note) {
        if (tlType.toLowerCase().startsWith("vector ") || tlType.toLowerCase().startsWith("vector<")) {
            String subtype = tlType.replaceAll("<", " ").replaceAll(">", "").split(" ")[1].trim();
            String sub_namespace = "";
            if (subtype.indexOf(".") > -1) {
                String[] parts = subtype.split("\\.");
                sub_namespace = parts[0];
                subtype = parts[1];
            }
            return "TL.writeVector(buffer, " + var + ", " + (tlType.startsWith("Vector") ? "true" : "false") + ", " + (subtype.substring(0, 1).toUpperCase().equals(subtype.substring(0, 1)) ? "true" : "false") + ")";
        }

        if (tlType.equals("int")) {
            return "buffer.putInt(" + var + ")";
        } else if (tlType.equals("long")) {
            return "buffer.putLong(" + var + ")";
        } else if (tlType.equals("double")) {
            return "buffer.putDouble(" + var + ")";
        } else if (tlType.equals("int128") || tlType.equals("int256")) {
            return "buffer.put(" + var + ")";
        } else if (tlType.toLowerCase().equals("bytes")) {
            return "TL.writeString(buffer, " + var + ", " + (tlType.substring(0, 1).toUpperCase().equals(tlType.substring(0, 1)) ? "true" : "false") + ")";
        } else if (tlType.toLowerCase().equals("string")) {
            return "TL.writeString(buffer, " + var + stringBin(note) + ", " + (tlType.substring(0, 1).toUpperCase().equals(tlType.substring(0, 1)) ? "true" : "false") + ")";
        } else if (tlType.equals("Bool")) {
            return "buffer.putInt(" + var + " ? 0x997275b5 : 0xbc799737)";
        } else {
            return var + ".writeTo(buffer, " + (tlType.substring(0, 1).toUpperCase().equals(tlType.substring(0, 1)) ? "true" : "false") + ")";
        }
    }

    private String getString(String tlType, String var, String note) {
        if (tlType.toLowerCase().startsWith("vector ") || tlType.toLowerCase().startsWith("vector<")) {
            return "TL.toString(" + var + ")";
        }

        if (tlType.equals("long")) {
            return "String.format(\"0x%016x\", " + var + ")";
        } else if (tlType.equals("int128") || tlType.equals("int256")) {
            return "new java.math.BigInteger(" + var + ")";
        } else if (tlType.toLowerCase().equals("bytes")) {
            return "TL.toString(" + var + ")";
        } else if (tlType.equals("Bool")) {
            return "(" + var + " ? \"true\" : \"false\")";
        } else if (tlType.toLowerCase().equals("string")) {
            return var;
        } else {
            return var;
        }
    }


}
