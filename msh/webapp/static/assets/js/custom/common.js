// public function
$(function () {
    msh.util.autoCompleteOff();
    msh.util.listOperation();
    msh.util.bindMenuToggleEvent();
    $.ajaxSetup({ cache: true });
});

function changeStatusReload(tips, status, name) {
    var selectedValues = msh.util.getCheckedValue(name);
    if (selectedValues.length == 0) {
        return;
    }
    msh.util.confirm('你确定要' + tips + '吗？', {ok: function(){
        msh.ajax.doAjax({
            type: 'post',
            url: 'status',
            data: {multi:true, ids: selectedValues, status:status},
            success: function (result) {
                if (resultSuccess(result)) {
                    reload();
                }
            }
        }, tips);
    }});
}

function deleteReload(name) {
    var selectedValues = msh.util.getCheckedValue(name);
    if (selectedValues.length == 0) {
        return;
    }
    msh.util.confirm('你确定要删除选中项吗？', {ok: function(){
        msh.ajax.doAjax({
            type: 'post',
            url: 'delete',
            data: {ids: selectedValues},
            success: function (result) {
                if (resultSuccess(result)) {
                    reload();
                }
            }
        });
    }});
}

function formSubmit(form) {
    $(form).submit();
}

function generateQRCode(title, url, options) {
    if (url.indexOf('/') == 0) {
        url = msh.hostName + url;
    }
    var _qrCodeImageUrl = '/common/qrCode/download?download=false&size=350&code=' + encodeURI(url);
    var message = '<div style="text-align: center;">' +
        '<img style="display: inline-block;" src="' + _qrCodeImageUrl + '" width="350" height="350" /><br>' +
        '<p class="link-2dCode">' +
        '<b>链接地址：</b>' + url + '</p>' +
        '</div>';
    var setting = $.extend({}, {width: 390, height: 460}, options);
    openDialog(title, message, null, setting.width, setting.height, true);
}

function openWindow(url) {
    window.open(url);
}

function changeStatusDoSearch(select) {
    forward('list?status=' + $(select).val());
}

function bindDatePicker() {
    $(".datePicker").datepicker({
        changeMonth: true,
        changeYear: true,
        showButtonPanel: true
    });
}

function bindDateOfBirthPicker() {
    $(".dateOfBirthPicker").datepicker({
        yearRange: '1900:',
        maxDate: new Date(),
        changeMonth: true,
        changeYear: true,
        showButtonPanel: true
    });
}

function bindDateFromToPicker(from, to, dateLimit) {
    var startTime = $('#' + from);
    var endTime = $('#' + to);
    var _options = {
        changeMonth: true,
        changeYear: true,
        showButtonPanel: true,
        dateLimit: dateLimit
    };
    $.timepicker.dateRange(startTime, endTime, _options);
}

function bindDatetimePicker() {
    $(".dateTimePicker").datetimepicker();
}

function bindTimeFromToPicker(from, to) {
    $.timepicker.timeRange($('#' + from), $('#' + to), {});
}

function memberChangePassword() {
    var content = $('.m_change_password');
    content.hide();
    $('#m_change_password_btn').bind('click', function () {
        if (content.css('display') == 'none') {
            content.show();
        } else {
            content.hide();
        }
    });
}

function openDialog(title, content, loadUrl, width, height, modal) {
    initDialog('ju_dialog', title, content, loadUrl, width, height, modal);
}

function initDialog(id, title, content, loadUrl, width, height, modal) {
    var $dialog = initDialogById(id, title, width, height, modal);
    msh.util._dialogContent(id, $dialog, content, loadUrl);
}

function openAjaxDialog(title, loadUrl, width, height, modal) {
    openDialog(title, null, loadUrl, width, height, modal);
}

function initDialogById(id, title, width, height, modal) {
    return msh.util._dialog(id, {title: title, width: width, height: height, modal: modal});
}

function closeDialogById(id) {
    var dialog = $("#" + id);
    var backdrop = $('.modal-backdrop');
    dialog.removeClass('in');
    setTimeout(function () {
        dialog.hide();
        if (!$('.modal:visible').length) {
            $(document.body).removeClass('modal-open');
            backdrop.removeClass('in');
            backdrop.remove();
        }
    }, 300);
}

function closeDialog() {
    closeDialogById('ju_dialog');
}

