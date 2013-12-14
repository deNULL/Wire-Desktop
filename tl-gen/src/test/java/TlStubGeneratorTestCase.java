import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import ru.denull.wire.tlgen.TlStubGenerator;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class TlStubGeneratorTestCase {

    @Test
    @Ignore
    public void test() throws Exception {

        String typeTemplateStr = IOUtils.toString(this.getClass().getResourceAsStream("tmpl/TypeTemplate.java"));
        String constrTemplateStr = IOUtils.toString(this.getClass().getResourceAsStream("tmpl/ConstructorTemplate.java"));
        List<String> protocol = IOUtils.readLines(this.getClass().getResourceAsStream("protocol/types.txt"));


        TlStubGenerator generator =
                TlStubGenerator.createInstance(typeTemplateStr,
                        constrTemplateStr,
                        protocol,
                        "src/test/java");

        generator.generate();
    }
}
