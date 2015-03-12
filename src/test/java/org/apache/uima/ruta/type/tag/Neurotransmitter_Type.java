
/* First created by JCasGen Fri Mar 13 11:37:29 CET 2015 */
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
 * Updated by JCasGen Fri Mar 13 11:37:29 CET 2015
 * @generated */
public class Neurotransmitter_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Neurotransmitter_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Neurotransmitter_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Neurotransmitter(addr, Neurotransmitter_Type.this);
  			   Neurotransmitter_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Neurotransmitter(addr, Neurotransmitter_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Neurotransmitter.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.apache.uima.ruta.type.tag.Neurotransmitter");
 
  /** @generated */
  final Feature casFeat_ontologyId;
  /** @generated */
  final int     casFeatCode_ontologyId;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getOntologyId(int addr) {
        if (featOkTst && casFeat_ontologyId == null)
      jcas.throwFeatMissing("ontologyId", "org.apache.uima.ruta.type.tag.Neurotransmitter");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ontologyId);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setOntologyId(int addr, String v) {
        if (featOkTst && casFeat_ontologyId == null)
      jcas.throwFeatMissing("ontologyId", "org.apache.uima.ruta.type.tag.Neurotransmitter");
    ll_cas.ll_setStringValue(addr, casFeatCode_ontologyId, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Neurotransmitter_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_ontologyId = jcas.getRequiredFeatureDE(casType, "ontologyId", "uima.cas.String", featOkTst);
    casFeatCode_ontologyId  = (null == casFeat_ontologyId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ontologyId).getCode();

  }
}



    