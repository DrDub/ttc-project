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
package eu.project.ttc.tools.utils;

import java.io.IOException;
import java.io.Writer;

/**
 * Base class for implementing TSV file, builders.
 * 
 * @author Sebastián Peña Saldarriaga
 */
public abstract class AbstractTSVBuilder {

	/** Output of the builder */
	private final Writer output;

	/** Separator between values */
	private final char valSep;

	/** Separator between lines */
	private final String lineSep;

	/**
	 * Creates a new instance using the specified parameters.
	 * 
	 * @param output
	 *            The output writer
	 * @param valSep
	 *            The value separator
	 * @param lineSep
	 *            The line separator
	 */
	protected AbstractTSVBuilder(Writer output, char valSep, String lineSep) {
		this.output = output;
		this.valSep = valSep;
		this.lineSep = lineSep;
	}

	/**
	 * Creates a new instance using the specified <code>output</code> using '\t'
	 * and "\n" as value and line separators.
	 * 
	 * @param output
	 *            The output writer.
	 */
	public AbstractTSVBuilder(Writer output) {
		this(output, '\t', "\n");
	}

	/**
	 * Closes the underlying {@link Writer}, flushing it first.
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		output.close();
	}

	/**
	 * Writes the specified <code>line</code> as is to the writer, i.e. without
	 * appending the new line character.
	 * 
	 * @param line
	 * @throws IOException
	 */
	protected void write(String line) throws IOException {
		output.append(line);
	}

	/**
	 * Appends a line to the writer. The values are separated using the current
	 * separator, an line separator is appended at the end.
	 * 
	 * @param firstVal
	 *            The first value of the line
	 * @param otherVals
	 *            The remaining values
	 * @throws IOException
	 */
	public void append(String firstVal, String... otherVals) throws IOException {
		output.append(firstVal);
		for (String v : otherVals)
			output.append(valSep).append(v);
		output.append(lineSep);
	}
}
