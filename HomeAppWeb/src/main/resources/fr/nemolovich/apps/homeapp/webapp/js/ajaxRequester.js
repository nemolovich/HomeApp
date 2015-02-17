/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function request(bean, value) {
	if (!beanExists(bean)) {
		console.error("The bean '" + bean + "' does not exist!");
		return;
	}
	var uuid = generateUUID();
	var url = "/ajax/" + uuid;
	var fun = getAjaxFunction(bean);
	return $.ajax({
		type: 'POST',
		url: url,
		data: {
			bean:	bean,
			uid:	uuid,
			value:	JSON.stringify(value)
		},
		success: function (response) {
			console.log('Success: ' + response);
			try {
				var r = response.replace(/\n/g, '').replace(/\r/g, '')
						.replace(/\/\*{2}.*\*\//, '');
				var resp = JSON.parse(r);
				console.log(resp);
				fun(resp.value);
			} catch (e) {
				console.error("Can not parse result!");
			}
		},
		error: function (xhr, ajaxOptions, thrownError) {
			console.error(xhr.status); // 0
			console.error(ajaxOptions);
			console.error(thrownError);
		}
	});
}

function generateUUID() {
	var d = new Date().getTime();
	var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
		var r = (d + Math.random() * 16) % 16 | 0;
		d = Math.floor(d / 16);
		return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
	});
	return uuid;
}

function getAjaxFunction(bean) {
	/*
	 * TODO: Search in map
	 */
	return log;
}

function log(txt) {
	console.log(txt);
}

function beanExists(bean) {
	/*
	 * TODO: Search in 2 maps
	 */
	return true;
}
