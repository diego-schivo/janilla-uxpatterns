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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

import com.janilla.http.HttpRequest;
import com.janilla.io.IO;
import com.janilla.web.Handle;
import com.janilla.web.Render;

public class FileUploadInputWeb {

	@Handle(method = "GET", path = "/file-upload-input")
	public Page getPage() {
		return new Page(new Form());
	}

	@Handle(method = "POST", path = "/file-upload-input/upload")
	public Form upload(HttpRequest request) throws IOException {
		System.out.println(request.getHeaders());
		var c = (ReadableByteChannel) request.getBody();
		var b = ByteBuffer.allocate(1024);
		var l = IO.repeat(i -> {
			b.clear();
			var n = c.read(b);
//			System.out.println(n);
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				throw new RuntimeException(e);
//			}
			return n;
		}, Integer.MAX_VALUE);
		System.out.println(l);
		return new Form();
	}

	@Render("FileUploadInput.html")
	public record Page(Form form) {
	}

	@Render("FileUploadInput-Form.html")
	public record Form() {
	}
}