function renderDialogTo(id, html) {
    var target = $("#" + id);
    var modal = target.find('.modal-body-content');
    if (modal.length) {
        modal.html(html);
    } else {
        target.html(html);
    }
    msh.util.autoCompleteOff();
}

function renderDialog(html) {
    renderDialogTo('ju_dialog', html);
}

function resultSuccess(result) {
    return (typeof(result) == 'object') || ('' == result);
}

function dialogSubmitReload(form) {
    msh.form.ajaxSubmit(form, function(result){
        if (resultSuccess(result)) {
            closeDialog();
            location.reload();
        } else {
            renderDialog(result);
        }
    });
}

function dialogSubmit(form, fun, tips) {
    msh.form.ajaxSubmit(form, function(result){
        if (resultSuccess(result)) {
            fun(result);
        } else {
            renderDialog(result);
        }
    }, tips);
}

function submitWithAjax(form, tips) {
    msh.form.ajaxSubmit(form, function(result){
        if (result.success) {
            msh.util.tip(tips ? tips : '保存成功', function(){
                var redirectUrl = result.redirectUrl;
                if (redirectUrl && !isEmpty(redirectUrl)) {
                    forward(redirectUrl);
                }
            });
        } else {
            ajaxErrorHandler(result);
        }
    });
}

function searchByPageNumber(form, currentPage, url, targetId) {
    var thisForm = $(form);
    url = isEmpty(url) ? thisForm.attr('action') : url;
    var searchUrl = url + ((url.indexOf("?") != -1) ? "&" : "?") + "currentPage=" + currentPage;
    if (targetId) {
        $.ajax({
            type: 'get',
            url: searchUrl,
            data: thisForm.serialize(),
            success: function(result){
                renderDialogTo(targetId, result);
            },
            error: function(x, s, e){
                msh.ajax.defaults.error(x, s, e);
            }
        })
    } else {
        forward(searchUrl + '&' + thisForm.serialize());
    }
}

function search(form) {
    var thisForm = $(form);
    var searchUrl = thisForm.attr('action');
    forward(searchUrl + '?' + thisForm.serialize());
}

function tabsSearch(form) {
    var thisForm = $(form);
    var searchUrl = thisForm.attr('action');
    msh.util.loadCurrentTabs(searchUrl + '?' + thisForm.serialize());
}

function searchByInputPageNumber(form, goToPage, currentPage, pageCount, url, targetId) {
    var reg = /^[1-9]\d*$/;
    var pageNumber = goToPage;
    if (!reg.test(goToPage)) {
        pageNumber = currentPage;
    } else {
        if (goToPage < 1 || goToPage > pageCount) {
            pageNumber = currentPage;
        }
    }
    searchByPageNumber(form, pageNumber, url, targetId);
}

function toggleMore(e) {
    var $toggleElem = $(e);
    var currentValue = $toggleElem.html();
    var toggleValue = $toggleElem.attr('data-toggle-value');
    var toggleSelector = $toggleElem.attr('data-toggle-selector');
    var $target = $(toggleSelector);
    if ($target.hasClass('hidden')) {
        $target.removeClass('hidden');
    } else {
        $target.addClass('hidden');
    }
    $toggleElem.attr({'data-toggle-value': currentValue});
    $toggleElem.html(toggleValue);
}

function changeStatus(id, status, operate, urlPrefix, tab) {
    if (tab && 'true' == tab) {
        msh.util.tabsChangeStatus(id, status, operate, urlPrefix)
    } else {
        msh.util.confirm("你确定要" + operate + "吗？", {ok: function(){
            afterAjaxReload((isEmpty(urlPrefix) ? '' : urlPrefix) + 'status?id=' + id + '&status=' + status);
        }});
    }
}

function changeStatusCallback(id, status, tips, urlPrefix, callback) {
    msh.util.confirm("你确定要" + tips + "吗？", {ok: function(){
        var url = (isEmpty(urlPrefix) ? '' : urlPrefix) + 'status?id=' + id + '&status=' + status;
        ajaxRequest(url, function(result){
            callback(result);
        });
    }});
}

function deleteRecord(id) {
    msh.util.confirm("你确定要删除这条记录吗？", {ok: function(){
        afterAjaxReload('delete?id=' + id);
    }});
}

