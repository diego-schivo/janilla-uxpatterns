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
class ClickToEdit {

	selector;

	listen = () => {
		const e = this.selector();
		e.querySelector('#edit')?.addEventListener('click', this.handleEditClick);
		e.matches('form') && e.addEventListener('submit', this.handleFormSubmit);
		e.querySelector('#cancel')?.addEventListener('click', this.handleCancelClick);
	}

	handleEditClick = async () => {
		const s = await fetch('/click-to-edit/contact/1/edit');
		const t = await s.text();
		this.selector().outerHTML = t;
		this.listen();
	}

	handleFormSubmit = async e => {
		e.preventDefault();
		const s = await fetch('/click-to-edit/contact/1', {
			method: 'PUT',
			body: new URLSearchParams(new FormData(e.currentTarget))
		});
		const t = await s.text();
		this.selector().outerHTML = t;
		this.listen();
	}

	handleCancelClick = async e => {
		e.preventDefault();
		const s = await fetch('/click-to-edit/contact/1');
		const t = await s.text();
		this.selector().outerHTML = t;
		this.listen();
	}
}

document.addEventListener('DOMContentLoaded', () => {
	const c = new ClickToEdit();
	c.selector = () => document.body.firstElementChild;
	c.listen();
});
