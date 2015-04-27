# Apache Ruta TAG extension

Extends the [Apache Ruta language](https://uima.apache.org/d/ruta-current/tools.ruta.book.html) with a `TAG` keyword.
 
    PACKAGE org.apache.uima.ruta.type;

    // tagging with a text file
    DECLARE RutaColoring;
    Document{ -> TAG(RutaColoring, "colors.txt")};

    // tagging with an OBO file
    DECLARE Animal;
    Document{ -> TAG(Animal, "animals.obo")};


# Ontologies



some NS-relevant OWL and OBO ontologies 
https://bbpteam.epfl.ch/project/spaces/display/NLP/OBO

Relation ontology, http://www.obofoundry.org/cgi-bin/detail.cgi?id=ro
http://obo-relations.googlecode.com/svn/trunk/src/ontology/ro.obo
http://obo-relations.googlecode.com/svn/trunk/src/ontology/ro.owl


Subcellular anatomy ontology, http://www.obofoundry.org/cgi-bin/detail.cgi?id=sao
http://ccdb.ucsd.edu/SAO/1.2/SAO.owl

Biological Spatial Ontology, http://www.obofoundry.org/cgi-bin/detail.cgi?id=spatial
https://biological-spatial-ontology.googlecode.com/svn/trunk/src/ontology/bspo.obo

Ontology for biomedical investigations, http://www.obofoundry.org/cgi-bin/detail.cgi?id=obi
http://www.berkeleybop.org/ontologies/obi.obo
