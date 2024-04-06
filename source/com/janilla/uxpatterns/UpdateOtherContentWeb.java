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

import com.janilla.web.Handle;
import com.janilla.web.Render;

public class UpdateOtherContentWeb {

	@Handle(method = "GET", path = "/update-other-content")
	public Page getPage() {
		return new Page("form");
	}

	@Handle(method = "POST", path = "/update-other-content/contacts")
	public Add add(Contact contact) {
		return new Add(contact, "form");
	}

	public record Contact(String name, String email) {
	}

	@Render(template = "UpdateOtherContent.html")
	public record Page(@Render(template = "UpdateOtherContent-form.html") Object form) {
	}

	@Render(template = "UpdateOtherContent-Add.html")
	public record Add(@Render(template = "UpdateOtherContent-row.html") Contact row,
			@Render(template = "UpdateOtherContent-form.html") Object form) {
	}
}
