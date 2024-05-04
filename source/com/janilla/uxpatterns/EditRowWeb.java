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

import java.util.List;

import com.janilla.reflect.Parameter;
import com.janilla.reflect.Reflection;
import com.janilla.web.Handle;
import com.janilla.web.Render;

public class EditRowWeb {

	private static List<Contact> contacts = List.of(new Contact(Long.valueOf(1), "Joe", "Smith", "joe@smith.org"),
			new Contact(Long.valueOf(2), "Angie", "MacDowell", "angie@macdowell.org"),
			new Contact(Long.valueOf(3), "Fuqua", "Tarkenton", "fuqua@tarkenton.org"),
			new Contact(Long.valueOf(4), "Kim", "Yee", "kim@yee.org"));

	@Handle(method = "GET", path = "/edit-row")
	public Page getPage() {
		return new Page(contacts);
	}

	@Handle(method = "GET", path = "/edit-row/contact/(\\d+)/edit")
	public @Render("EditRow-Editor.html") Contact editContact(@Parameter(name = "id") long id) {
		return contacts.stream().filter(x -> x.id() == id).findFirst().orElseThrow();
	}

	@Handle(method = "GET", path = "/edit-row/contact/(\\d+)")
	public @Render("EditRow-Row.html") Contact getContact(@Parameter(name = "id") long id) {
		return contacts.stream().filter(x -> x.id() == id).findFirst().orElseThrow();
	}

	@Handle(method = "PUT", path = "/edit-row/contact/(\\d+)")
	public @Render("EditRow-Row.html") Contact saveContact(long id, Contact contact) {
		var c = contacts.stream().filter(x -> x.id() == id).findFirst().orElseThrow();
		Reflection.copy(contact, c, n -> !n.equals("id"));
		return c;
	}

	@Render("EditRow.html")
	public record Page(List<@Render("EditRow-Row.html") Contact> contacts) {
	}
}
