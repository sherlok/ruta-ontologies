//package net.sf.jtmt.ontology.relational;
package org.apache.uima.ruta.tag.owl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Holder class for node information. The equals() and hashCode() method have
 * been overriden.
 * 
 * @author Sujit Pal
 * @version $Revision: 8 $
 */
public class Entity implements Serializable {

    private static final long serialVersionUID = 54272228896206677L;

    private long id;
    private String name;
    private List<Attribute> attributes = new ArrayList<Attribute>();

    public Entity() {
        super();
    }

    public Entity(long id) {
        this();
        setId(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

    @Override
    public int hashCode() {
        return (int) id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Entity)) {
            return false;
        }
        Entity that = (Entity) obj;
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this,
                ToStringStyle.NO_FIELD_NAMES_STYLE);
    }
}
