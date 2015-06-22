package org.apache.uima.ruta.tag.owl;

import java.io.InputStream;

import org.junit.Test;

public class OwlParserTest {

    @Test
    public void test() throws Exception {

        InputStream owl = OwlParserTest.class
                .getResourceAsStream("/NIF-Dysfunction.owl");
        // String x =
        // "http://ontology.neuinfo.org/NIF/Dysfunction/NIF-Dysfunction.owl";
        // String x =
        // "http://repos.frontology.org/dron/raw/master/dron-full.owl";

        new OwlParser().parseAndLoadData(owl); // TODO

    }

}
