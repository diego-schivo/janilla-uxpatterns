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

import java.time.LocalDateTime;

import com.janilla.http.HttpRequest;
import com.janilla.web.Handle;
import com.janilla.web.Render;

public class AsyncAuthWeb {

	@Handle(method = "GET", path = "/async-auth")
	public @Render("AsyncAuth.html") Object getPage() {
		return "page";
	}

	@Handle(method = "GET", path = "/async-auth/example")
	public @Render("""
			{}
			""") String example(HttpRequest request) {
		if (request.getHeaders().stream()
				.noneMatch(x -> x.name().equals("AUTH") && x.value().equals("foo-bar-baz-qux")))
			throw new RuntimeException();
		return LocalDateTime.now().toString();
	}
}
