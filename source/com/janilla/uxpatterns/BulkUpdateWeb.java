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
import java.util.Set;

import com.janilla.frontend.RenderEngine;
import com.janilla.frontend.RenderParticipant;
import com.janilla.web.Handle;
import com.janilla.web.Bind;
import com.janilla.web.Render;

public class BulkUpdateWeb {

	private static List<User> users = List.of(new User("Joe Smith", "joe@smith.org", true),
			new User("Angie MacDowell", "angie@macdowell.org", true),
			new User("Fuqua Tarkenton", "fuqua@tarkenton.org", true), new User("Kim Yee", "kim@yee.org", false));

	@Handle(method = "GET", path = "/bulk-update")
	public Object getPage() {
		return new Page(users);
	}

	@Handle(method = "POST", path = "/bulk-update/users")
	public Toast postUsers(@Bind("active") Set<String> active) {
		var m = 0;
		var n = 0;
		for (var i = 0; i < users.size(); i++) {
			var u = users.get(i);
			var a = active.contains(u.email());
			if (!u.active()) {
				if (a) {
					users.set(i, new User(u.name(), u.email(), true));
					m++;
				}
			} else {
				if (!a) {
					users.set(i, new User(u.name(), u.email(), false));
					n++;
				}
			}
		}
		return new Toast(m, n);
	}

	@Render("BulkUpdate.html")
	public record Page(List<@Render("BulkUpdate-Row.html") User> users) implements RenderParticipant {

		@Override
		public boolean render(RenderEngine engine) {
			record A(User user, Object checkedAttribute) {
			}
			return engine.match(A.class, (i, o) -> o.setValue(i.user.active() ? "checked" : ""));
		}
	}

	@Render("BulkUpdate-Toast.html")
	public record Toast(int activated, int deactivated) {
	}
}
