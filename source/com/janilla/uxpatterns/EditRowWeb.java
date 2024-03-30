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

import com.janilla.http.HttpExchange;
import com.janilla.reflect.Parameter;
import com.janilla.reflect.Reflection;
import com.janilla.web.Handle;
import com.janilla.web.Render;

public class EditRowWeb {

	private static java.util.List<Contact> contacts = java.util.List.of(new Contact(0, "Joe Smith", "joe@smith.org"),
			new Contact(1, "Angie MacDowell", "angie@macdowell.org"),
			new Contact(2, "Fuqua Tarkenton", "fuqua@tarkenton.org"), new Contact(3, "Kim Yee", "kim@yee.org"));

	@Handle(method = "GET", path = "/edit-row/contacts")
	public Object getContacts(HttpExchange exchange) {
		var l = new List(contacts);
		return exchange.getRequest().getHeaders().get("Accept").equals("*/*") ? l : new Page(l);
	}

	@Handle(method = "GET", path = "/edit-row/contact/(\\d+)")
	public @Render(template = "EditRow-Item.html") Contact getContact(@Parameter(name = "id") long id) {
		return contacts.stream().filter(x -> x.id == id).findFirst().orElseThrow();
	}

	@Handle(method = "GET", path = "/edit-row/contact/(\\d+)/edit")
	public @Render(template = "EditRow-ItemEdit.html") Contact editContact(@Parameter(name = "id") long id) {
		return contacts.stream().filter(x -> x.id == id).findFirst().orElseThrow();
	}

	@Handle(method = "PUT", path = "/edit-row/contact/(\\d+)")
	public @Render(template = "EditRow-Item.html") Contact saveContact(long id, Contact contact) {
		var c = contacts.stream().filter(x -> x.id == id).findFirst().orElseThrow();
		Reflection.copy(contact, c, n -> !n.equals("id"));
		return c;
	}

	public static class Contact {

		private long id;

		private String name;

		private String email;

		public Contact() {
		}

		public Contact(long id, String name, String email) {
			super();
			this.id = id;
			this.name = name;
			this.email = email;
		}

		public long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
	}

	@Render(template = "EditRow.html")
	public record Page(List list) {
	}

	@Render(template = "EditRow-List.html")
	public record List(java.util.List<@Render(template = "EditRow-Item.html") Contact> contacts) {
	}
}
