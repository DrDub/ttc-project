
/* First created by JCasGen Sat Sep 24 08:50:19 CEST 2011 */
package eu.project.ttc.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;

/** 
 * Updated by JCasGen Sat Sep 24 11:28:02 CEST 2011
 * @generated */
public class CompoundWordAnnotation_Type extends WordAnnotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (CompoundWordAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = CompoundWordAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new CompoundWordAnnotation(addr, CompoundWordAnnotation_Type.this);
  			   CompoundWordAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new CompoundWordAnnotation(addr, CompoundWordAnnotation_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = CompoundWordAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("eu.project.ttc.types.CompoundWordAnnotation");



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public CompoundWordAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

  }
}



    