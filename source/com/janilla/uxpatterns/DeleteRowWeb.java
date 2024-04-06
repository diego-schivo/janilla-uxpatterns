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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.janilla.frontend.RenderEngine;
import com.janilla.frontend.Renderer;
import com.janilla.reflect.Parameter;
import com.janilla.web.Handle;
import com.janilla.web.Render;

public class DeleteRowWeb {

	private static List<Contact> contacts = Stream.of(new Contact(0, "Joe Smith", "joe@smith.org", true),
			new Contact(1, "Angie MacDowell", "angie@macdowell.org", true),
			new Contact(2, "Fuqua Tarkenton", "fuqua@tarkenton.org", true),
			new Contact(3, "Kim Yee", "kim@yee.org", false)).collect(Collectors.toCollection(ArrayList::new));

	@Handle(method = "GET", path = "/delete-row")
	public Page getPage() {
		return new Page(contacts);
	}

	@Handle(method = "DELETE", path = "/delete-row/contact/(\\d+)")
	public void deleteContact(@Parameter(name = "id") long id) {
		contacts.removeIf(x -> x.id == id);
	}

	public record Contact(long id, String name, String email, boolean active) {
	}

	@Render(template = "DeleteRow.html")
	public record Page(List<@Render(template = "DeleteRow-Row.html") Contact> contacts) implements Renderer {

		@Override
		public boolean evaluate(RenderEngine engine) {
			record A(Contact contact, Object status) {
			}
			return engine.match(A.class, (i, o) -> o.setValue(i.contact.active ? "Active" : "Inactive"));
		}
	}
}
