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
import org.apache.uima.ruta.engine.Ruta;
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
