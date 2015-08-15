package org.apache.uima.ruta.tag.obo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import nl.flotsam.xeger.Xeger;

import org.junit.Ignore;
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

    @Test
    public void testXeger2() {

        String regex = "(non[- ]?)?(spik(ing|e)|firing|bursting|stuttering|chattering|accomm?odating|adapting)";
        for (String g : RoboExpander.expand(regex)) {
            System.out.println(g);
        }
    }

    @Test
    public void testXegerLayer1() {

        String regex = "[Ll]ayer[- ]?[1I]";
        for (String g : RoboExpander.expand(regex)) {
            System.out.println(g);
        }
    }

    @Test
    public void testXegerLayer2() {

        String regex = "layer[ -](II|ii|2)";
        for (String g : RoboExpander.expand(regex)) {
            System.out.println(g);
        }
    }

    @Test
    public void testXegerL1_2() {

        String regex = "[Ll](1[-/]2|I[-/]II)";
        for (String g : RoboExpander.expand(regex)) {
            System.out.println(g);
        }
    }

    @Test
    public void testXegerLayer3a() {

        // TOO MUCH String regex = "[Ll](ayer)?[- ]?(3|iii|III) ?[Aa]";
        String regex = "[Ll]ayer[- ]?(3|iii|III) ?[Aa]";
        for (String g : RoboExpander.expand(regex)) {
            System.out.println(g);
        }
    }

    @Test
    public void testXegerLayer3_4() {

        String regex = "[Ll](ayers?)? (3[-/]4|III[-/]IV)";
        for (String g : RoboExpander.expand(regex)) {
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
    @Ignore
    public void testLayers() throws Exception {
        RoboExpander
                .expand(new File(
                        "/Volumes/HDD2/ren_data/dev_hdd/bluebrain/git2/neuroNER/resources/bluima/neuroner/hbp_layer_ontology.robo"),
                        new File("target/layers.obo"));
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
