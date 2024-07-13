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

import java.util.concurrent.ThreadLocalRandom;

import com.janilla.web.Handle;
import com.janilla.web.Render;

public class ProgressBarWeb {

	@Handle(method = "GET", path = "/progress-bar")
	public Page getPage(CustomExchange exchange) {
		return new Page(getUI(exchange));
	}

	@Handle(method = "GET", path = "/progress-bar/job")
	public UI getUI(CustomExchange exchange) {
		var j = exchange.getJob(CustomExchange.JobGetOption.REMOVE);
		return new UI(j);
	}

	@Handle(method = "POST", path = "/progress-bar/start")
	public UI startJob(CustomExchange exchange) {
		var j = exchange.getJob(CustomExchange.JobGetOption.CREATE);
		Thread.ofVirtual().start(j);
		return new UI(j);
	}

	@Handle(method = "GET", path = "/progress-bar/job/progress")
	public int getProgress(CustomExchange exchange) {
		var j = exchange.getJob();
		return getPercentage(j);
	}

	private static int getPercentage(Job job) {
		var p = job != null ? job.progress : 0f;
		return (int) Math.round(p * 100);
	}

	@Render("ProgressBar.html")
	public record Page(UI ui) {
	}

	@Render("ProgressBar-UI.html")
	public record UI(Job job) {

		public String title() {
			return job == null ? "Start Progress" : job.progress < 1f ? "Running" : "Complete";
		}

		public String titleAttributes() {
			return job == null ? null : """
					role="status" id="pblabel" tabindex="-1" autofocus""";
		}

		public @Render("ProgressBar-bar.html") Integer bar() {
			return job == null ? null : getPercentage(job);
		}

		public @Render("ProgressBar-button.html") String button() {
			return job == null ? "Start Job" : job.progress < 1f ? null : "Restart Job";
		}
	}

	public static class Job implements Runnable {

		public float progress;

		@Override
		public void run() {
			var r = ThreadLocalRandom.current();
			do {
				try {
					Thread.sleep(r.nextLong(200, 1000));
				} catch (InterruptedException e) {
					break;
				}
				progress = Math.min(progress + r.nextFloat(.05f, .25f), 1f);
			} while (progress < 1f);
		}
	}
}
