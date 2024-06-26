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
class FileUploadInput {

	selector;

	listen = () => {
		this.selector().querySelector('form').addEventListener('submit', this.handleFormSubmit);
	}

	handleFormSubmit = e => {
		e.preventDefault();
		const f = this.selector();
		const r = new XMLHttpRequest();
		r.upload.addEventListener('loadstart', x => f.querySelector('progress').max = x.total);
		r.upload.addEventListener('progress', x => f.querySelector('progress').value = x.loaded);
		r.addEventListener('load', () => {
			f.querySelector('form').outerHTML = r.responseText;
			this.listen();
		});

		const d = new FormData();
		d.append('file', f.querySelector('input[name="file"]').files[0]);
		r.open('POST', '/file-upload-input/upload', true);
		r.send(d);
	}
}

document.addEventListener('DOMContentLoaded', () => {
	const i = new FileUploadInput();
	i.selector = () => document.querySelector('main');
	i.listen();
});
