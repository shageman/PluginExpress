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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jboss.netty.handler.codec.http.HttpMethod;
import org.restexpress.Format;
import org.restexpress.RestExpress;
import org.restexpress.plugin.AbstractPlugin;
import org.restexpress.route.RouteBuilder;

/**
 * @author toddf
 * @since Nov 21, 2013
 */
public class SwaggerPlugin
extends AbstractPlugin
{
	private static final String SWAGGER_VERSION = "1.2";

	private SwaggerController controller;
	private String urlPath;
	private String apiVersion;
	private String swaggerVersion = SWAGGER_VERSION;
	private List<String> flags = new ArrayList<String>();
	private Map<String, Object> parameters = new HashMap<String, Object>();

	public SwaggerPlugin()
	{
		this("/api-docs");
	}

	public SwaggerPlugin(String urlPath)
	{
		super();
		this.urlPath = urlPath;
	}

	public SwaggerPlugin apiVersion(String version)
	{
		this.apiVersion = version;
		return this;
	}

	public SwaggerPlugin swaggerVersion(String version)
	{
		this.swaggerVersion = version;
		return this;
	}

	@Override
	public SwaggerPlugin register(RestExpress server)
	{
		if (isRegistered()) return this;

		super.register(server);
		controller = new SwaggerController(server, apiVersion, swaggerVersion);

		RouteBuilder resources = server.uri(urlPath, controller)
			.action("readAll", HttpMethod.GET)
			.name("swagger.resources")
		    .format(Format.JSON);

		RouteBuilder apis = server.uri(urlPath + "/{path}", controller)
			.method(HttpMethod.GET)
			.name("swagger.apis")
			.format(Format.JSON);
		
		for (String flag : flags)
		{
			resources.flag(flag);
			apis.flag(flag);
		}

		for (Entry<String, Object> entry : parameters.entrySet())
		{
			resources.parameter(entry.getKey(), entry.getValue());
			apis.parameter(entry.getKey(), entry.getValue());
		}

		return this;
	}

	@Override
	public void bind(RestExpress server)
	{
		controller.initialize(urlPath, server.getRouteMetadata());
	}

	// RouteBuilder route augmentation delegates.

	public SwaggerPlugin flag(String flagValue)
	{
		if (!flags.contains(flagValue))
		{
			flags.add(flagValue);
		}

		return this;
	}

	public SwaggerPlugin parameter(String name, Object value)
	{
		if (!parameters.containsKey(name))
		{
			parameters.put(name, value);
		}

		return this;
	}
}
