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
import java.util.Map;

import com.janilla.web.Bind;
import com.janilla.web.Handle;
import com.janilla.web.Render;

public class ValueSelectWeb {

	private List<Map.Entry<String, String>> makes = List.of(Map.entry("audi", "Audi"), Map.entry("toyota", "Toyota"),
			Map.entry("bmw", "BMW"));

	private Map<String, List<Map.Entry<String, String>>> models = Map.of("audi",
			List.of(Map.entry("a1", "A1"), Map.entry("a4", "A4"), Map.entry("a6", "A6")), "toyota",
			List.of(Map.entry("Landcruiser", "Landcruiser"), Map.entry("Tacoma", "Tacoma"),
					Map.entry("Yaris", "Yaris")),
			"bmw", List.of(Map.entry("325i", "325i"), Map.entry("325ix", "325ix"), Map.entry("X5", "X5")));

	@Handle(method = "GET", path = "/value-select")
	public Page getPage() {
		return new Page(new OptionList(makes), new OptionList(models.get(makes.get(0).getKey())));
	}

	@Handle(method = "GET", path = "/value-select/models")
	public OptionList getModels(@Bind("make") String make) throws InterruptedException {
		Thread.sleep(500);
		return new OptionList(models.get(make));
	}

	@Render("ValueSelect.html")
	public record Page(OptionList makes, OptionList models) {
	}

	@Render("ValueSelect-OptionList.html")
	public record OptionList(List<Map.@Render("ValueSelect-Entry.html") Entry<String, String>> entries) {
	}
}
