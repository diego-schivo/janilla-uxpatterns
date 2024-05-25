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

import java.util.Properties;

import com.janilla.http.HttpExchange;
import com.janilla.web.HandleException;
import com.janilla.web.MethodHandlerFactory;
import com.janilla.web.MethodInvocation;

public class CustomMethodHandlerFactory extends MethodHandlerFactory {

	public Properties configuration;

	@Override
	protected void handle(MethodInvocation invocation, HttpExchange exchange) {
		if (Boolean.parseBoolean(configuration.getProperty("uxpatterns.live-demo"))) {
			var q = exchange.getRequest();
			switch (q.getMethod().name()) {
			case "GET":
				break;
			default:
				switch (q.getURI().getPath()) {
				case "/active-search/search", "/bulk-update/users":
					break;
				default:
					if (!q.getURI().getPath().startsWith("/animations/"))
						throw new HandleException(new MethodBlockedException());
				}
			}
		}
		super.handle(invocation, exchange);
	}
}
