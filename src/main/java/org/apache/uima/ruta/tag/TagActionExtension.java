package org.apache.uima.ruta.tag;

import static java.io.File.createTempFile;
import static org.apache.commons.io.FileUtils.copyURLToFile;
import static org.apache.commons.io.FilenameUtils.getExtension;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.uima.ruta.RutaElement;
import org.apache.uima.ruta.action.AbstractRutaAction;
import org.apache.uima.ruta.action.ActionFactory;
import org.apache.uima.ruta.expression.RutaExpression;
import org.apache.uima.ruta.expression.bool.IBooleanExpression;
import org.apache.uima.ruta.expression.bool.SimpleBooleanExpression;
import org.apache.uima.ruta.expression.number.INumberExpression;
import org.apache.uima.ruta.expression.number.SimpleNumberExpression;
import org.apache.uima.ruta.expression.resource.LiteralWordListExpression;
import org.apache.uima.ruta.expression.resource.LiteralWordTableExpression;
import org.apache.uima.ruta.expression.resource.WordListExpression;
import org.apache.uima.ruta.expression.resource.WordTableExpression;
import org.apache.uima.ruta.expression.string.ComposedStringExpression;
import org.apache.uima.ruta.expression.string.IStringExpression;
import org.apache.uima.ruta.expression.string.SimpleStringExpression;
import org.apache.uima.ruta.expression.type.SimpleTypeExpression;
import org.apache.uima.ruta.extensions.IRutaActionExtension;
import org.apache.uima.ruta.extensions.RutaParseException;
import org.apache.uima.ruta.tag.obo.OboWordTable;
import org.apache.uima.ruta.verbalize.RutaVerbalizer;

public class TagActionExtension implements IRutaActionExtension {

    public final static String EXTENSION_KEYWORD = "TAG";
    public final static SimpleBooleanExpression TRUE = new SimpleBooleanExpression(
            true);
    private final Class<?>[] extensions = new Class[] {};

    private String typeString;
    private String file;

    @Override
    public String verbalize(RutaElement element, RutaVerbalizer verbalizer) {
        return verbalizeName(element) + "(" + typeString + ", " + file + ")";
    }

    @Override
    public String verbalizeName(RutaElement element) {
        return EXTENSION_KEYWORD;
    }

    @Override
    public String[] getKnownExtensions() {
        return new String[] { EXTENSION_KEYWORD };
    }

    @Override
    public Class<?>[] extensions() {
        return extensions;
    }

    public AbstractRutaAction createAction(String name,
            List<RutaExpression> args) throws RutaParseException {

        if (args == null || args.size() < 2) {
            throw new RutaParseException(
                    "TAG acccepts as arguments File AnnotationClass");
        } else {

            file = get(args, 0);

            /*-
            // handle remote urls
            if (file.startsWith("http://") || file.startsWith("https://")) {
                String url = file;
                try {
                    String urlExt = url.substring(url.lastIndexOf('.'));
                    File tmpFile = createTempFile(
                            file.substring(file.lastIndexOf('/')), urlExt);
                    copyURLToFile(new URL(file), tmpFile);

                    file = tmpFile.getAbsolutePath();
                } catch (Exception e) {
                    throw new RutaParseException("Could not download '" + url
                            + "'");
                }
                // Ensure file is not empty (check if 1st line is empty)
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    if (br.readLine() == null) {
                        br.close();
                        throw new IOException();
                    }
                    br.close();
                } catch (IOException e) {
                    throw new RutaParseException(url + " is empty");
                }
            }*/

            // Annotation type (2nd parameter)
            RutaExpression re = args.get(1);
            SimpleTypeExpression te = (SimpleTypeExpression) re;
            typeString = te.getTypeString();

            // switch based on extension
            String fileExt = getExtension(file);
            if ("txt".equals(fileExt)) {
                WordListExpression list = new LiteralWordListExpression(file);
                IBooleanExpression ignore = TRUE;
                INumberExpression ignoreLengthTxt = new SimpleNumberExpression(
                        3);
                IBooleanExpression ignoreWS = TRUE;
                return ActionFactory.createMarkFastAction(te, list, ignore,
                        ignoreLengthTxt, ignoreWS, null);

            } else { // CSV or OBO or fail

                // common params for CSV and OBO
                // the Ruta element is searched for all occurences of the
                // entries of the 'index' column of the given 'table'
                INumberExpression index = new SimpleNumberExpression(1);
                WordTableExpression table;
                // case ignored if the length of the word exceeds 4
                IBooleanExpression ignoreCase = TRUE;
                INumberExpression ignoreLength = new SimpleNumberExpression(4);

                if ("csv".equals(fileExt)) {
                    if (args.size() < 3) {
                        throw new RutaParseException(
                                "For CSV file types, TAG should have the format TAG('myfile.csv', MyAnnotationClass, 'field1, 'field2', ... )");
                    }
                    table = new LiteralWordTableExpression(file);

                    // chars x are ignored, but maximally y of them
                    IStringExpression ignoreChar = new SimpleStringExpression(
                            "");
                    INumberExpression maxIgnoreChar = new SimpleNumberExpression(
                            0);

                    // mapping
                    Map<IStringExpression, INumberExpression> map = new HashMap<>();
                    for (int i = 2; i < args.size(); i++) {
                        String fieldName = get(args, i);
                        map.put(new SimpleStringExpression(fieldName),
                                new SimpleNumberExpression(i));
                    }

                    return ActionFactory.createMarkTableAction(te, index,
                            table, map, ignoreCase, ignoreLength, ignoreChar,
                            maxIgnoreChar, null);

                } else if ("obo".equals(fileExt)) {
                    try {
                        table = new OboWordTable(file);
                    } catch (FileNotFoundException e) {
                        throw new RutaParseException(
                                "could not find OBO ontology at '" + file
                                        + "' (resolving to '"
                                        + new File(file).getAbsolutePath()
                                        + "'");
                    } catch (IOException e) {
                        throw new RutaParseException(
                                "could not parse OBO ontology at '" + file
                                        + "' (resolving to '"
                                        + new File(file).getAbsolutePath()
                                        + "'");
                    }

                    // mapping
                    String idFieldName = "id"; // default
                    if (args.size() == 3) {// -> take id from 3rd arg
                        idFieldName = get(args, 2);
                    } else {
                        throw new RutaParseException(
                                "For OBO file types, TAG should have the format TAG('myfile.obo', MyAnnotationClass, 'idFieldName')");
                    }
                    Map<IStringExpression, INumberExpression> map = new HashMap<>();
                    map.put(new SimpleStringExpression(idFieldName),
                            new SimpleNumberExpression(2));

                    // chars x are ignored, but maximally y of them
                    IStringExpression ignoreChar = new SimpleStringExpression(
                            "");
                    INumberExpression maxIgnoreChar = new SimpleNumberExpression(
                            0);

                    return ActionFactory.createMarkTableAction(te, index,
                            table, map, ignoreCase, ignoreLength, ignoreChar,
                            maxIgnoreChar, null);

                } else {
                    throw new RutaParseException(
                            "TAG acccepts as arguments AnnotationClass File, where File should be of type 'txt', 'csv' or 'obo', but you provided '"
                                    + file + "'");
                }
            }
        }
    }

    private static String get(List<RutaExpression> args, int i) {
        ComposedStringExpression ex = (ComposedStringExpression) args.get(i);
        return ex.getExpressions().get(0).toString();
    }
}
