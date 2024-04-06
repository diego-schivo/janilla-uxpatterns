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
class Animations {

	selector;

	newContent = false;

	constructor() {
		setInterval(this.updateColor, 1000);
		setInterval(this.toggleFaded, 1000);
	}

	listen = () => {
		const e = this.selector();
		e.querySelector('.fade-me-out')?.addEventListener('click', this.handleFadeMeOutClick);
		e.querySelector('.fade-me-in').addEventListener('click', this.handleFadeMeInClick);
		e.querySelector('form')?.addEventListener('submit', this.handleFormSubmit);
		e.querySelector('.slide-it button').addEventListener('click', this.handleSlideItClick);
	}

	updateColor = async () => {
		const s = await fetch('/animations/colors');
		this.selector().querySelector('.color-demo').style.color = await s.json();
	}

	handleFadeMeOutClick = async e => {
		const b = e.currentTarget;
		const s = await fetch('/animations/fade_out_demo', { method: 'DELETE' });
		const t = await s.text();
		b.classList.add('swapping');
		setTimeout(() => b.outerHTML = t, 1000);
	}

	handleFadeMeInClick = async e => {
		let b = e.currentTarget;
		const s = await fetch('/animations/fade_in_demo', { method: 'POST' });
		b.outerHTML = await s.text();
		this.listen();
		b = this.selector().querySelector('.fade-me-in');
		b.classList.add('added');
		setTimeout(() => b.classList.remove('added'), 1000);
	}

	handleFormSubmit = async e => {
		e.preventDefault();
		const f = e.currentTarget;
		f.classList.add('request');
		const s = await fetch('/animations/name', {
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			body: JSON.stringify(Object.fromEntries(new FormData(f)))
		});
		f.outerHTML = await s.text();
	}

	toggleFaded = () => {
		this.selector().querySelector('.demo').classList.toggle('faded');
	}
	
	handleSlideItClick = async e => {
		let b = e.currentTarget;
		this.newContent = !this.newContent;
		const s = await fetch(`/animations/${this.newContent ? 'new' : 'original'}-content`);
		const t = await s.text();
		const u = () => {
			b.closest('div').innerHTML = t;
			this.listen();
		};
		document.startViewTransition ? document.startViewTransition(u) : u();
	}
}

document.addEventListener('DOMContentLoaded', () => {
	const a = new Animations();
	a.selector = () => document.querySelector('main');
	a.listen();
});
