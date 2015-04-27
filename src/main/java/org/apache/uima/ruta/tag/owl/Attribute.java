package org.apache.uima.ruta.tag.owl;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Simple name value pair. An entity can have multiple Attributes.
 * 
 * @author Sujit Pal
 * @version $Revision: 8 $
 */
public class Attribute {

    private String name;
    private String value;

    public Attribute() {
        super();
    }

    public Attribute(String name, String value) {
        this();
        setName(name);
        setValue(value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Attribute)) {
            return false;
        }
        Attribute that = (Attribute) obj;
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public String toString() {
        return name + "::" + value;
    }
}
