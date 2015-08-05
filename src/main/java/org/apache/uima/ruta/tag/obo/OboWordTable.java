package org.apache.uima.ruta.tag.obo;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.uima.ruta.RutaBlock;
import org.apache.uima.ruta.RutaStatement;
import org.apache.uima.ruta.engine.RutaEngine;
import org.apache.uima.ruta.expression.resource.WordTableExpression;
import org.apache.uima.ruta.resource.RutaResourceLoader;
import org.apache.uima.ruta.resource.RutaTable;
import org.apache.uima.ruta.resource.RutaWordList;
import org.apache.uima.ruta.resource.TreeWordList;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class OboWordTable extends WordTableExpression {

    private String oboPath;

    public OboWordTable(String oboPath) throws IOException {
        this.oboPath = oboPath;
    }

    @Override
    public RutaTable getTable(RutaStatement element) {

        ResourceLoader resourceLoader = new RutaResourceLoader(element
                .getEnvironment().getResourcePaths());
        Resource resource = resourceLoader.getResource(oboPath);
        if (!resource.exists()) {
            throw new RuntimeException("Cannot file obo file "
                    + resource.getFilename() + " [" + oboPath + "]");
        } else {
            try {
                OBOOntology obo = new OBOOntology().read(resource
                        .getInputStream());
                if (resource.getFilename().endsWith("robo")) {
                    RoboExpander.expand(obo);
                }
                return new OboRutaTable(obo);
            } catch (IOException e) {
                throw new RuntimeException("Error reading obo file "
                        + resource.getFilename(), e);
            } catch (OboFormatException e) {
                throw new RuntimeException("OBO format error: "
                        + resource.getFilename(), e);
            }
        }
    }

    public static class OboRutaTable implements RutaTable {

        private List<List<String>> tableData = new ArrayList<>();

        private Map<Integer, RutaWordList> columnWordLists = new HashMap<>(2);

        public OboRutaTable(OBOOntology obo) {
            for (OntologyTerm term : obo.getTerms().values()) {

                String id = term.getId();
                String name = term.getName();
                if (name != null) {
                    tableData.add(asList(new String[] { name, id }));
                }
                for (Synonym s : term.getSynonyms()) {
                    tableData.add(asList(new String[] { s.getSyn(), id }));
                }
            }
        }

        // FIXME below copied from CSVTable :-( --> ask to make fields protected

        public RutaWordList getWordList(int index, RutaBlock parent) {
            RutaWordList list = columnWordLists.get(index);
            if (list == null) {
                if (index > 0 && index <= tableData.get(0).size()) {
                    Boolean dictRemoveWS = (Boolean) parent.getContext()
                            .getConfigParameterValue(
                                    RutaEngine.PARAM_DICT_REMOVE_WS);
                    if (dictRemoveWS == null) {
                        dictRemoveWS = false;
                    }
                    list = new TreeWordList(getColumnData(index - 1),
                            dictRemoveWS);
                    columnWordLists.put(index, list);
                }
            }
            return list;
        }

        private List<String> getColumnData(int i) {
            List<String> result = new LinkedList<String>();
            for (List<String> each : tableData) {
                if (each.size() > i) {
                    result.add(each.get(i));
                } else {
                    result.add("");
                }
            }
            return result;
        }

        public String getEntry(int row, int column) {
            return tableData.get(row).get(column);
        }

        public List<String> getRowWhere(int column, String value) {
            List<String> columnData = getColumnData(column);
            int i = 0;
            for (String string : columnData) {
                if (string.toLowerCase().equals(value.toLowerCase())) {
                    return tableData.get(i);
                }
                i++;
            }
            i = 0;
            for (String string : columnData) {
                if (string.toLowerCase().replaceAll("\\s", "")
                        .equals(value.toLowerCase())) {
                    return tableData.get(i);
                }
                i++;
            }
            return new ArrayList<String>();
        }
    }
}