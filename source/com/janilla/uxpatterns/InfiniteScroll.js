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
class InfiniteScroll {

	selector;

	page = 1;

	listen = () => {
		const o = new IntersectionObserver(this.handleIntersect);
		const r = this.selector().querySelector('tbody').lastElementChild;
		o.observe(r);
	}

	handleIntersect = async (entries, observer) => {
		const e = entries[0];
		if (!e.isIntersecting)
			return;
		const r = e.target;
		observer.disconnect();
		const i = this.selector().querySelector('.indicator-div');
		i.style.display = 'block';
		const s = await fetch(`/infinite-scroll/contacts?page=${++this.page}`);
		i.style.display = '';
		r.insertAdjacentHTML('afterend', await s.text());
		this.listen();
	}
}

document.addEventListener('DOMContentLoaded', () => {
	const x = new InfiniteScroll();
	x.selector = () => document.querySelector('main');
	x.listen();
});
