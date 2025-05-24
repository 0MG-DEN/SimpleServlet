const dumpForm = document.forms.dump;
dumpForm.onsubmit = function() {
	const get = function(url) {
		let xhr = new XMLHttpRequest();
		xhr.open('GET', url, false);
		xhr.send();
		return xhr.response;
	}
	const url = dumpForm.elements['url'];
	const html = dumpForm.elements['html'];
	html.value = (html.value == "") ? get(url.value) : html.value;
	this.submit();
};
