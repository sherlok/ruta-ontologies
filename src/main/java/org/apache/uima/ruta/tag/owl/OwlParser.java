package org.apache.uima.ruta.tag.owl;

import static org.semanticweb.owlapi.search.Searcher.annotations;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_LABEL;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.ruta.tag.obo.OntologyTerm;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OwlParser {

    public static final String NIF_SYNONYM = "http://ontology.neuinfo.org/NIF/Backend/OBO_annotation_properties.owl#synonym";

    private final OWLOntology ontology;
    private OWLDataFactory df;

    public OwlParser(OWLOntology ontology, OWLDataFactory df) {
        this.ontology = ontology;
        this.df = df;
    }

    public List<OntologyTerm> parseOntology()
            throws OWLOntologyCreationException {
        List<OntologyTerm> terms = new ArrayList<>();

        for (OWLClass cls : ontology.getClassesInSignature()) {

            String id = cls.getIRI().toString();
            String label = get(cls, RDFS_LABEL.toString()).get(0);
            List<String> synonms = get(cls, NIF_SYNONYM);

            OntologyTerm term = new OntologyTerm(id, label);

            // System.out.println(label + " [" + id + "]");
            for (String s : synonms) {
                term.addSynonym(s); // System.out.println("   " + s);
            }
            terms.add(term);
        }
        return terms;
    }

    private List<String> get(OWLClass clazz, String property) {
        List<String> ret = new ArrayList<>();

        final OWLAnnotationProperty owlProperty = df
                .getOWLAnnotationProperty(IRI.create(property));

        for (OWLOntology o : ontology.getImportsClosure()) {

            for (OWLAnnotation annotation : annotations(
                    o.getAnnotationAssertionAxioms(clazz.getIRI()), owlProperty)) {
                if (annotation.getValue() instanceof OWLLiteral) {
                    OWLLiteral val = (OWLLiteral) annotation.getValue();
                    ret.add(val.getLiteral());
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) throws OWLException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {

        // String x =
        // "file:///Volumes/HDD2/ren_data/dev_hdd/uima/sherlok/ruta-tag/NIF-Dysfunction.owl";
        //String x = "http://ontology.neuinfo.org/NIF/Dysfunction/NIF-Dysfunction.owl";
        String x = "http://repos.frontology.org/dron/raw/master/dron-full.owl";

        IRI documentIRI = IRI.create(x);
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager
                .loadOntologyFromOntologyDocument(documentIRI);

        OwlParser parser = new OwlParser(ontology, manager.getOWLDataFactory());
        List<OntologyTerm> onto = parser.parseOntology();
        for (OntologyTerm term : onto) {
            System.out.println(term);
        }
    }
}
