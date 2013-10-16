/**
 * Copyright (c) 2013 dictanova. All rights reserved.
 * Created by sebastian / 16 oct. 2013
 */
package eu.project.ttc.tools.spotter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.FsIndexDescription;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasCreationUtils;
import org.apache.uima.util.XmlCasDeserializer;
import org.xml.sax.SAXException;

/**
 * @author Sebastián Peña Saldarriaga
 * 
 */
public class ExtendedProcessingResult extends ProcessingResult {

    private final CAS leCas;

    private final File leFile;

    private final TypeSystemDescription leTypeSystem;

    public ExtendedProcessingResult(File xmiFile,
            TypeSystemDescription description)
            throws ResourceInitializationException, SAXException, IOException {
        this.leFile = xmiFile;
        this.leTypeSystem = description;
        leCas = CasCreationUtils.createCas(leTypeSystem, null,
                new FsIndexDescription[0]);
        FileInputStream is = new FileInputStream(leFile);
        XmlCasDeserializer.deserialize(is, leCas, true);
        is.close();
    }

    @Override
    public CAS getCas() throws Exception {
        return leCas;
    }

    @Override
    public File getFile() {
        return super.getFile();
    }

    @Override
    public TypeSystemDescription getTypeSystem() {
        return leTypeSystem;
    }

    @Override
    public void setCas(CAS cas) throws AnalysisEngineProcessException {

    }
    
    @Override
    public String toString() {
        return leFile.getName();
    }
}
