

/* First created by JCasGen Fri Mar 13 11:37:29 CET 2015 */
package org.apache.uima.ruta.type.tag;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Fri Mar 13 11:37:29 CET 2015
 * XML source: /Volumes/HDD2/ren_data/dev_hdd/uima/sherlok/ruta-tag/src/test/resources/desc/type/TagTestTypes.xml
 * @generated */
public class Neurotransmitter extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Neurotransmitter.class);
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
  protected Neurotransmitter() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Neurotransmitter(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Neurotransmitter(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Neurotransmitter(JCas jcas, int begin, int end) {
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
  //* Feature: ontologyId

  /** getter for ontologyId - gets 
   * @generated
   * @return value of the feature 
   */
  public String getOntologyId() {
    if (Neurotransmitter_Type.featOkTst && ((Neurotransmitter_Type)jcasType).casFeat_ontologyId == null)
      jcasType.jcas.throwFeatMissing("ontologyId", "org.apache.uima.ruta.type.tag.Neurotransmitter");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Neurotransmitter_Type)jcasType).casFeatCode_ontologyId);}
    
  /** setter for ontologyId - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setOntologyId(String v) {
    if (Neurotransmitter_Type.featOkTst && ((Neurotransmitter_Type)jcasType).casFeat_ontologyId == null)
      jcasType.jcas.throwFeatMissing("ontologyId", "org.apache.uima.ruta.type.tag.Neurotransmitter");
    jcasType.ll_cas.ll_setStringValue(addr, ((Neurotransmitter_Type)jcasType).casFeatCode_ontologyId, v);}    
  }

    