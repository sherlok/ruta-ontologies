package org.apache.uima.ruta.ontologies;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.ruta.engine.Ruta;
import org.apache.uima.ruta.type.DebugScriptApply;
import org.junit.Test;

public class OntoActionExtensionTest {

    static Map<String, Object> parameters = new HashMap<>();
    static {
        parameters.put("additionalExtensions",
                new String[] { OntoActionExtension.class.getName() });
    }

    @Test
    public void testObo() throws Exception {

        for (String extension : new String[] { "obo", "robo" }) {

            JCas jCas = JCasFactory.createJCas();
            jCas.setDocumentText("A serotoninergic and glutamate neuron");

            String script = ""
                    + "PACKAGE org.apache.uima.ruta.type.tag;\n" //
                    + "DECLARE Neurotransmitter(STRING ontologyId);\n"
                    + "ONTO(\"hbp_neurotransmitter_ontology." + extension
                    + "\", Neurotransmitter, \"ontologyId\");";

            Ruta.apply(jCas.getCas(), script, parameters);

            Collection<TOP> nt = select(jCas, "Neurotransmitter");
            assertEquals(2, nt.size());
            // for (TOP n : nt) { System.out.println(n); }
            TOP serotonine = nt.iterator().next();
            assertEquals("HBP_NEUROTRANSMITTER:0000001",
                    serotonine.getFeatureValueAsString(serotonine.getType()
                            .getFeatureByBaseName("ontologyId")));
        }
    }

    @Test
    public void testOboLayers() throws Exception {

        for (String layer : new String[] { "layer 7", "layer 4", "layer 3",
                "L4", "LayerIV", "layerIV", "layer 1/2", "layer 2/3",
                "layer 3/4", "layer 5-6" }) {
            System.out.println("\nTESTING[" + layer + "]");
            JCas jCas = JCasFactory.createJCas();
            jCas.setDocumentText(layer);

            String script = ""
                    + "PACKAGE org.apache.uima.ruta.type.tag;\n" //
                    + "RETAINTYPE(SPACE);\n" //
                    + "DECLARE Neurotransmitter(STRING ontologyId);\n"
                    + "ONTO(\"hbp_layer_ontology.robo\", Neurotransmitter, \"ontologyId\");";

            Ruta.apply(jCas.getCas(), script, parameters);

            Collection<TOP> nt = select(jCas, "Neurotransmitter");
            assertEquals("for " + layer, 1, nt.size());
        }
    }

    @Test
    public void testNeedsSynonym() throws Exception {

        for (String layer : new String[] { "layer5", "bla", "L4", "LayerI2",
                "LayerI V" }) {
            System.out.println("\nTESTING[" + layer + "]");
            JCas jCas = JCasFactory.createJCas();
            jCas.setDocumentText(layer);

            String script = ""
                    + "PACKAGE org.apache.uima.ruta.type.tag;\n" //
                    + "RETAINTYPE(SPACE);DECLARE Neurotransmitter(STRING ontologyId);\n"
                    + "ONTO(\"hbp_layer_ontology2.robo\", Neurotransmitter, \"ontologyId\");";

            Ruta.apply(jCas.getCas(), script, parameters);

            Collection<TOP> nt = select(jCas, "Neurotransmitter");
            assertEquals("for " + layer, 1, nt.size());
            Annotation next = (Annotation) nt.iterator().next();
            assertEquals(layer, next.getCoveredText());
        }
    }

    @Test
    public void testOboRegions() throws Exception {

        for (String br : new String[] { "reticular nucleus of the thalamus",
                "spinotectal pathway" }) {
            JCas jCas = JCasFactory.createJCas();
            jCas.setDocumentText(br);

            String script = ""
                    + "PACKAGE org.apache.uima.ruta.type.tag;\n" //
                    + "RETAINTYPE(SPACE);\n" //
                    + "DECLARE Neurotransmitter(STRING ontologyId);\n"
                    + "ONTO(\"hbp_brainregions_aba-syn.obo\", Neurotransmitter, \"ontologyId\");\n"
                    // keep longest
                    + "(Neurotransmitter{-> UNMARK(Neurotransmitter)}){PARTOFNEQ(Neurotransmitter)};";

            Ruta.apply(jCas.getCas(), script, parameters);

            Collection<TOP> nt = select(jCas, "Neurotransmitter");
            for (TOP top : nt) {
                System.err.println(((Annotation) top).getCoveredText());
                System.err.println(top);
            }
            assertEquals("for " + br, 1, nt.size());
            Annotation annot = (Annotation) nt.iterator().next();
            assertEquals(0, annot.getBegin());
            assertEquals(br.length(), annot.getEnd());
        }
    }

    @Test
    public void testMarkfast() throws Exception {

        JCas jCas = JCasFactory.createJCas();
        jCas.setDocumentText("abcd");

        Ruta.apply(jCas.getCas(),
                "MARKFAST(DebugScriptApply, 'testMarkfastLayers.txt', true, 4, false);");
        // note: DebugScriptApply is a dummy annot

        assertEquals(1, JCasUtil.select(jCas, DebugScriptApply.class).size());

    }

    @Test
    public void testOboIgnoreLength() throws Exception {

        for (int ignoreLength : new int[] { 2, 3, 4, 5, 6 }) {

            JCas jCas = JCasFactory.createJCas();
            jCas.setDocumentText("a GaBA neuron");

            String script = ""
                    + "PACKAGE org.apache.uima.ruta.type.tag;\n" //
                    + "DECLARE Neurotransmitter(STRING ontologyId);\n"
                    + "ONTO(\"hbp_neurotransmitter_ontology.obo"
                    + "\", Neurotransmitter, \"ontologyId\", " + ignoreLength
                    + ");";

            Ruta.apply(jCas.getCas(), script, parameters);

            Collection<TOP> nt = select(jCas, "Neurotransmitter");
            System.out.println(nt.size());// +" "+nt.iterator().next()); FIXME

        }
    }

    public static Collection<TOP> select(JCas jCas, String shortName) {
        List<TOP> ret = new ArrayList<>();
        for (TOP t : JCasUtil.selectAll(jCas)) {
            if (t.getType().getShortName().equals(shortName)) {
                ret.add(t);
            }
        }
        return ret;
    }
}
