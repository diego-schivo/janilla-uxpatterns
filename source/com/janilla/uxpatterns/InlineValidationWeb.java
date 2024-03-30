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

public class InlineValidationWeb {

	@Handle(method = "GET", path = "/inline-validation/contact")
	public Object getForm(HttpExchange exchange) {
		var f = new Form(null);
		return exchange.getRequest().getHeaders().get("Accept").equals("*/*") ? f : new Page(f);
	}

	@Handle(method = "POST", path = "/inline-validation/contact/email")
	public Email validateEmail(Contact contact) throws InterruptedException {
		Thread.sleep(500);
		var e = !contact.email.equals("test@test.com") ? "That email is already taken. Please enter another email."
				: null;
		return new Email(contact, e);
	}

	public static class Contact {

		private String email;

		private String firstName;

		private String lastName;

		public Contact() {
		}

		public Contact(String email, String firstName, String lastName) {
			super();
			this.email = email;
			this.firstName = firstName;
			this.lastName = lastName;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
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
	}

	@Render(template = "InlineValidation.html")
	public record Page(Form form) {
	}

	@Render(template = "InlineValidation-Form.html")
	public record Form(Contact contact) {

		public Email email() {
			return new Email(contact, null);
		}
	}

	@Render(template = "InlineValidation-Email.html")
	public record Email(Contact contact, @Render(template = "InlineValidation-Error.html") String error) {

		public String errorClass() {
			return error != null ? "error" : null;
		}
	}
}
