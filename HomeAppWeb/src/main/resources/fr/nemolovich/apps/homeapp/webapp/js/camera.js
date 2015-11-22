function updateSize(json) {
    var width=json.pi_camera_width;
    var height=json.pi_camera_height;
    $('#pi_camera_width_ajax_input').val(width);
    $('#pi_camera_height_ajax_input').val(height);
    var vlc=$('#vlc_pi_camera');
    vlc.attr('width', width);
    vlc.attr('height', height);
}

function requestSize() {
    request(bean, fields, updateSize);
}