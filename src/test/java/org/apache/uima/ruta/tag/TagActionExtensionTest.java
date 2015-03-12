package org.apache.uima.ruta.tag;

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
import org.junit.Ignore;
import org.junit.Test;

public class TagActionExtensionTest {

    static Map<String, Object> parameters = new HashMap<>();
    static {
        parameters.put("additionalExtensions",
                new String[] { TagActionExtension.class.getName() });
    }

    @Test
    public void testTxt() throws Exception {

        JCas jCas = JCasFactory.createJCas();
        jCas.setDocumentText("The red fox jumps over the blue fence");

        String script = ""
                + "PACKAGE org.apache.uima.ruta.type;\n" //
                + "DECLARE RutaColoring;\n"
                + "Document{->TAG(\"colors.txt\", RutaColoring)};";

        Ruta.apply(jCas.getCas(), script, parameters);

        Collection<RutaColoring> colors = JCasUtil.select(jCas,
                RutaColoring.class);
        assertEquals(2, colors.size());
        assertEquals(2, select(jCas, "RutaColoring").size());
    }

    @Test
    @Ignore
    public void testTxtRemote() throws Exception {

        JCas jCas = JCasFactory.createJCas();
        jCas.setDocumentText("The red fox jumps over the blue fence");

        String countries = "https://rawgit.com/sherlok/sherlok/7ce0355dcbd06e18c9db20b2c4cd6c1e6b7f195d/config/resources/countries.txt";

        String script = ""
                + "PACKAGE org.apache.uima.ruta.type.tag;\n" //
                + "DECLARE Country;\n" + "Document{->TAG(\"" + countries
                + "\", Country)};";

        Ruta.apply(jCas.getCas(), script, parameters);

        Collection<RutaColoring> colors = JCasUtil.select(jCas,
                RutaColoring.class);
        assertEquals(2, colors.size());
        assertEquals(2, select(jCas, "RutaColoring").size());
    }

    @Test
    public void testCsv() throws Exception {

        JCas jCas = JCasFactory.createJCas();
        jCas.setDocumentText("The red fox jumps over the blue fence");

        String script = ""
                + "PACKAGE org.apache.uima.ruta.type.tag;\n" //
                + "DECLARE Annotation Animal(STRING color, STRING species);\n"
                + "Document{->TAG(\"animals.csv\", Animal, \"color\")};";

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
                + "Document{->TAG(\"hbp_neurotransmitter_ontology.obo\", Neurotransmitter, \"ontologyId\")};";

        Ruta.apply(jCas.getCas(), script, parameters);

        Collection<TOP> nt = select(jCas, "Neurotransmitter");
        assertEquals(2, nt.size());
        // for (TOP n : nt) { System.out.println(n); }
        TOP serotonine = nt.iterator().next();
        assertEquals("HBP_NEUROTRANSMITTER:0000001",
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
