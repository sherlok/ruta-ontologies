# Ontology support for Apache Ruta

## ONTO keyword

Extends the [Apache Ruta language](https://uima.apache.org/d/ruta-current/tools.ruta.book.html) with an `ONTO` keyword.
 
    

For OBO file types, `ONTO` should have 3 arguments with the format `ONTO('myfile.obo', MyAnnotationClass, 'idFieldName')");`

    PACKAGE org.apache.uima.ruta.type;
    DECLARE Annotation Neurotransmitter(STRING ontologyId);
    Document{->ONTO("hbp_neurotransmitter_ontology.obo", Neurotransmitter, "ontologyId")};


For CSV file types, `ONTO` should have the format `ONTO('myfile.csv', MyAnnotationClass, 'field1, 'field2', ... )");`

    PACKAGE org.apache.uima.ruta.type.tag;
    DECLARE Annotation Animal(STRING color, STRING species);
    Document{ -> ONTO("animals.csv", Animal, "color")};



## Some OBO Ontologies


### [some NS-relevant OWL and OBO ontologies](https://bbpteam.epfl.ch/project/spaces/display/NLP/OBO)

* [Relation ontology](http://www.obofoundry.org/cgi-bin/detail.cgi?id=ro)
    * http://obo-relations.googlecode.com/svn/trunk/src/ontology/ro.obo
    * http://obo-relations.googlecode.com/svn/trunk/src/ontology/ro.owl
* [Subcellular anatomy ontology](http://www.obofoundry.org/cgi-bin/detail.cgi?id=sao)
    * http://ccdb.ucsd.edu/SAO/1.2/SAO.owl
* [Biological Spatial Ontology](http://www.obofoundry.org/cgi-bin/detail.cgi?id=spatial)
    * https://biological-spatial-ontology.googlecode.com/svn/trunk/src/ontology/bspo.obo
* [Ontology for biomedical investigations](http://www.obofoundry.org/cgi-bin/detail.cgi?id=obi)
    * http://www.berkeleybop.org/ontologies/obi.obo
* [Neuroscience methods ontology](https://github.com/tgbugs/methodsOntology)
    * https://github.com/tgbugs/methodsOntology/blob/master/ns_methods.obo 
