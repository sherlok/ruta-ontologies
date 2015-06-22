package org.apache.uima.ruta.tag.obo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import nl.flotsam.xeger.Xeger;

import org.junit.Test;

public class RoboExpanderTest {

    @Test
    public void test() {

        // String regex = "[ab]{4,6}c";
        String regex = "(regular|tonic)(ly)?";
        Xeger generator = new Xeger(regex);
        String result = generator.generate();
        assertTrue(result.matches(regex));

        // bruteforce
        Set<String> generated = new HashSet<>();
        for (int i = 0; i < 100000; i++) {
            generated.add(generator.generate());
        }
        for (String g : generated) {
            System.out.println(g);
        }
    }

    static final String ELECTROPYHS_ROBO = "src/test/resources/hbp_electrophysiology_ontology.robo";

    @Test
    public void testElectrophys() throws Exception {
        RoboExpander.expand(new File(ELECTROPYHS_ROBO), new File(
                "src/test/resources/hbp_electrophysiology_ontology.obo"));
    }

    @Test
    public void testParseElectrophys() throws Exception {

        OBOOntology obo = new OBOOntology().read(new File(ELECTROPYHS_ROBO));
        assertNotNull(obo.getIdsForTerm("regular spiking"));
        assertNull(obo.getIdsForTerm("regularly"));

        OBOOntology robo = RoboExpander.expand(new File(ELECTROPYHS_ROBO));
        assertNotNull(robo.getIdsForTerm("regular spiking"));
        assertNotNull(robo.getIdsForTerm("regularly"));

    }
}
