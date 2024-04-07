package com.janilla.uxpatterns;

import com.janilla.web.Error;
import com.janilla.web.Render;

@Error(code = 403, text = "Forbidden")
@Render(template = """
		${message}
		""")
public class MethodBlockedException extends RuntimeException {

	private static final long serialVersionUID = -7277975068794933439L;

	public MethodBlockedException() {
		super("The requested action is disabled on this public server: please set up and run the application locally");
	}
}
