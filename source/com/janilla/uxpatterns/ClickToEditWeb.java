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
import com.janilla.web.Handle;
import com.janilla.web.Render;

public class ClickToEditWeb {

	private static Contact contact = new Contact("Joe", "Blow", "joe@blow.com");

	@Handle(method = "GET", path = "/click-to-edit/contact/(\\d+)")
	public Object getContact(long id, HttpExchange exchange) {
		var d = new Details(contact);
		return exchange.getRequest().getHeaders().get("Accept").equals("*/*") ? d : new Page(d);
	}

	@Handle(method = "GET", path = "/click-to-edit/contact/(\\d+)/edit")
	public Form editContact(long id) {
		return new Form(contact);
	}

	@Handle(method = "PUT", path = "/click-to-edit/contact/(\\d+)")
	public Details putContact(Contact contact) {
		ClickToEditWeb.contact = contact;
		return new Details(contact);
	}

	public static class Contact {

		private String firstName;

		private String lastName;

		private String email;

		public Contact() {
		}

		public Contact(String firstName, String lastName, String email) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
	}

	@Render(template = "ClickToEdit.html")
	public record Page(Details details) {
	}

	@Render(template = "ClickToEdit-Details.html")
	public record Details(Contact contact) {
	}

	@Render(template = "ClickToEdit-Form.html")
	public record Form(Contact contact) {
	}
}
