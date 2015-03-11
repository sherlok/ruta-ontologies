package org.apache.uima.ruta.tag;

import java.util.List;

import org.apache.uima.ruta.RutaElement;
import org.apache.uima.ruta.action.AbstractRutaAction;
import org.apache.uima.ruta.action.ActionFactory;
import org.apache.uima.ruta.expression.RutaExpression;
import org.apache.uima.ruta.expression.bool.IBooleanExpression;
import org.apache.uima.ruta.expression.bool.SimpleBooleanExpression;
import org.apache.uima.ruta.expression.number.INumberExpression;
import org.apache.uima.ruta.expression.number.SimpleNumberExpression;
import org.apache.uima.ruta.expression.resource.LiteralWordListExpression;
import org.apache.uima.ruta.expression.resource.WordListExpression;
import org.apache.uima.ruta.expression.string.ComposedStringExpression;
import org.apache.uima.ruta.expression.type.SimpleTypeExpression;
import org.apache.uima.ruta.extensions.IRutaActionExtension;
import org.apache.uima.ruta.extensions.RutaParseException;
import org.apache.uima.ruta.verbalize.RutaVerbalizer;

public class TagActionExtension implements IRutaActionExtension {

    public final static String EXTENSION_KEYWORD = "TAG";
    private final Class<?>[] extensions = new Class[] { TagAction.class };

    private String typeString;
    private String file;

    public String verbalize(RutaElement element, RutaVerbalizer verbalizer) {
        return verbalizeName(element) + "(" + typeString + ", " + file + ")";
    }

    public String verbalizeName(RutaElement element) {
        return EXTENSION_KEYWORD;
    }

    public String[] getKnownExtensions() {
        return new String[] { EXTENSION_KEYWORD };
    }

    public Class<?>[] extensions() {
        return extensions;
    }

    public String getFile() {
        return file;
    }

    public String getTypeString() {
        return typeString;
    }

    public AbstractRutaAction createAction(String name,
            List<RutaExpression> args) throws RutaParseException {

        if (args == null || args.size() != 2) {
            throw new RutaParseException(
                    "TAG acccepts as arguments AnnotationClass File");
        } else {

            RutaExpression re = args.get(0);
            SimpleTypeExpression te = (SimpleTypeExpression) re;
            typeString = te.getTypeString();

            RutaExpression re2 = args.get(1);
            ComposedStringExpression ce = (ComposedStringExpression) re2;
            file = ce.getExpressions().get(0).toString();

            WordListExpression list = new LiteralWordListExpression(file);

            IBooleanExpression ignore = new SimpleBooleanExpression(true);
            INumberExpression ignoreLength = new SimpleNumberExpression(3);
            IBooleanExpression ignoreWS = new SimpleBooleanExpression(true);
            return ActionFactory.createMarkFastAction(te, list, ignore,
                    ignoreLength, ignoreWS, null);
        }
    }
}
