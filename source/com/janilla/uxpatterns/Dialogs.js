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
class Dialogs {

	selector;

	listen = () => {
		this.selector().querySelector('button').addEventListener('click', this.handleButtonClick);
	}

	handleButtonClick = async () => {
		const s = prompt('Enter a string');
		if (s !== null && confirm('Are you sure?')) {
			const t = await fetch('/dialogs/submit', {
				method: 'POST',
				headers: { Prompt: s }
			});
			this.selector().querySelector('.response').innerHTML = await t.text();
		}
	}
}

document.addEventListener('DOMContentLoaded', () => {
	const d = new Dialogs();
	d.selector = () => document.querySelector('main');
	d.listen();
});