function afterAjaxReload(url) {
    ajaxRequest(url, function(){reload();});
}

function ajaxRequest(url, fun) {
    msh.ajax.doAjax({
        type: 'get',
        url: url,
        success: function(result){
            if (typeof fun == 'function') fun(result);
        }
    });
}

/**
 * 上传单张图片
 * @param field
 */
function pictureUploader(field) {
    var fieldItem = $('#' + field);
    if (fieldItem.length == 0) return;

    var oKey = $('#' + field + '_key').val();
    if (!isEmpty(oKey)) {
        showPicture(field, (oKey.indexOf('/') != -1) ? oKey : ('/common/images/' + oKey));
    }

    fieldItem.uploadify({
        swf: '/js/jquery/uploadify/uploadify.swf',
        uploader: '/common/file/upload?fieldName=' + field + '&mode=1&dataType=json&JSESSIONID=' + msh.sessionId,
        fileObjName: field,
        buttonText: '选择图片',
        fileTypeDesc: '图片文件',
        fileTypeExts: '*.png; *.jpg; *.gif; *.bmp',
        fileSizeLimit: '512KB',
        onUploadStart: function() {
            var imageShow = $('#' + field + '_imageShow');
            imageShow.empty();
            imageShow.addClass('file-loading');
        },
        onUploadSuccess: function (file, data, response) {
            $('#' + field + '_imageShow').removeClass('file-loading');
            if (response) {
                handleUploadResult(field, data);
            }
        }
    });
}

/**
 * Excel上传
 * @param field
 * @param eid
 * @param rid
 */
function excelUploader(field, eid, rid) {
    var fieldItem = $('#' + field);
    if (fieldItem.length == 0) return;
    var uploadResult = $('.upload-result');
    uploadResult.attr({'class': 'upload-result'});
    fieldItem.uploadify({
        swf: '/js/jquery/uploadify/uploadify.swf',
        uploader: '/common/file/upload?fieldName=' + field + '&mode=3&dataType=json&enterpriseId=' + eid + '&restaurantId=' + rid + '&JSESSIONID=' + msh.sessionId,
        fileObjName: field,
        buttonText: '上传Excel',
        fileTypeDesc: 'Excel文件',
        fileTypeExts: '*.xls',
        successTimeout: 999,
        onUploadStart: function() {
            uploadResult.html('<img src="/images/loading.gif"/> 正在上传');
        },
        onUploadSuccess: function (file, data, response) {
            if (response) {
                uploadResult.html('');
                var result = $.parseJSON(data);
                var errors = result.errors;
                if (result.success) {
                    var tip = '';
                    for (var i in errors) {
                        var error = errors[i];
                        tip += '<p>' + error.message + '</p>';
                    }
                    if (tip != '' && tip != null) {
                        msh.util.confirm(tip, { ok: function () {
                            closeDialog();
                            msh.util.tip('导入成功！', function () {
                                reload();
                            });
                        }});
                    } else {
                        closeDialog();
                        msh.util.tip('导入成功！', function () {
                            reload();
                        });
                    }
                } else {
                    var displayHtml = '<ul>';
                    for (var i in errors) {
                        var error = errors[i];
                        displayHtml += '<li>' + error.message + '</li>';
                    }
                    displayHtml += '</ul>';
                    uploadResult.html(displayHtml);
                    if (errors.length > 0) {
                        uploadResult.addClass('alert alert-danger');
                    }
                }
            } else {
                uploadResult.html('上传失败');
            }
        }
    });
}

function templateDownload(filename) {
    forward('/common/download_template?filename=' + filename);
}

function excelUploadDialog(title, field, templateName, eid, rid) {
    var content = '<html><head><link rel="stylesheet" href="/js/jquery/uploadify/uploadify.css" media="screen">' +
        '<script type="text/javascript" src="/js/jquery/uploadify/jquery.uploadify.js"></script></head>' +
        '<body>' +
        '<div class="mt10"><form class="form-horizontal">' +
        '<div class="form-group">' +
        '<input type="file" id="' + field + '"/>' +
        '</div>' +
        '<div class="form-group">' +
        '<p class="upload-status tips help-block">请先下载模板，填写后上传 <a class="link" href="javascript:void(0)" onclick="templateDownload(\'' + templateName + '\');">下载模板</a></p>' +
        '<div class="upload-result"></div>' +
        '</div>' +
        '</form></div>' +
        '</body></html>';
    openDialog(title, content, null, 500, 180, true);
    excelUploader(field, eid, rid);
}

