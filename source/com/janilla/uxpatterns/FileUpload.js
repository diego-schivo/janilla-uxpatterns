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
class FileUpload {

	selector;

	listen = () => {
		this.selector().querySelector('form').addEventListener('submit', this.handleFormSubmit);
	}

	handleFormSubmit = e => {
		e.preventDefault();
		const f = this.selector();
		const i = f.querySelector('input[name="file"]');
		const p = f.querySelector('progress');

		const r = new XMLHttpRequest();
		r.upload.addEventListener('loadstart', x => {
			p.value = 0;
			p.max = x.total;
		});
		r.upload.addEventListener('progress', x => p.value = x.loaded);

		const d = new FormData();
		d.append('file', i.files[0]);
		r.open('POST', '/file-upload/upload', true);
		r.send(d);
	}
}

document.addEventListener('DOMContentLoaded', () => {
	const u = new FileUpload();
	u.selector = () => document.querySelector('main');
	u.listen();
});
