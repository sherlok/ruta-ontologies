

/* First created by JCasGen Fri Mar 13 09:57:04 CET 2015 */
package org.apache.uima.ruta.type.tag;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Fri Mar 13 09:57:04 CET 2015
 * XML source: /Volumes/HDD2/ren_data/dev_hdd/uima/sherlok/ruta-tag/src/test/resources/desc/type/TagTestTypes.xml
 * @generated */
public class Animal extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Animal.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Animal() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Animal(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Animal(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Animal(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: color

  /** getter for color - gets 
   * @generated
   * @return value of the feature 
   */
  public String getColor() {
    if (Animal_Type.featOkTst && ((Animal_Type)jcasType).casFeat_color == null)
      jcasType.jcas.throwFeatMissing("color", "org.apache.uima.ruta.type.tag.Animal");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Animal_Type)jcasType).casFeatCode_color);}
    
  /** setter for color - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setColor(String v) {
    if (Animal_Type.featOkTst && ((Animal_Type)jcasType).casFeat_color == null)
      jcasType.jcas.throwFeatMissing("color", "org.apache.uima.ruta.type.tag.Animal");
    jcasType.ll_cas.ll_setStringValue(addr, ((Animal_Type)jcasType).casFeatCode_color, v);}    
   
    
  //*--------------*
  //* Feature: species

  /** getter for species - gets 
   * @generated
   * @return value of the feature 
   */
  public String getSpecies() {
    if (Animal_Type.featOkTst && ((Animal_Type)jcasType).casFeat_species == null)
      jcasType.jcas.throwFeatMissing("species", "org.apache.uima.ruta.type.tag.Animal");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Animal_Type)jcasType).casFeatCode_species);}
    
  /** setter for species - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSpecies(String v) {
    if (Animal_Type.featOkTst && ((Animal_Type)jcasType).casFeat_species == null)
      jcasType.jcas.throwFeatMissing("species", "org.apache.uima.ruta.type.tag.Animal");
    jcasType.ll_cas.ll_setStringValue(addr, ((Animal_Type)jcasType).casFeatCode_species, v);}    
  }

    