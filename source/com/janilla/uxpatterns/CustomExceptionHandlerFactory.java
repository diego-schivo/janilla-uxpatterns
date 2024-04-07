package com.janilla.uxpatterns;

import java.io.IOException;

import com.janilla.frontend.RenderEngine.Entry;
import com.janilla.http.HttpExchange;
import com.janilla.web.Error;
import com.janilla.web.ExceptionHandlerFactory;
import com.janilla.web.HandlerFactory;

public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {

	protected HandlerFactory mainFactory;

	public void setMainFactory(HandlerFactory mainFactory) {
		this.mainFactory = mainFactory;
	}

	@Override
	protected void handle(Error error, HttpExchange exchange) throws IOException {
		super.handle(error, exchange);

		if (exchange.getException() instanceof MethodBlockedException e) {
			var o = new Entry(null, e, null);
			mainFactory.createHandler(o, exchange).accept(exchange);
		}
	}
}
