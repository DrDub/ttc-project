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

import eu.project.ttc.types.TermAnnotation;

/**
 * A predicate for filtering terms.
 * 
 * @author Sebastián Peña Saldarriaga
 */
public interface TermPredicate {

	/**
	 * Accepts a term depending on an implementation dependent condition.
	 * 
	 * @param term
	 *            The term to verify
	 * @return <code>true</code> if the term is accepted or <code>false</code>
	 *         otherwise.
	 */
	public boolean accept(TermAnnotation term);
}
