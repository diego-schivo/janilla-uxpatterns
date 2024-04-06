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
let authToken;
const auth = () => new Promise(resolve => {
	switch (authToken) {
		case undefined:
			authToken = null;
			setTimeout(() => {
				authToken = 'foo-bar-baz-qux';
				resolve();
				dispatchEvent(new CustomEvent('auth'));
			}, 3000);
			break;
		case null:
			addEventListener('auth', resolve, { once: true });
			break;
		default:
			resolve();
			break;
	}
});

auth().then(() => { });

class AsyncAuth {

	selector;

	listen = () => {
		this.selector().querySelector('button').addEventListener('click', this.handleButtonClick);
	}

	handleButtonClick = async () => {
		await auth();
		const s = await fetch('/async-auth/example', { headers: { 'AUTH': authToken } });
		this.selector().querySelector('output').innerHTML = await s.text();
	}
}

document.addEventListener('DOMContentLoaded', () => {
	const x = new AsyncAuth();
	x.selector = () => document.querySelector('main');
	x.listen();
});
