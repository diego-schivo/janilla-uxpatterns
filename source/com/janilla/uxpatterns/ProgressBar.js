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
class ProgressBar {

	selector;

	intervalID;

	listen = () => {
		this.selector().querySelector('#start').addEventListener('click', this.handleStartClick);
	}

	handleStartClick = async () => {
		const s = await fetch('/progress-bar/start', { method: 'POST' });
		this.selector().outerHTML = await s.text();
		this.intervalID = setInterval(this.update, 600);
	}

	update = async () => {
		let s = await fetch('/progress-bar/job/progress');
		const v = await s.json();
		const e = this.selector();
		const p = e.querySelector('#progress');
		p.setAttribute('aria-valuenow', v);
		p.firstElementChild.style.flexBasis = `${v}%`;
		if (v >= 100) {
			clearInterval(this.intervalID);
			s = await fetch('/progress-bar/job');
			e.outerHTML = await s.text();
			this.listen();
		}
	}
}

document.addEventListener('DOMContentLoaded', () => {
	const b = new ProgressBar();
	b.selector = () => document.querySelector('main');
	b.listen();
});