/**
 * 批量上传图片
 * @param field
 * @param preFun 上传前处理函数
 * @param del 要删除的元素
 * @param success 上传成功回调函数
 * @param size 文件大小
 */
function multiPictureUploader(field, preFun, del, success, size) {
    var fieldItem = $('#' + field);
    if (fieldItem.length == 0) return;

    fieldItem.uploadify({
        swf: '/js/jquery/uploadify/uploadify.swf',
        uploader: '/common/file/upload?fieldName=' + field + '&mode=1&dataType=json&JSESSIONID=' + msh.sessionId,
        fileObjName: field,
        buttonText: '上传图片',
        fileTypeDesc: '图片文件',
        fileTypeExts: '*.png; *.jpg',
        fileSizeLimit: size || '512KB',
        multi: true,
        onUploadStart: function(){
            preFun();
        },
        onUploadSuccess: function (file, data, response) {
            var result = $.parseJSON(data);
            var pic = $('.pic.loading');
            if (response) {
                if (pic.length > 0) {
                    pic.attr({key: result.key});
                    pic.css({background: 'url("/common/images/' + result.key + '") 50% 50% / cover no-repeat'});
                    pic.append('<span class="remove" onclick="removePicture(this, \'' + del + '\', \'' + field + '\');"><img src="/images/icons/deactivate.png"/></span>');
                    pic.removeClass('loading');
                    pic.parent().addClass('uploaded');
                    if (typeof success == 'function') {
                        success(result, pic);
                    }
                }
            } else {
                if (pic.length > 0) {
                    pic.remove();
                }
            }
        }
    });
}

function updatePictureFieldValue(field) {
    var keys = [];
    $('.' + field + '-box').find('.pic-item').each(function () {
        var key = $(this).find('.pic').attr('key');
        keys.push('\'' + key + '\'');
    });
    $('#' + field + '-data').val('[' + keys.join(',') + ']');
}

/**
 * 批量上传图片-删除
 * @param e 事件元素
 * @param del 要删除的元素
 * @param field x
 */
function removePicture(e, del, field) {
    if ($('.pic-item').length > 1) {
        $(e).closest(del).remove();
    }
    if (field) {
        updatePictureFieldValue(field);
    }
}

/**
 * 上传MP3
 * @param field
 * @param size Ex:1MB, 1KB etc.
 */
function mp3Uploader(field, size) {
    var fieldItem = $('#' + field);
    if (fieldItem.length == 0) return;

    var oKey = $('#' + field + '_key').val();
    if (!isEmpty(oKey)) {
        initAudioPlayer(field, oKey);
    }
    fieldItem.uploadify({
        swf: '/js/jquery/uploadify/uploadify.swf',
        uploader: '/common/file/upload?fieldName=' + field + '&mode=2&dataType=json&JSESSIONID=' + msh.sessionId,
        fileObjName: field,
        buttonText: '上传MP3',
        fileTypeDesc: 'MP3文件',
        fileTypeExts: '*.mp3',
        successTimeout: 999,
        fileSizeLimit: size,
        onUploadStart: function() {
            var musicShow = $('#' + field + '_musicShow');
            musicShow.empty();
            musicShow.addClass('file-loading');
        },
        onUploadSuccess: function (file, result, response) {
            $('#' + field + '_musicShow').removeClass('file-loading');
            if (response) {
                result = $.parseJSON(result);
                if (result.success) {
                    var key = result.key;
                    if (!isEmpty(key)) {
                        initAudioPlayer(field, key);
                    }
                } else {
                    msh.util.tip(result.result.label);
                }
            }
        },
        onUploadError: function() {
            $('#' + field + '_musicShow').removeClass('file-loading');
        }
    });
}

/**
 * 上传应用
 * @param field
 * @param callback Function
 */
