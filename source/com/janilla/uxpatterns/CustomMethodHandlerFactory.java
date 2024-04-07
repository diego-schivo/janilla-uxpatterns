package com.janilla.uxpatterns;

import java.io.IOException;
import java.util.Properties;

import com.janilla.http.HttpExchange;
import com.janilla.web.HandleException;
import com.janilla.web.MethodHandlerFactory;
import com.janilla.web.MethodInvocation;

public class CustomMethodHandlerFactory extends MethodHandlerFactory {

	Properties configuration;

	public void setConfiguration(Properties configuration) {
		this.configuration = configuration;
	}

	@Override
	protected void handle(MethodInvocation invocation, HttpExchange exchange) throws IOException {
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
