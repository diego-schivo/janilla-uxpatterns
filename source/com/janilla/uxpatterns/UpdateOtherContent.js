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
class UpdateOtherContent {

	selector;

	listen = () => {
		this.selector().querySelector('form').addEventListener('submit', this.handleFormSubmit);
	}
	
	handleFormSubmit = async e => {
		e.preventDefault();
		const f = e.currentTarget;
		const s = await fetch('/update-other-content/contacts', {
			method: 'POST',
			body: new URLSearchParams(new FormData(f))
		});
		const p = new DOMParser();
		const d = p.parseFromString(await s.text(), 'text/html');
		this.selector().querySelector('#contacts-table').insertAdjacentElement('beforeend', d.querySelector('tr'));
		f.replaceWith(d.querySelector('form'));
		this.listen();
	}
}

document.addEventListener('DOMContentLoaded', () => {
	const x = new UpdateOtherContent();
	x.selector = () => document.querySelector('main');
	x.listen();
});
