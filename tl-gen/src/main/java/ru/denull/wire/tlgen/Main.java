package ru.denull.wire.tlgen;

import error.FatalError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        if (args.length < 4)
            throw new FatalError("illegal arguments");

        String constrTemplateStr = getResourceAsString(args[0]);
        String typeTemplateStr = getResourceAsString(args[1]);
        List<String> protocol = getResourceAsStringList(args[2]);
        String outputDirPath = args[3];

        TlStubGenerator generator =
                TlStubGenerator.createInstance(typeTemplateStr,
                        constrTemplateStr,
                        protocol,
                        outputDirPath);

        generator.generate();
    }

    private static List<String> getResourceAsStringList(String path) {
        try {
            LOG.info("reading file: " + new File(path).getAbsoluteFile());
            InputStream is = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader buffer = new BufferedReader(isr);

            String line = "";
            List<String> stringList = new LinkedList<String>();
            while ((line = buffer.readLine()) != null) {
                stringList.add(line);
            }
            return stringList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getResourceAsString(String path) {
        try {
            LOG.info("reading file: " + new File(path).getAbsoluteFile());
            InputStream is = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader buffer = new BufferedReader(isr);

            String line = "";
            StringBuffer fileBuffer = new StringBuffer();
            while ((line = buffer.readLine()) != null) {
                fileBuffer.append(line).append("\n");
            }
            return fileBuffer.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
