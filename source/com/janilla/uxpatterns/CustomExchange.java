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

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.janilla.http.HeaderField;
import com.janilla.http.Http;
import com.janilla.http.HttpCookie;
import com.janilla.http.HttpExchange;

public class CustomExchange extends HttpExchange {

	private static Map<UUID, ProgressBarWeb.Job> jobs = new ConcurrentHashMap<>();

	public ProgressBarWeb.Job getJob(JobGetOption... options) {
		var oo = Set.of(options);
		var hh = getRequest().getHeaders();
		var h = hh != null
				? hh.stream().filter(x -> x.name().equals("Cookie")).map(HeaderField::value).findFirst().orElse(null)
				: null;
		var cc = h != null ? Http.parseCookieHeader(h) : null;
		var s = cc != null ? cc.get("job") : null;
		var u = s != null ? UUID.fromString(s) : null;
		var j = u != null ? jobs.get(u) : null;
		if (j != null) {
			if (j.progress >= 1.0 && oo.contains(JobGetOption.REMOVE)) {
				jobs.remove(u);
				getResponse().getHeaders()
						.add(new HeaderField("set-cookie",
								HttpCookie.of("job", null)
										.withExpires(ZonedDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC))
										.withPath("/").withSameSite("strict").format()));
			}
		} else if (oo.contains(JobGetOption.CREATE)) {
			u = UUID.randomUUID();
			j = new ProgressBarWeb.Job();
			jobs.put(u, j);
			getResponse().getHeaders().add(new HeaderField("set-cookie",
					HttpCookie.of("job", u.toString()).withPath("/").withSameSite("strict").format()));
		}
		return j;
	}

	public enum JobGetOption {

		CREATE, REMOVE;
	}
}
