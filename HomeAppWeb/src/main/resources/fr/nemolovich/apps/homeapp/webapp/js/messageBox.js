var MESSAGE_BOX_ID = 0;
var DEFAULT_HIDDING_TIME = 2000;
var DIALOG_BACKGROUND;
var DIALOG_FROM = null;

function hideMessage(id) {
	hideMessage(id, DEFAULT_HIDDING_TIME);
}

function hideMessage(id, time) {
	var box = $('#' + id);
	box.mouseenter(function () {
		showMessage(id, -1);
	});
	box.mouseleave(function () {
		hideMessage(id, DEFAULT_HIDDING_TIME);
	});
	box.clearQueue();
	box.stop();
	var duration = 3000;
	box.delay(time).animate({
		"opacity": "0"
	}, duration, function () {
		box.css('display', 'none');
		box.remove();
	});
}

function killMessage(id) {
	var box = $('#' + id);
	box.unbind('mouseenter mouseleave');
	box.clearQueue();
	box.stop();
	box.css('min-height', '0px');
	var close = box.find('.title');
	close.css('display', 'none');
	box.slideUp(500, function () {
		$(this).remove();
	});
}

function showMessage(id, time) {
	var box = $('#' + id);
	box.clearQueue();
	box.stop();
	box.animate({
		"opacity": "1"
	}, 100, function () {
		box.css('display', 'block');
	});
	box.delay(50).queue(function () {
		box.css('opacity', '1');
		box.css('display', 'block');
	});
	if (time >= 1) {
		hideMessage(id, time);
	}
}

function addMessage(title, msg, style) {
	var boxContainer = $('#msgBoxContainer');
	var id = 'msgBox' + (MESSAGE_BOX_ID++);
	var classType = "";
	if (style !== undefined) {
		classType = " " + style
	}
	var box = $('<div class="msgBox' + classType + '" id="' + id + '"></div>');
	var div = $('<div class="icon-close" title="Close message"></div>');
	div.click(function () {
		killMessage(id);
	});
	box.append(div);
	div = $('<div class="title">' + title + '</div>');
	box.append(div);
	div = $('<div>' + msg + '</div>');
	box.append(div);
	boxContainer.append(box);
	showMessage(id, 20000);
}

//$(document).ready(function() {
//	$('.msgBox')
//});

function openDialog(dialogID, modal) {
	var dialog = $('#' + dialogID);
	if (modal) {
		DIALOG_BACKGROUND.stop();
		DIALOG_BACKGROUND.clearQueue();
		DIALOG_BACKGROUND.fadeIn(500, 'swing');
	}
	dialog.stop();
	dialog.clearQueue();
	dialog.show(1000);
}

function closeDialog(dialogID) {
	var dialog = $('#' + dialogID);
	DIALOG_BACKGROUND.stop();
	dialog.stop();
	DIALOG_BACKGROUND.clearQueue();
	dialog.clearQueue();
	DIALOG_BACKGROUND.fadeOut(500, 'swing');
	dialog.hide(1000);
}

function addDialogTitle(dialogID, dialogTitle) {
	var dialog = $('#' + dialogID);
	var titleDiv = '<div class="title" title="Move window">' + dialogTitle +
			'<span class="icon-close" title="Close window" onclick="javascript:closeDialog(\'' +
			dialogID + '\');"/></div>';
	dialog.prepend(titleDiv);
}

function dialogDrag(dialog, event) {
	DIALOG_FROM = {x: event.clientX, y: event.clientY};
	dialog.addClass('ui-moving-object');
}

function dialogMove(dialog, event) {
	if (DIALOG_FROM) {
		dialog.css('top', dialog.offset().top - (DIALOG_FROM.y - event.clientY));
		dialog.css('left', dialog.offset().left - (DIALOG_FROM.x - event.clientX));
		DIALOG_FROM = {x: event.clientX, y: event.clientY};
	}
}

function dialogDrop(dialog, event) {
	if (DIALOG_FROM) {
		dialog.css('top', dialog.offset().top - (DIALOG_FROM.y - event.clientY));
		dialog.css('left', dialog.offset().left - (DIALOG_FROM.x - event.clientX));
		DIALOG_FROM = {x: event.clientX, y: event.clientY};
	}
	dialog.removeClass('ui-moving-object');
	DIALOG_FROM = null;
}

$(function () {
	DIALOG_BACKGROUND = $('<div id="modal-background"></div>');
	$('body').prepend(DIALOG_BACKGROUND);
	$('body').append('<div id="msgBoxContainer"></div>');

	$("#opener").click(function () {
		openDialog('dialog1');
	});

	$("#closer").click(function () {
		closeDialog('dialog1');
	});

	$('.dialog').each(function () {
		var elm = $(this);
		var title = elm.attr('title');
		elm.removeAttr('title');
		addDialogTitle(elm.attr('id'), title);
		elm.remove();
		$('body').prepend(elm);
		elm.find('.title').mousedown(function (event) {
			dialogDrag(elm, event);
		});
		elm.mousemove(function (event) {
			dialogMove(elm, event);
		});
		elm.mouseup(function (event) {
			dialogDrop(elm, event);
		});
	});
});