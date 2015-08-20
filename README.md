# Ontology support for Apache Ruta

[![](https://img.shields.io/github/release/sherlok/ruta-ontologies.svg?label=JitPack)](https://jitpack.io/#sherlok/ruta-ontologies/)



## ONTO keyword

Extends the [Apache Ruta language](https://uima.apache.org/d/ruta-current/tools.ruta.book.html) with an `ONTO` keyword.
 
    

For OBO file types, `ONTO` should have 3 arguments with the format `ONTO('myfile.obo', MyAnnotationClass, 'idFieldName')");`

    PACKAGE org.apache.uima.ruta.type;
    DECLARE Annotation Neurotransmitter(STRING ontologyId);
    Document{->ONTO("hbp_neurotransmitter_ontology.obo", Neurotransmitter, "ontologyId")};


## ROBO file format

So as to improve the management of synonyms in an ontology, we propose an enhanced version of the OBO format called _ROBO_ (for regular-expression OBO). ROBO allows to specify synonyms through compact regular expressions, thus improving the expressiveness and compactness of the ontology. For example, the synonyms for the layer two of the neocortex:

    [Term]
    id: LAYER:001
    name: layer 2
    synonym: "layer-2"
    synonym: "layer 2"
    synonym: "layer-II"
    synonym: "layer II"
    synonym: "layer-ii"
    synonym: "layer ii"

can be written in ROBO as a single regular expression:

    [Term] 
    id: LAYER:001
    name: layer 2
    rsynonym: "layer[ -](II|ii|2)"
