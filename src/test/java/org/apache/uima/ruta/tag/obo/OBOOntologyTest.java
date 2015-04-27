package org.apache.uima.ruta.tag.obo;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.URL;
import java.util.Map.Entry;

import org.junit.Test;

public class OBOOntologyTest {

    @Test
    public void test() throws Exception {

        InputStream is = OBOOntologyTest.class
                .getResourceAsStream("/hbp_neurotransmitter_ontology.obo");
        OBOOntology onto = new OBOOntology().read(is);
        /*-
        for (Entry<String, OntologyTerm> t : onto.getTerms().entrySet()) {
            System.out.println(t.getValue());
        }
         */
        assertEquals(4, onto.getTerms().size());
    }

    @Test
    public void testMethods() throws Exception {

        InputStream is = new URL(
                "https://rawgit.com/tgbugs/methodsOntology/master/ns_methods.obo")
                .openStream();
        OBOOntology onto = new OBOOntology().read(is);

        for (Entry<String, OntologyTerm> t : onto.getTerms().entrySet()) {
            System.out.println(t.getValue());
        }
        assertTrue(onto.getTerms().size() > 200);
    }
}
