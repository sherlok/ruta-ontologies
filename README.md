# Apache Ruta TAG extension

Extends the [Apache Ruta language](https://uima.apache.org/d/ruta-current/tools.ruta.book.html) with a `TAG` keyword.
 
    PACKAGE org.apache.uima.ruta.type;

    // tagging with a text file
    DECLARE RutaColoring;
    Document{ -> TAG(RutaColoring, "colors.txt")};

    // tagging with an OBO file
    DECLARE Animal;
    Document{ -> TAG(Animal, "animals.obo")};

