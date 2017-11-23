package org.apache.uima.ruta.ontologies;

import static org.apache.commons.io.FilenameUtils.getExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.uima.ruta.RutaElement;
import org.apache.uima.ruta.action.AbstractRutaAction;
import org.apache.uima.ruta.action.ActionFactory;
import org.apache.uima.ruta.action.MarkTableAction;
import org.apache.uima.ruta.expression.AnnotationTypeExpression;
import org.apache.uima.ruta.expression.RutaExpression;
import org.apache.uima.ruta.expression.bool.SimpleBooleanExpression;
import org.apache.uima.ruta.expression.number.ComposedNumberExpression;
import org.apache.uima.ruta.expression.number.INumberExpression;
import org.apache.uima.ruta.expression.number.SimpleNumberExpression;
import org.apache.uima.ruta.expression.resource.WordTableExpression;
import org.apache.uima.ruta.expression.string.ComposedStringExpression;
import org.apache.uima.ruta.expression.string.IStringExpression;
import org.apache.uima.ruta.expression.string.SimpleStringExpression;
import org.apache.uima.ruta.expression.type.SimpleTypeExpression;
import org.apache.uima.ruta.extensions.IRutaActionExtension;
import org.apache.uima.ruta.extensions.RutaParseException;
import org.apache.uima.ruta.tag.obo.OboWordTable;
import org.apache.uima.ruta.verbalize.RutaVerbalizer;

/**
 * Ruta extension to handle simple cases of lexical matching from static
 * ontology files (obo). Also works with csv and txt.
 * 
 * @author renaud@apache.org
 */
public class OntoActionExtension implements IRutaActionExtension {

    public final static String EXTENSION_KEYWORD = "ONTO";
    public final static SimpleBooleanExpression TRUE = new SimpleBooleanExpression(
            true);
    public final static SimpleBooleanExpression FALSE = new SimpleBooleanExpression(
            false);
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
            throw new RutaParseException(EXTENSION_KEYWORD
                    + " acccepts as arguments File AnnotationClass");
        } else {

            file = get(args, 0);

            // Annotation type (2nd parameter)
            RutaExpression re = args.get(1);
            AnnotationTypeExpression ate = (AnnotationTypeExpression) re;
            typeString = ate.toString();

            // case ignored if the length of the word exceeds 4
            INumberExpression ignoreLength = new SimpleNumberExpression(4);

            // check extension
            String fileExt = getExtension(file);
            if (!"obo".equals(fileExt) && !"robo".equals(fileExt)) {
                throw new RutaParseException(
                        "ONTO supports files of type 'obo' or 'robo', but you provided '"
                                + file + "'");
            } else {
                WordTableExpression table;
                try {
                    table = new OboWordTable(file);

                } catch (FileNotFoundException e) {
                    throw new RutaParseException(
                            "could not find (R)OBO ontology at '" + file
                                    + "' (resolving to '"
                                    + new File(file).getAbsolutePath() + "'");
                } catch (IOException e) {
                    throw new RutaParseException(
                            "could not parse (R)OBO ontology at '" + file
                                    + "' (resolving to '"
                                    + new File(file).getAbsolutePath() + "'");
                }

                // mapping
                String idFieldName = "id"; // default
                if (args.size() >= 3) {// -> take id from 3rd arg
                    idFieldName = get(args, 2);

                }
                if (args.size() == 4) {// -> take ignoreLength from 4th arg
                    try {
                        ignoreLength = getInt(args, 3);
                    } catch (Exception nfe) {
                        nfe.printStackTrace();
                        throw new RutaParseException(
                                "optional 4th argument needs to be a number but was "
                                        + args.get(3).toString());
                    }
                } else if (args.size() > 4) {
                    throw new RutaParseException(
                            "For (R)OBO file types, ONTO should have 3 arguments with the format ONTO('myfile.obo', MyAnnotationClass, 'idFieldName') and an optional 4th argument ignoreLength");
                }

                // Configuring MartTable. This corresponds to MARKTABLE(te, 1,
                // table, idFieldName = 2, true, ignoreLength, "", 0, null);
                //
                // the Ruta element is searched for all occurences of the
                // entries of the 'index' (1) column of the given 'table' 
                final INumberExpression index = new SimpleNumberExpression(1);
                // idFieldName = 2
                Map<IStringExpression, INumberExpression> featuresMap = new HashMap<>();
                featuresMap.put(new SimpleStringExpression(idFieldName),
                        new SimpleNumberExpression(2));

                // chars x are ignored, but maximally y of them
                IStringExpression ignoreChar = new SimpleStringExpression("");
                INumberExpression maxIgnoreChar = new SimpleNumberExpression(0);

                MarkTableAction2 mta = new MarkTableAction2(ate, index, table, featuresMap,
                                TRUE, ignoreLength, ignoreChar, maxIgnoreChar);
                mta.setIgnoreWS(FALSE); // do not ignore whitespace in resources
                return mta;
            }
        }
    }

    private static String get(List<RutaExpression> args, int i) {
        ComposedStringExpression ex = (ComposedStringExpression) args.get(i);
        return ex.getExpressions().get(0).toString();
    }

    private static SimpleNumberExpression getInt(List<RutaExpression> args,
            int i) {
        ComposedNumberExpression ex = (ComposedNumberExpression) args.get(i);
        ComposedNumberExpression ex2 = (ComposedNumberExpression) ex
                .getExpressions().get(0);
        return (SimpleNumberExpression) ex2.getExpressions().get(0);
    }
}
