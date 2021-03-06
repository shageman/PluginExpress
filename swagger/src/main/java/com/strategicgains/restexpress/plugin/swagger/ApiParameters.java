/*
    Copyright 2013, Strategic Gains, Inc.

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/
package com.strategicgains.restexpress.plugin.swagger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author toddf
 * @since Nov 22, 2013
 */
public class ApiParameters
{
	private String paramType; // path, query, body, header, form
	private String name;
	private String description;
	private String dataType;	// primitive type, or complex or container (for body paramType)
	private String format;
	private boolean required;	// must be true for query paramType
	
	@JsonProperty("enum")
	private String[] enumeration;
	
	public ApiParameters(String type, String name, String dataType, boolean isRequired)
	{
		super();
		this.paramType = type;
		this.name = name;
		this.dataType = dataType;
		this.required = isRequired;
	}
}
