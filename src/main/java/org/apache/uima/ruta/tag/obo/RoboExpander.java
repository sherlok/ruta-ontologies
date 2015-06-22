package org.apache.uima.ruta.tag.obo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.flotsam.xeger.Xeger;

/**
 * Processes an obo ontology, and expands each regular-expression synonym
 * ('rsynonym' entry), so that e.g.
 * 
 * <pre>
 * rsynonym: "(regular|tonic)(ly)?"
 * </pre>
 * 
 * expands to
 * 
 * <pre>
 * synonym: "tonicly"
 * synonym: "regularly"
 * synonym: "regular"
 * synonym: "tonic"
 * </pre>
 * 
 * The goal of the robo formati is to allow to write more compact ontologies.
 * 
 * @author renaud@apache.org
 */
public class RoboExpander {

    public static OBOOntology expand(File oboFile) throws IOException {
        return expand(new FileInputStream(oboFile));
    }

    /**
     * Expands a .robo inputstream into an OBOOntology
     * 
     * @param is
     *            The .obo file inputstream to read.
     */
    public static OBOOntology expand(InputStream is) throws IOException {
        return expand(new OBOOntology().read(is));
    }

    /** Expands an OBOOntology */
    public static OBOOntology expand(OBOOntology obo) throws IOException {

        for (OntologyTerm term : obo.getTerms().values()) {

            List<Synonym> synonyms = term.getSynonyms();
            List<String> rsynonyms = term.getOtherProperties("rsynonym");
            if (rsynonyms != null) {
                for (String rsynonym : rsynonyms) {
                    // replace trailing quotes, if any
                    rsynonym = rsynonym.replaceAll("^\"|\"$", "");
                    for (String syn : expand(rsynonym)) {
                        synonyms.add(new Synonym(syn, null, null));
                    }
                }
            }
            term.setSynonyms(synonyms);
            obo.addTerm(term);
        }
        return obo;
    }

    static OBOOntology expand(BufferedReader br) throws IOException {

        return null;
    }

    /**
     * Expands a .robo inputstream into an obo outputstream
     * 
     * @param is
     *            The .obo file inputstream to read.
     */
    public static void expand(InputStream is, OutputStream os)
            throws IOException {
        try {
            expand(new BufferedReader(new InputStreamReader(is, "UTF-8")),
                    new PrintStream(os));
        } finally {
            is.close();
            os.close();
        }
    }

    public static void expand(File oboFile, File roboFile) throws IOException {
        expand(new FileInputStream(oboFile), new FileOutputStream(roboFile));
    }

    static void expand(BufferedReader br, PrintStream ps) throws IOException {
        String line = br.readLine();
        while (line != null) {
            if (line.startsWith("rsynonym: \"")) {
                ps.println(line);
                // expand all synonyms

                int end = line.lastIndexOf('"');
                if (end == -1) {
                    end = line.length();
                }
                String rsynonym = line.substring("rsynonym: \"".length(), end)
                        .trim();
                for (String expanded : expand(rsynonym)) {
                    ps.println("synonym: \"" + expanded + "\"");
                }
            } else {
                ps.println(line);
            }
            line = br.readLine();
        }
    }

    public static Set<String> expand(String regex) {
        Xeger generator = new Xeger(regex);

        Set<String> generated = new HashSet<>();
        for (int i = 0; i < 10000; i++) { // bruteforce
            generated.add(generator.generate());
        }
        return generated;
    }
}
