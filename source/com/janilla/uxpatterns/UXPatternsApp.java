/*
 * MIT License
 *
 * Copyright (c) 2024 Diego Schivo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.janilla.uxpatterns;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.janilla.net.Server;
import com.janilla.reflect.Factory;
import com.janilla.util.Lazy;
import com.janilla.util.Util;
import com.janilla.web.ApplicationHandlerBuilder;
import com.janilla.web.Handle;
import com.janilla.web.Render;

public class UXPatternsApp {

	public static void main(String[] args) throws Exception {
		var a = new UXPatternsApp();
		{
			var c = new Properties();
			try (var s = a.getClass().getResourceAsStream("configuration.properties")) {
				c.load(s);
			}
			a.configuration = c;
		}

		var s = a.getFactory().create(Server.class);
		s.setAddress(new InetSocketAddress(Integer.parseInt(a.configuration.getProperty("uxpatterns.server.port"))));
		s.setHandler(a.getHandler());
		s.serve();
	}

	public Properties configuration;

	private Supplier<Factory> factory = Lazy.of(() -> {
		var f = new Factory();
		f.setTypes(Util.getPackageClasses(getClass().getPackageName()).toList());
		f.setSource(this);
		return f;
	});

	Supplier<Server.Handler> handler = Lazy.of(() -> {
		var b = getFactory().create(ApplicationHandlerBuilder.class);
		return b.build();
	});

	public UXPatternsApp getApplication() {
		return this;
	}

	public Factory getFactory() {
		return factory.get();
	}

	public Server.Handler getHandler() {
		return handler.get();
	}

	@Handle(method = "GET", path = "/")
	public App getApp() {
		return new App(Util.getPackageClasses(getClass().getPackageName()).map(Class::getSimpleName)
				.filter(x -> x.endsWith("Web")).map(x -> x.substring(0, x.length() - 3)).sorted().map(x -> {
					var y = Util.splitCamelCase(x);
					return new Link(
							URI.create(
									"/" + Arrays.stream(y).map(String::toLowerCase).collect(Collectors.joining("-"))),
							Arrays.stream(y).collect(Collectors.joining(" ")));
				}).toList());
	}

	@Render("App.html")
	public record App(List<Link> links) {
	}

	@Render("App-Link.html")
	public record Link(URI uri, String text) {
	}
}
