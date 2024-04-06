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
class TabsJavascript {

	selector;

	constructor() {
		setTimeout(async () => await this.foo(0), 100);
	}

	listen = () => {
		this.selector().querySelector('#tabs').addEventListener('click', this.handleTabClick);
	}

	handleTabClick = async e => {
		const b = e.target.closest('button');
		const i = [...b.parentElement.children].indexOf(b);
		await this.foo(i);
	}
	
	foo = async i => {
		const s = await fetch(`/tabs-javascript/tab${i + 1}`);
		this.selector().querySelector('#tab-contents').innerHTML = await s.text();
		[...this.selector().querySelector('#tabs').children].forEach((e, j) => {
			e.classList[j === i ? 'add' : 'remove']('selected');
			e.setAttribute('aria-selected', j === i);
		});
	}
}

document.addEventListener('DOMContentLoaded', () => {
	const x = new TabsJavascript();
	x.selector = () => document.querySelector('main');
	x.listen();
});
