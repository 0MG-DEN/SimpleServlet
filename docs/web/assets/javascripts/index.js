const textForm = document.getElementById('text-form');
const fileForm = document.getElementById('file-form');

const hostInput = document.getElementById('host-input');
const portInput = document.getElementById('port-input');

const callback = function() {
	textForm.action =
	fileForm.action = `http://${hostInput.value}:${portInput.value}/simpleservlet/default`;
};

hostInput.onchange =
portInput.onchange = callback; 

callback();
