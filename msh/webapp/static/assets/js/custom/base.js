/**
 * Created by tyd on 2016/3/6.
 */
function isEmpty(text) {
    return !text || null == text || "" == text || "null" == text || "undefined" == text;
}

function forward(url) {
    location.href = url;
}

function formForward(url, form) {
    location.href = url + ((url.indexOf("?") != -1) ? "&" : "?") + $(form).serialize();
}

function reload() {
    location.reload();
}

window.msh = {};
