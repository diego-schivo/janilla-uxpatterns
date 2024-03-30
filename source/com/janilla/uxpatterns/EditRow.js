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
class EditRow {

	selector;

	listen = () => {
		this.selector().querySelectorAll('.edit').forEach(x => x.addEventListener('click', this.handleEditClick));
		this.selector().querySelectorAll('.cancel').forEach(x => x.addEventListener('click', this.handleCancelClick));
		this.selector().querySelectorAll('.save').forEach(x => x.addEventListener('click', this.handleSaveClick));
	}

	handleEditClick = async e => {
		const b = e.currentTarget;
		let r = this.selector().querySelector('.editing');
		if (r) {
			if (confirm('Already Editing. Do you want to cancel that edit and continue?'))
				await this.cancelEdit(r);
			else
				return;
		}
		r = b.closest('tr');
		const s = await fetch(`/edit-row/contact/${b.value}/edit`);
		r.outerHTML = await s.text();
		this.listen();
	}

	handleCancelClick = async e => {
		await this.cancelEdit(e.currentTarget.closest('tr'));
		this.listen();
	}

	handleSaveClick = async e => {
		const b = e.currentTarget;
		const r = b.closest('tr');
		const s = await fetch(`/edit-row/contact/${b.value}`, {
			method: 'PUT',
			headers: { 'Content-Type': 'application/json' },
			body: JSON.stringify(Object.fromEntries([...r.querySelectorAll('input')].map(x => [x.name, x.value])))
		});
		r.outerHTML = await s.text();
		this.listen();
	}

	cancelEdit = async row => {
		const s = await fetch(`/edit-row/contact/${row.querySelector('.cancel').value}`);
		row.outerHTML = await s.text();
	}
}

document.addEventListener('DOMContentLoaded', () => {
	const c = new EditRow();
	c.selector = () => document.body.firstElementChild;
	c.listen();
});
