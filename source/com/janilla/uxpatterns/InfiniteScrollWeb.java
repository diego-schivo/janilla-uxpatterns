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
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.janilla.web.Handle;
import com.janilla.web.Bind;
import com.janilla.web.Render;

public class InfiniteScrollWeb {

	@Handle(method = "GET", path = "/infinite-scroll")
	public Page getPage() {
		return new Page(getResultPage2(1));
	}

	@Handle(method = "GET", path = "/infinite-scroll/contacts")
	public ResultPage getResultPage(@Bind("page") int page) throws InterruptedException {
		Thread.sleep(1000);
		return getResultPage2(page);
	}

	protected ResultPage getResultPage2(int page) {
		var s = Math.max(page - 1, 0) * 10;
		return new ResultPage(IntStream.range(s, s + 10).mapToObj(i -> {
			var n = "Agent Smith";
			var e = "void" + (i + 10) + "@null.org";
			var j = ThreadLocalRandom.current().ints(15, 0, 17)
					.mapToObj(k -> k < 10 ? String.valueOf(k) : Character.toString('A' + k - 10))
					.collect(Collectors.joining());
			return new Contact(n, e, j);
		}).toList());
	}

	public record Contact(String name, String email, String id) {
	}

	@Render("InfiniteScroll.html")
	public record Page(ResultPage page) {
	}

	@Render("InfiniteScroll-Page.html")
	public record ResultPage(List<@Render("InfiniteScroll-Row.html") Contact> contacts) {
	}
}
