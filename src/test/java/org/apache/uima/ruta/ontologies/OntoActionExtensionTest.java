package org.apache.uima.ruta.ontologies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.ruta.engine.Ruta;
import org.apache.uima.ruta.type.RutaColoring;
import org.junit.Test;

public class OntoActionExtensionTest {

    static Map<String, Object> parameters = new HashMap<>();
    static {
        parameters.put("additionalExtensions",
                new String[] { OntoActionExtension.class.getName() });
    }

    @Test
    public void testTxt() throws Exception {

        JCas jCas = JCasFactory.createJCas();
        jCas.setDocumentText("The red fox jumps over the blue fence");

        String script = ""
                + "PACKAGE org.apache.uima.ruta.type;\n" //
                + "DECLARE RutaColoring;\n"
                + "Document{->ONTO(\"colors.txt\", RutaColoring)};";

        Ruta.apply(jCas.getCas(), script, parameters);

        Collection<RutaColoring> colors = JCasUtil.select(jCas,
                RutaColoring.class);
        assertEquals("red & blue", 2, colors.size());
        assertEquals("same, but using the convenience method", 2,
                select(jCas, "RutaColoring").size());
    }

    @Test
    public void testCsv() throws Exception {

        JCas jCas = JCasFactory.createJCas();
        jCas.setDocumentText("The red fox jumps over the blue fence");

        String script = ""
                + "PACKAGE org.apache.uima.ruta.type.tag;\n" //
                + "DECLARE Annotation Animal(STRING color, STRING species);\n"
                + "Document{->ONTO(\"animals.csv\", Animal, \"color\")};";

        Ruta.apply(jCas.getCas(), script, parameters);

        Collection<TOP> animals = select(jCas, "Animal");
        assertEquals(1, animals.size());
        TOP fox = animals.iterator().next();
        assertEquals(
                "brown",
                fox.getFeatureValueAsString(fox.getType().getFeatureByBaseName(
                        "color")));
        assertNull(fox.getFeatureValueAsString(fox.getType()
                .getFeatureByBaseName("species")));
    }

    @Test
    public void testObo() throws Exception {

        JCas jCas = JCasFactory.createJCas();
        jCas.setDocumentText("A serotonergic and glutamate neuron");

        String script = ""
                + "PACKAGE org.apache.uima.ruta.type.tag;\n" //
                + "DECLARE Annotation Neurotransmitter(STRING ontologyId);\n"
                + "Document{->ONTO(\"hbp_neurotransmitter_ontology.obo\", Neurotransmitter, \"ontologyId\")};";

        Ruta.apply(jCas.getCas(), script, parameters);

        Collection<TOP> nt = select(jCas, "Neurotransmitter");
        assertEquals(2, nt.size());
        // for (TOP n : nt) { System.out.println(n); }
        TOP serotonine = nt.iterator().next();
        assertEquals("HBP_NEUROTRANSMITTER:0000001",
                serotonine.getFeatureValueAsString(serotonine.getType()
                        .getFeatureByBaseName("ontologyId")));
    }
    @Test
    public void testRobo() throws Exception {
        
        JCas jCas = JCasFactory.createJCas();
        jCas.setDocumentText("A regularly bbb");
        
        String script = ""
                + "PACKAGE org.apache.uima.ruta.type.tag;\n" //
                + "DECLARE Annotation Neurotransmitter(STRING ontologyId);\n"
                + "Document{->ONTO(\"hbp_electrophysiology_ontology.robo\", Neurotransmitter, \"ontologyId\")};";
        
        Ruta.apply(jCas.getCas(), script, parameters);
        
        Collection<TOP> nt = select(jCas, "Neurotransmitter");
        assertEquals(1, nt.size());
        // for (TOP n : nt) { System.out.println(n); }
        TOP serotonine = nt.iterator().next();
        assertEquals("HBP_EPHYS_PHENO:0000001",
                serotonine.getFeatureValueAsString(serotonine.getType()
                        .getFeatureByBaseName("ontologyId")));
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
