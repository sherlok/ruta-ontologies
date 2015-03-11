package org.apache.uima.ruta.tag.obo;

import java.io.File;
import java.io.IOException;

/**
 * Coverts OBO ontologies into Ruta WORDTABLES
 * 
 * @author richarde
 */
public class Obo2Wordtable {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {

        OBOOntology obo = new OBOOntology();
        String oboFile = "";
        obo.read(new File(oboFile + ".obo"));

    }
}
