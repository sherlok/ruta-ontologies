package org.apache.uima.ruta.tag.obo;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

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

}
