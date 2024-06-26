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
class ModalCustom {

	selector;

	listen = () => {
		this.selector().querySelector('#openButton').addEventListener('click', this.handleOpenClick);
	}

	handleOpenClick = async () => {
		const s = await fetch('/modal-custom/modal');
		document.body.insertAdjacentHTML('beforeend', await s.text());
		const m = document.body.lastElementChild;
		m.addEventListener('closeModal', this.handleModalClose);
		m.querySelector('.modal-underlay').addEventListener('click', this.handleUnderlayClick);
		m.querySelector('#closeButton').addEventListener('click', this.handleCloseClick);
	}

	handleUnderlayClick = e => {
		e.currentTarget.dispatchEvent(new CustomEvent('closeModal', { bubbles: true }));
	}

	handleCloseClick = e => {
		e.currentTarget.dispatchEvent(new CustomEvent('closeModal', { bubbles: true }));
	}

	handleModalClose = e => {
		const m = e.currentTarget.closest('#modal');
		m.classList.add('closing');
		m.addEventListener('animationend', () => m.remove());
	}
}

document.addEventListener('DOMContentLoaded', () => {
	const x = new ModalCustom();
	x.selector = () => document.querySelector('main');
	x.listen();
});
