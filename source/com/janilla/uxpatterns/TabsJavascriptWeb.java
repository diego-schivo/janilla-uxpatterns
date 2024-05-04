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

import static java.util.Map.entry;

import java.util.List;
import java.util.Map.Entry;

import com.janilla.frontend.RenderEngine;
import com.janilla.frontend.Renderer;
import com.janilla.web.Handle;
import com.janilla.web.Render;

public class TabsJavascriptWeb {

	private static List<Entry<String, String>> tabs = List.of(entry("Tab 1",
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."),
			entry("Tab 2",
					"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."),
			entry("Tab 3",
					"Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."));

	@Handle(method = "GET", path = "/tabs-javascript")
	public @Render("TabsJavascript.html") Object getPage() {
		return new Object();
	}

	@Handle(method = "GET", path = "/tabs-javascript/tab([1-3])")
	public @Render("""
			{}
			""") String getContent(int tab) {
		return tabs.get(tab - 1).getValue();
	}

	@Render("TabsJavascript-Tabs.html")
	public record Tabs(int tab) implements Renderer {

		public List<@Render("TabsJavascript-Tab.html") String> tabs() {
			return tabs.stream().map(Entry::getKey).toList();
		}

		public String text() {
			return tabs.get(tab - 1).getValue();
		}

		@Override
		public boolean evaluate(RenderEngine engine) {
			record A(String[] tabs, int index, Object selectedClass) {
			}
			return engine.match(A.class, (i, o) -> o.setValue(i.index + 1 == tab ? "selected" : null));
		}
	}
}
