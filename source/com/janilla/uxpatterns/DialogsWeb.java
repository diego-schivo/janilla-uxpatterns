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

import com.janilla.http.HttpHeader;
import com.janilla.http.HttpRequest;
import com.janilla.web.Handle;
import com.janilla.web.Render;

public class DialogsWeb {

	@Handle(method = "GET", path = "/dialogs")
	public Page getPage() {
		return new Page();
	}

	@Handle(method = "POST", path = "/dialogs/submit")
	public @Render("""
			User entered <i>{}</i>
			""") String submit(HttpRequest request) {
		return request.getHeaders().stream().filter(x -> x.name().equals("Prompt")).map(HttpHeader::value).findFirst()
				.orElse(null);
	}

	@Render("Dialogs.html")
	public record Page() {
	}
}