function appUploader(field, callback) {
    var fieldItem = $('#' + field);
    if (fieldItem.length == 0) return;

    fieldItem.uploadify({
        swf: '/js/jquery/uploadify/uploadify.swf',
        uploader: '/common/file/upload?fieldName=' + field + '&mode=4&dataType=json&JSESSIONID=' + msh.sessionId,
        fileObjName: field,
        buttonText: '上传应用',
        fileTypeDesc: 'APK,EXE',
        fileTypeExts: '*.apk; *.exe',
        successTimeout: 999,
        onUploadStart: function() {
            var upload = $('#' + field + '_upload');
            upload.empty();
            upload.addClass('file-loading');
        },
        onUploadSuccess: function (file, result, response) {
            $('#' + field + '_upload').removeClass('file-loading');
            if (response) {
                result = $.parseJSON(result);
                if (result.success) {
                    callback(result);
                } else {
                    msh.util.tip(result.result.label);
                }
            }
        },
        onUploadError: function() {
            $('#' + field + '_upload').removeClass('file-loading');
        }
    });
}

/**
 * 上传文件 返回Base64
 * @param field
 * @param callback Function
 */
function base64Handler(field, callback) {
    var fieldItem = $('#' + field);
    if (fieldItem.length == 0) return;

    var base64 = $('#' + field + '_base64').val();
    if (!isEmpty(base64)) {
        base64Download(field);
    }

    fieldItem.uploadify({
        swf: '/js/jquery/uploadify/uploadify.swf',
        uploader: '/common/file/upload?fieldName=' + field + '&mode=5&dataType=json&JSESSIONID=' + msh.sessionId,
        buttonText: '上传文件',
        fileObjName: field,
        successTimeout: 999,
        onUploadStart: function() {
            var upload = $('#' + field + '_upload');
            upload.empty();
            upload.addClass('file-loading');
        },
        onUploadSuccess: function (file, result, response) {
            $('#' + field + '_upload').removeClass('file-loading');
            if (response) {
                result = $.parseJSON(result);
                if (result.success) {
                    callback(field, result);
                } else {
                    msh.util.tip(result.result.label);
                }
            }
        },
        onUploadError: function() {
            $('#' + field + '_upload').removeClass('file-loading');
        }
    });
}

function base64Download(field) {
    var download = $('<a href="javascript:void(0)" onclick="downloadBase64File(this)">下载</a>');
    download.attr({target: field});
    $('#' + field + '_upload').html(download);
}

function downloadBase64File(elem) {
    var target = $(elem).attr('target');
    var inputItem = $('#' + target + '_base64');
    var base64Data = inputItem.val();
    var filename = inputItem.attr('filename');
    post('/common/download_base64', [{name: 'filename', value: filename}, {name: 'data', value: base64Data}]);
}

/**
 * 上传图片结果处理
 * @param field
 * key: field + '_key'
 * @param result
 */
function handleUploadResult(field, result) {
    result = $.parseJSON(result);
    if (result.success) {
        $('#' + field + '_key').val(result.key);
        showPicture(field, result.url);
    } else {
        if (result.errors.length>0) {
            msh.util.tip(result.errors[0].message);
        } else {
            msh.util.tip(result.result.label);
        }
    }
}

/**
 * 异步上传图片后显示图片
 * @param field
 * imageId = field + '_imageShow'
 * @param url
 */
function showPicture(field, url) {
    if (isEmpty(url)) return;
    var imageShow = $('#' + field + '_imageShow');
    var img = $('<img />', {'src': url, 'class': 'tooltip'});
    imageShow.append(img);
}

function initAudioPlayer(field, key) {
    var musicShow = $('#' + field + '_musicShow');
    musicShow.empty();
    musicShow.removeClass('file-loading');

    if (isEmpty(key)) return;
    $('#' + field + '_key').val(key);

    var src = '/common/mp3/' + key;
    var audio = $('<audio></audio>', {src: src, preload: 'none'});
    musicShow.append(audio);

    audiojs.events.ready(function() {
        audiojs.createAll({}, audio);
        var key = $('#' + field + '_key').val();
        var filename = key.substring(key.lastIndexOf('/') + 1);
        var nameShow = $('<em class="filename" title="' + filename + '">' + decodeURIComponent(filename) + '</em>');
        musicShow.find('.audiojs').find('.time').append(nameShow);
    });
}


//////////////////////         模板选择           ////////////////////////////////
/**
 * @param type type=1时为图片模板，type=2时为背景音乐
 */
