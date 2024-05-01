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

public class AnimationsWeb {

	private static String[] colors = { "red", "blue", "green", "orange" };

	@Handle(method = "GET", path = "/animations")
	public Page getPage() {
		return new Page(getColor(), fadeIn(), getContent("original"));
	}

	@Handle(method = "GET", path = "/animations/colors")
	public String getColor() {
		return colors[(int) ((System.currentTimeMillis() / 1000) % 4)];
	}

	@Handle(method = "DELETE", path = "/animations/fade_out_demo")
	public void fadeOut() {
	}

	@Handle(method = "POST", path = "/animations/fade_in_demo")
	public FadeIn fadeIn() {
		return new FadeIn();
	}

	@Handle(method = "GET", path = "/animations/([a-z]+)-content")
	public Content getContent(String kind) {
		return switch (kind) {
		case "original" -> new Content("Initial Content", "Swap It!");
		case "new" -> new Content("New Content", "Restore It!");
		default -> throw new RuntimeException();
		};
	}

	@Handle(method = "POST", path = "/animations/name")
	public @Render(template = """
			{}
			""") String setName() throws InterruptedException {
		Thread.sleep(500);
		return "Submitted!";
	}

	@Render(template = "Animations.html")
	public record Page(String color, FadeIn fadeIn, Content content) {
	}

	@Render(template = "Animations-FadeIn.html")
	public record FadeIn() {
	}

	@Render(template = "Animations-Content.html")
	public record Content(String title, String text) {
	}
}
