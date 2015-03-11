package org.apache.uima.ruta.tag;

import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.ruta.engine.Ruta;
import org.apache.uima.ruta.type.RutaColoring;
import org.junit.Test;

public class TagActionExtensionTest {

    @Test
    public void testTextfile() throws Exception {

        JCas jCas = JCasFactory.createJCas();
        jCas.setDocumentText("The red fox jumps over the blue fence");

        String script = ""
                + "PACKAGE org.apache.uima.ruta.type;\n" //
                + "DECLARE RutaColoring;\n"
                + "Document{->TAG(RutaColoring, \"colors.txt\")};";
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("additionalExtensions",
                new String[] { TagActionExtension.class.getName() });

        Ruta.apply(jCas.getCas(), script, parameters);

        Collection<RutaColoring> colors = select(jCas, RutaColoring.class);

        assertEquals(2, colors.size());

    }
}
