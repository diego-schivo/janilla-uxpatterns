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
class ActiveSearch {

	selector;

	timeoutID;

	listen = () => {
		this.selector().querySelector('input[name="search"]').addEventListener('input', this.handleSearchInput);
	}

	handleSearchInput = async () => {
		if (this.timeoutID)
			clearTimeout(this.timeoutID);
		this.timeoutID = setTimeout(this.update, 500);
	}

	update = async () => {
		delete this.timeoutID;
		const e = this.selector();
		const i = e.querySelector('input[name="search"]');
		const j = e.querySelector('.indicator');
		j.classList.add('request');
		const s = await fetch('/active-search/search', {
			method: 'POST',
			body: new URLSearchParams(Object.fromEntries([[i.name, i.value]]))
		});
		j.classList.remove('request');
		e.querySelector('.search-results').innerHTML = await s.text();
	}
}

document.addEventListener('DOMContentLoaded', () => {
	const x = new ActiveSearch();
	x.selector = () => document.querySelector('main');
	x.listen();
});
