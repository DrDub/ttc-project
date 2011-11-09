package eu.project.ttc.tools.ziggurat;

import org.apache.uima.resource.metadata.ConfigurationParameter;
import org.apache.uima.resource.metadata.ConfigurationParameterDeclarations;

import eu.project.ttc.tools.utils.Parameters;

public class ZigguratSettings extends Parameters {	
	
	public ZigguratSettings(String resource) {
		super(resource);
	}

	protected void setMetaData(ConfigurationParameterDeclarations declarations) {
		this.addParameter(declarations, "SourceLanguage", ConfigurationParameter.TYPE_STRING, false, true);
		this.addParameter(declarations, "SourceDirectory", ConfigurationParameter.TYPE_STRING, false, true);
		this.addParameter(declarations, "SourceTerminologyFile", ConfigurationParameter.TYPE_STRING, false, true);
		this.addParameter(declarations, "TargetLanguage", ConfigurationParameter.TYPE_STRING, false, true);
		this.addParameter(declarations, "TargetDirectory", ConfigurationParameter.TYPE_STRING, false, false);
		this.addParameter(declarations, "TargetTerminologyFile", ConfigurationParameter.TYPE_STRING, false, true);
		this.addParameter(declarations, "AlignmentFile", ConfigurationParameter.TYPE_STRING, false, true);
		this.addParameter(declarations, "ScopeSize", ConfigurationParameter.TYPE_INTEGER, false, false);
		this.addParameter(declarations, "AssociationRateClassName", ConfigurationParameter.TYPE_STRING, false, false);
		this.addParameter(declarations, "SimilarityDistanceClassName", ConfigurationParameter.TYPE_STRING, false, false);
		this.addParameter(declarations, "DictionaryFile", ConfigurationParameter.TYPE_STRING, false, false);
		this.addParameter(declarations, "EvaluationListFile", ConfigurationParameter.TYPE_STRING, false, false);
	}
		
}