function openTemplateChooseDialog(type) {
    msh.ajax.doAjax({
        type: 'post',
        url: '/restaurant/template/categories',
        data: {type: type},
        success: function(result) {
            var categories = result.categories;
            var box = $('<div></div>');
            var tabs = $('<div class="template_categories"></div>');
            var ul = $('<ul></ul>');
            for (var i in categories) {
                var li = $('<li onclick="loadTemplate(this);"></li>').attr({type: type, category: categories[i]});
                if (i==0) { li.addClass('current');}
                li.append('<a href="javascript:void(0);">' + categories[i] + '</a>');
                ul.append(li);
            }
            var otherLi = $('<li onclick="loadTemplate(this);"></li>').attr({type: type, category: ''});
            otherLi.append('<a href="javascript:void(0);">其它</a>');
            ul.append(otherLi);
            tabs.append(ul);
            box.append(tabs);
            var templateList = $('<div id="template_list"></div>');
            if (type == 1) {
                templateList.attr({'class': 'music_template'});
            } else if (type == 2) {
                templateList.attr({'class': 'picture_template'});
            } else {
                msh.util.alert('参数错误，type取值只能为1或2');
                return;
            }
            box.append(templateList);
            openDialog("选择模板", box.html(), null, 700, 450, true);
            ul.find('li:first-child').trigger('click');
        }
    });
}
function loadTemplate(elem) {
    var li = $(elem);
    li.parent().find('li').removeClass('current');
    li.addClass('current');
    var type = li.attr('type');
    var category = li.attr('category');
    msh.ajax.doAjax({
        type: 'post',
        url: '/restaurant/template/templates',
        data: {type: type, category: category},
        success: function(result) {
            var ul = $('#template_list');
            ul.empty();
            var templates = result.templates;
            if (templates.length == 0) {
                ul.append('<li>即将上线</li>');
            } else {
                for (var i in templates) {
                    var template = templates[i];
                    var key = template.value;
                    var li = $('<li></li>').attr({key: key});
                    if (type == 1) {
                        li.attr({onclick: 'chooseMusic(this);'});
                        var filename = key.substring(key.lastIndexOf('/') + 1);
                        var name = $('<label>' + template.name + ' [' + filename + '] </label>');
                        var choose = $('<button class="choose">选择</button>');
                        li.append(name).append(choose);
                    } else if (type == 2) {
                        li.attr({onclick: 'setPicture(this);'});
                        var div = $('<div></div>');
                        div.css({background: 'background: url("/common/images/' + key + '") 50% 50% / cover no-repeat"'});
                        var name1 = $('<label>' + template.name + '</label>');
                        li.append(div, name1);
                    }
                    ul.append(li);
                }
            }
        }
    });
}

function bindSystemWindowResizeEvent() {
    $(window).resize(function () {
        bodyHeight();
    });
    bodyHeight();
}
function bodyHeight() {
    var pageHeight = $('html').height();
    var headHeight = $('#header').outerHeight();
    var minHeight = (pageHeight - headHeight - 60 - 35);
    if (window.fullHeight) {
        $('#body_content').css({'height': minHeight});
    } else {
        $('#body_content').css({'min-height': minHeight});
    }
}

function login(url) {
    if (url != undefined && !isEmpty(url)) {
        forward('/login?returnUrl=' + url);
    } else {
        forward('/login');
    }
}

//模拟spring验证 验证成功关闭当前窗口 并刷新当前页面
function validateOpenSaveOrUpdate(form){
    msh.util.showLoading();
    var thisForm = $(form);
    var url = thisForm.attr('action');
    $("[id^='error']").each(function (i) {
       $(this).text("");
    });
    $("[id^='has_error']").each(function (i) {
       $(this).removeClass('has-error');
    });
    msh.ajax.doAjax({
        type: 'post',
        url: url,
        data: thisForm.serialize(),
        success: function(result) {
            msh.util.hideLoading();
            if(result.success) {
                msh.util.alert("保存成功", {ok:function(){
                    window.parent.reload();
                    msh.util.closeOpenedUrl();
                }});
            }else{
                var obj = eval(result.errors);
                $.each(obj, function (n, value){
                    $('#has_error_'+n).addClass('has-error');
                    $('#error_'+n).text(value);
                });
            }
        }
    });
}

function switchUser(type, account) {
    forward('/system/' + type + '_switch_user/go?account=' + account);
}