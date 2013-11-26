/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package eu.project.ttc.engines;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;

import eu.project.ttc.types.WordAnnotation;

public class Exporter extends JCasAnnotator_ImplBase {
	
	private File directory;
	
	private void setDirectory(String path) throws IOException {
		File directory = new File(path);
		if (directory.exists()) {
			if (directory.isDirectory()) {
				this.directory = directory;
			} else {
				throw new IOException("This path '" + path + "' isn't a directory");
			}
		} else {
			throw new FileNotFoundException(path);
		}
	}
	
	private File getDirectory() {
		return this.directory;
	}
	
	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);
		try {
			String directory = (String) context.getConfigParameterValue("Directory");
			this.setDirectory(directory);
		} catch (Exception e) {
			throw new ResourceInitializationException(e);
		}
	}
	
	private String retrieve(JCas cas) {
		AnnotationIndex<Annotation> index = cas.getAnnotationIndex(SourceDocumentInformation.type);
		FSIterator<Annotation> iterator = index.iterator();
		if (iterator.hasNext()) {
			SourceDocumentInformation annotation = (SourceDocumentInformation) iterator.next();
			File file = new File(annotation.getUri());
			String name = file.getName();
			int i = name.lastIndexOf('.');
			if (i == -1) {
				return name + ".tsv";
			} else {
				return name.substring(0, i) + ".tsv";
			}
		} else {
			return null;
		}
	}
	
	private String analysis(JCas cas) {
		StringBuilder builder = new StringBuilder();
		AnnotationIndex<Annotation> index = cas.getAnnotationIndex(WordAnnotation.type);
		FSIterator<Annotation> iterator = index.iterator();
		while (iterator.hasNext()) {
			WordAnnotation annotation = (WordAnnotation) iterator.next();
			builder.append(annotation.getCoveredText());
			builder.append('\t');
			builder.append(annotation.getTag());
			builder.append('\t');
			builder.append(annotation.getLemma());
			// builder.append('\t');
			// builder.append(annotation.getStem());
			builder.append('\n');
		}
		return builder.toString();
	}
	
	@Override
	public void process(JCas cas) throws AnalysisEngineProcessException { 
		try {
			String name = this.retrieve(cas);
			if (name == null) { 
				this.getContext().getLogger().log(Level.WARNING,"Skiping CAS Converting");
			} else {
				File file = new File(this.getDirectory(), name);
				OutputStream stream = new FileOutputStream(file);
				OutputStreamWriter writer = new OutputStreamWriter(stream, "UTF-8");
				try {
					writer.write(this.analysis(cas));
					this.getContext().getLogger().log(Level.CONFIG,"Converting " + file.getAbsolutePath());
				} catch (Exception e) {
					this.getContext().getLogger().log(Level.WARNING,"Error while converting " + file.getAbsolutePath() + "\n" + e.getMessage());
				} finally {
					stream.close();
				}
			}
		} catch (Exception e) {
			throw new AnalysisEngineProcessException(e);
		}
	}
	
}
