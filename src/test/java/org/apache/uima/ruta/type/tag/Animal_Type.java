
/* First created by JCasGen Fri Mar 13 09:57:04 CET 2015 */
package org.apache.uima.ruta.type.tag;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Fri Mar 13 09:57:04 CET 2015
 * @generated */
public class Animal_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Animal_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Animal_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Animal(addr, Animal_Type.this);
  			   Animal_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Animal(addr, Animal_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Animal.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.apache.uima.ruta.type.tag.Animal");
 
  /** @generated */
  final Feature casFeat_color;
  /** @generated */
  final int     casFeatCode_color;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getColor(int addr) {
        if (featOkTst && casFeat_color == null)
      jcas.throwFeatMissing("color", "org.apache.uima.ruta.type.tag.Animal");
    return ll_cas.ll_getStringValue(addr, casFeatCode_color);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setColor(int addr, String v) {
        if (featOkTst && casFeat_color == null)
      jcas.throwFeatMissing("color", "org.apache.uima.ruta.type.tag.Animal");
    ll_cas.ll_setStringValue(addr, casFeatCode_color, v);}
    
  
 
  /** @generated */
  final Feature casFeat_species;
  /** @generated */
  final int     casFeatCode_species;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSpecies(int addr) {
        if (featOkTst && casFeat_species == null)
      jcas.throwFeatMissing("species", "org.apache.uima.ruta.type.tag.Animal");
    return ll_cas.ll_getStringValue(addr, casFeatCode_species);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSpecies(int addr, String v) {
        if (featOkTst && casFeat_species == null)
      jcas.throwFeatMissing("species", "org.apache.uima.ruta.type.tag.Animal");
    ll_cas.ll_setStringValue(addr, casFeatCode_species, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Animal_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_color = jcas.getRequiredFeatureDE(casType, "color", "uima.cas.String", featOkTst);
    casFeatCode_color  = (null == casFeat_color) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_color).getCode();

 
    casFeat_species = jcas.getRequiredFeatureDE(casType, "species", "uima.cas.String", featOkTst);
    casFeatCode_species  = (null == casFeat_species) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_species).getCode();

  }
}



    