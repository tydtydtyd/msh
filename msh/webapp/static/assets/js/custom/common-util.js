window.debugEnabled = false;
window.log = function(msg) {if(debugEnabled && window.console){window.console.log((typeof msg == 'string') ? (msh.date.now() + ': ' + msg) : msg)}};
String.prototype.trim = function() { return this.replace(/(^\s*)|(\s*$)/g,""); };
String.prototype.replaceAll = function(string, replaceWith, ignoreCase) {
    if (!RegExp.prototype.isPrototypeOf(string)) {
        return this.replace(new RegExp(string, (ignoreCase ? "gi": "g")), replaceWith);
    } else {
        return this.replace(string, replaceWith);
    }
};
String.prototype.startsWith=function(str){
    return (!isEmpty(str) || this.length || str.length <= this.length) && this.substring(0, str.length) == str;
};
String.prototype.endsWith=function(str){
    return (!isEmpty(str) || this.length || str.length <= this.length) && this.substring(this.length - str.length) == str;
};
Date.prototype.subtract = function(dateLimit) { var newLimit = {}; for (var i in dateLimit) newLimit[i] = -dateLimit[i]; return this.add(newLimit) };
Date.prototype.add = function(dateLimit) {
    if ('days' in dateLimit) {
        return new Date(this.setDate(this.getDate() + dateLimit['days']));
    } else if ('month' in dateLimit) {
        return new Date(this.setMonth(this.getMonth() + dateLimit['month']));
    } else {
        console.error('Date add/subtract support days|month ONLY.');
        return null;
    }
};


window.msh = {
    util: {
        hostName: function(){
            return location.protocol + '//' + location.host + (isEmpty(location.port) ? '' : ':' + location.port);
        },
        bindSelectAll: function(eId, name, m, callback) {
            var all = $('#' + eId);
            if (m) {
                all.bind('click', function(){
                    if (all.is(':checked')) {
                        msh.util.checkAll(name);
                    } else {
                        msh.util.uncheckAll(name);
                    }
                    if (callback && (typeof callback == 'function')) {
                        callback($(this), name);
                    }
                });
            } else {
                all.hide();
            }
            var checkbox = $(':checkbox[name=' + name + ']:not(:disabled)');
            checkbox.bind('click', function(){
                if (!m) {
                    checkbox.removeAttr('checked');
                    $(this).attr({checked: 'checked'});
                }
                if (callback && (typeof callback == 'function')) {
                    callback($(this), name);
                }
            });
        },
        checkAll: function(name) {
            $(':checkbox[name=' + name + ']:not(:disabled)').attr({checked:'checked'});
        },
        uncheckAll: function(name) {
            $(':checkbox[name=' + name + ']:checked').removeAttr('checked');
        },
        checkboxAllSupport: function(fieldName) {
            var $checkboxAll = $(':input[name="' + fieldName + '"]');
            $checkboxAll.bind('click', function(){
                toggleClick(this);
            });
            toggleClick($(':input[name="' + fieldName + '"]:checked'));

            function toggleClick(elem) {
                $('.checkbox-all-support').hide();
                var _currentValue = $(elem).val();
                $('.checkbox-all-support-' + _currentValue).show();
            }
        },
        getCheckedValue: function(name) {
            var selectedValues = [];
            var cbx = $(':checkbox[name=' + name + ']:checked');
            cbx.each(function(){
                selectedValues.push($(this).val());
            });
            return selectedValues;
        },
        getCheckedAttr: function(name, attr) {
            var selectedValues = [];
            var cbx = $(':checkbox[name=' + name + ']:checked');
            cbx.each(function(){
                selectedValues.push($(this).attr(attr));
            });
            return selectedValues;
        },
        getCheckedAttrObject: function(name, attrs) {
            var selectedObjects = [];
            var cbx = $(':checkbox[name=' + name + ']:checked');
            cbx.each(function(){
                var _this = $(this);
                var object = {'value': _this.val()};
                for (var a in attrs) {
                    var attr = attrs[a];
                    object[attr] = _this.attr(attr);
                }
                selectedObjects.push(object);
            });
            return selectedObjects;
        },
        getSelectedLabel: function(id) {
            var label = $('#' + id + ' option:selected').html();
            return label == '请选择' ? '' : label;
        },
        blockUI: function(msg) {
            if ($.blockUI) {
                $.blockUI.defaults.css['border'] = 'none';
                $.blockUI({baseZ: 2000,message: '<div class="block_ui_message"><img src="/images/loading128.gif"><h3 class="message">正在' + msg + '，请稍候...</h3></div>'});
            }
        },
        unblockUI: function(){
            $.unblockUI && $.unblockUI();
        },
        showLoading: function(){
            layer.open({
                type: 3
            });
        },
        hideLoading: function(){
            layer.closeAll('loading');
        },
        tip: function(msg, fun){
            if ($.growlUI) {
                $.blockUI.defaults.growlCSS['right'] = 'auto';
                $.blockUI.defaults.growlCSS['top'] = '50%';
                $.blockUI.defaults.growlCSS['left'] = '50%';
                $.blockUI.defaults.growlCSS['margin-left'] = '-175px';
                $.blockUI.defaults.css['border'] = 'none';
                $.blockUI.defaults.baseZ = 2000;
                $.growlUI(msg);
            }
            if (typeof fun == 'function') {
                setTimeout(function(){fun();}, 3000);
            }
        },
        halfUp: function(val, scale, ignoredZero) {
            var result = (Math.round(val * Math.pow(10, 2)) / Math.pow(10, 2));
            var resultVal = null;
            if (typeof scale == 'number' && scale >= 0) {
                resultVal = result.toFixed(scale);
            }
            if (ignoredZero) {
                var i = 0, fixed, floatVal = parseFloat(resultVal);
                for (; i <= scale; i++) {
                    var temp = result.toFixed(i);
                    if (floatVal == temp) {
                        fixed = temp;
                        break;
                    }
                }
                return fixed;
            }
            return result;
        },
        alert: function(message, opts) {
            var _opts = $.extend({}, {content: message}, opts || {});
            layer.open({
                type: 0,
                title:'提示',
                content: message,
                closeBtn: 0,
                yes: function(index, layero){
                    _opts.ok && _opts.ok();
                    layer.close(index);
                }
            });
        },
        confirm: function(message, opts){
            var _opts = $.extend({}, {content: message}, opts || {});
            var dialog = $('<div id="dialog-confirm" class="hide">' +
                '<div class="alert alert-info bigger-110">' + message +
                '</div></div>');
            var pageBody = $('body');
            pageBody.append(dialog);

            var title = "<div class='widget-header'><h4 class='smaller'><i class='ace-icon fa fa-question red'></i> 提示</h4></div>";
            dialog.removeClass('hide').dialog({
                resizable: false,
                modal: true,
                title: title,
                title_html: true,
                buttons: [
                    {
                        html: "<i class='ace-icon fa fa-check bigger-110'></i>&nbsp; 确定",
                        "class" : "btn btn-primary btn-xs",
                        click: function() {
                            _opts.ok && _opts.ok();
                            $( this ).dialog( "close" );
                            $('#dialog-confirm').remove();
                        }
                    },{
                        html: "<i class='ace-icon fa fa-times bigger-110'></i>&nbsp; 取消",
                        "class" : "btn btn-xs",
                        click: function() {
                            $( this ).dialog("close");
                            $('#dialog-confirm').remove();
                        }
                    }
                ]
            });
        },
        ajaxConfirm: function(title, url, opts){
            var _opts = $.extend({}, {okBtn: true, cancelBtn: true, loadUrl: url, padding: '0px'}, opts || {});
            msh.util._alert(title || '提示', _opts);
        },
        zDialog: function(title, url, width, height){
            var dialog = new Dialog();
            dialog.Drag = true;
            dialog.Title = title;
            dialog.URL = url;
            dialog.Width = width;
            dialog.Height = height;
            dialog.CancelEvent = function () { //关闭事件
                dialog.close();
            };
            dialog.show();
        },
        closeZDialog: function(){
            top.Dialog.close();
        },
        openUrl: function(title, url, width, height){
            layer.open({
                type: 2,
                content: url,
                title:title,
                area:[width, height]
            });
        },
        closeOpenedUrl: function(){
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        },
        _alert: function(title, opts) {
            var _opts = $.extend({}, {title: title, loadUrl: '', content: '', staticSize: true}, opts || {});
            var id = 'msh-alert';
            var mshAlert = msh.util._dialog(id, _opts);
            msh.util._dialogContent(id, mshAlert, _opts.content, _opts.loadUrl);
        },
        _dialog: function(id, opts){
            var _opts = $.extend({}, { title: '', width: 300, height: 'auto',  modal: true,  okBtn: false, cancelBtn: false, staticSize: false, ok: function(){}, cancel: function(){}}, opts || {});
            $('#' + id).remove();
            var dialog = $('<div id="' + id + '" class="modal fade">' +
            '<div class="modal-dialog"><div class="modal-content">' +
            '<div class="modal-header">' +
            '<a href="javascript:void(0)" class="close">&times;</a>' +
            '<h4 class="modal-title">' + _opts.title + '</h4></div>' +
            '<div class="modal-body"><div class="modal-body-content"></div></div>' +
            '</div></div></div>');
            dialog.find('.close').bind('click', function(){
                closeDialogById(id);
                _opts.cancel && _opts.cancel();
            });
            var pageBody = $('body');
            pageBody.append(dialog);
            var backdrop = $('.modal-backdrop');
            if (!backdrop.length) {
                backdrop = $('<div class="modal-backdrop fade"></div>');
                pageBody.append(backdrop);
            }
            var content = dialog.find('.modal-body-content');
            content.css({
                minWidth: _opts.width,
                minHeight: _opts.height
            });
            if (_opts.okBtn || _opts.cancelBtn) {
                var footer = $('<div class="modal-footer"></div>');
                if (_opts.okBtn) {
                    var ok = $('<button type="button" class="btn-ok mr5">确定</button>');
                    footer.append(ok);
                    ok.bind('click', function(){
                        closeDialogById(id);
                        _opts.ok && _opts.ok();
                    });
                }
                if (_opts.cancelBtn) {
                    var cancel = $('<button type="button" class="btn-cancel">取消</button>');
                    footer.append(cancel);
                    cancel.bind('click', function(){
                        closeDialogById(id);
                        _opts.cancel && _opts.cancel();
                    });
                }
                dialog.find('.modal-content').append(footer);
            }
            backdrop.addClass('in');
            dialog.show();
            $(document.body).addClass('modal-open');
            content.html('<img src="/images/loading.gif" style="display: inline-block;"/> 加载中...');
            setTimeout(function(){
                dialog.addClass('in');
            }, 30);
            return dialog;
        },
        _dialogContent: function(id, dialog, content, loadUrl){
            var body = dialog.find('.modal-body-content');
            body.empty();
            if (!isEmpty(content)) {
                renderDialogTo(id, content);
            } else {
                if (!isEmpty(loadUrl)) {
                    body.html('<table style="width: 100%; height: 60px;"><tr><td style="text-align: center; vertical-align: middle;"><img src="/images/loading.gif" style="display: inline-block;"/> 加载中...</td></tr></table>');
                    $.ajax({
                        type: 'get',
                        url: loadUrl,
                        success: function(result) {
                            var login = $(result).find('#userLogin');
                            if (login && login.length > 0) {
                                closeDialogById(id);
                                msh.util.confirm('当前会话已过期，您需要重新登录才能继续操作！', {ok: function(){
                                    forward('/login?action=2');
                                }});
                            } else {
                                renderDialogTo(id, result);
                            }
                        },
                        error: function(x, s, e){
                            closeDialogById(id);
                            msh.ajax.defaults.error(x, s, e);
                        }
                    });
                }
            }
        },
        updateOrders: function(name, attr){
            msh.util.multiUpdateOrders({name: name, attr: attr, callback: function(){
                reload();
            }});
        },
        multiUpdateOrders: function(opts){
            var options = $.extend({}, {container: $('body'), name: '', attr: '', url: 'order', callback: function(){}}, opts || {});
            var orders = options.container.find(':input[name=' + options.name + ']');
            var index = 0;
            var _orders = [];
            orders.each(function(){
                var _id = $(this).attr(options.attr);
                var order = ($(this).val() || 1);
                var object = 'orders[' + (index++) + ']';
                _orders.push(object + '.id=' + _id);
                _orders.push(object + '.order=' + order);
            });
            msh.ajax.doAjax({
                type: 'post',
                url: options.url,
                data: _orders.join('&'),
                success: function(){
                    var callback = options.callback;
                    if (callback && typeof callback == 'function') {
                        callback();
                    }
                }
            }, '保存排序');
        },
        restaurantChange: function (restaurantId, restaurantName) {
            $.ajax({
                type: 'post',
                datatype: 'json',
                url: '/restaurant/change_restaurant',
                data: {restaurantId: restaurantId, restaurantName: restaurantName},
                success: function () {
                    forward('/restaurant/main');
                }
            });
        },
        tabs: function() {
            var tabs = $('#tabs');
            tabs.tabs({
                create: function(){
                    // default is hidden
                    $('#tabs').show();
                },
                ajaxOptions: {
                    complete: function(){
                        msh.util.listOperation();
                    }
                }
            });
            var active = false;
            $('.tab').each(function(i){
                var tab = $(this);
                var errors = tab.find('span[id$="errors"]');
                if (errors.length > 0) {
                    if (!active) {
                        tabs.tabs({selected: i});
                        active = true;
                    }
                    tabs.find('a[href$=' + tab.attr('id') + ']').append('<span class="tab-error">*</span>')
                }
            });
            return tabs;
        },
        refreshCurrentTabs: function(){
            var tabs = $('#tabs');
            var index = tabs.tabs('option', 'selected');
            tabs.tabs('load', index);
        },
        loadCurrentTabs: function(url){
            var tabs = $('#tabs');
            var index = tabs.tabs('option', 'selected');
            var div = tabs.find('.ui-tabs-panel:eq(' + index + ')');
            div.load(url);
        },
        tabsChangeStatus: function(id, status, tips, urlPrefix) {
            changeStatusCallback(id, status, tips, urlPrefix, function(){msh.util.refreshCurrentTabs()});
        },
        tabsMultiChangeStatus: function(name, status, tips, urlPrefix){
            var selectedValues = msh.util.getCheckedValue(name);
            if (selectedValues.length == 0) {
                return;
            }
            msh.util.confirm('你确定要' + tips + '吗？', {ok: function(){
                msh.ajax.doAjax({
                    type: 'post',
                    url: (isEmpty(urlPrefix) ? '' : urlPrefix) + 'status',
                    data: {multi: true, ids: selectedValues, status: status},
                    success: function (result) {
                        if (resultSuccess(result)) {
                            msh.util.refreshCurrentTabs();
                        }
                    }
                }, tips);
            }});
        },
        exportWithForm: function(form, url){
            var thisForm = $(form);
            var param = thisForm.attr('data-param');
            url = isEmpty(url) ? thisForm.attr('action') : url;
            var searchUrl = url + ((url.indexOf("?") != -1) ? "&" : "?") + thisForm.serialize();
            if (!isEmpty(param)) searchUrl += '&' + param;
            forward(searchUrl);
        },
        printWithForm: function(form, url){
            var thisForm = $(form);
            url = isEmpty(url) ? thisForm.attr('action') : url;
            var searchUrl = url + ((url.indexOf("?") != -1) ? "&" : "?") + thisForm.serialize();
            openWindow(searchUrl);
        },
        autoCompleteOff: function(){
            var forms = $('form');
            forms.length > 0 && forms.attr({autocomplete: 'off'});
        },
        listOperation: function(){
            $('.list_operation').find('a').each(function(){
                var link = $(this);
                if (!isEmpty(link.attr('title'))) return;
                var label = link.find('span');
                if (label.length > 0) {
                    link.attr({title: label.html()});
                }
            });
        },
        toggleMore: function() {
            var m = $('.more');
            $('#more_toggle').bind('click', function(){
                toggleMore(m.hasClass('off'));
            });

            var sign = $('#more_sign');
            toggleMore(sign.val() == 'true');

            function toggleMore(show) {
                if (show) {
                    m.removeClass('off');
                    sign.val(true);
                } else {
                    m.addClass('off');
                    sign.val(false);
                }
            }
        },
        insert: function(e, val) {
            var elem = $(e);
            if (elem.length == 0) return;

            var field = elem[0];
            if (document.selection) { // IE support
                field.focus();
                var sel = document.selection.createRange();
                sel.text = val;
                sel.select();
            } else if (field.selectionStart || field.selectionStart == 0) { // MOZILLA/NETSCAPE support
                var startPos = field.selectionStart;
                var endPos = field.selectionEnd;
                // save scrollTop before insert
                var restoreTop = field.scrollTop;
                field.value = field.value.substring(0, startPos) + val + field.value.substring(endPos, field.value.length);
                if (restoreTop > 0) {
                    field.scrollTop = restoreTop;
                }
                field.focus();
                field.selectionStart = startPos + val.length;
                field.selectionEnd = startPos + val.length;
            } else {
                field.value += val;
                field.focus();
            }
        },
        insertThis: function (_this, target) {
            msh.util.insert($('#' + target), $(_this).text());
        },
        removeSpecialChar: function (e) {
            var current = $(e);
            var val = current.val();
            if (!isEmpty(val) && (typeof val == 'string')) {
                val = val.trim();
                val = val.replaceAll('\t', '');
                current.val(val);
            }
        },
        toInt: function(val, defaultVal) {
            var parseVal = parseInt(val);
            return isNaN(parseVal) ? defaultVal : parseVal;
        },
        toFloat: function(val, defaultVal) {
            var parseVal = parseFloat(val);
            return isNaN(parseVal) ? defaultVal : parseVal;
        },
        bindMenuToggleEvent: function () {
            var toggles = $('.menu-toggle');
            if (!toggles.length) return;

            toggles.each(function(){
                var toggle = $(this);
                toggle.bind('click', function () {
                    toggleMenu(this);
                });
                var maskSelector = toggle.attr('data-toggle-mask');
                $(maskSelector).bind('click', function(){
                    toggleMenu(toggle)
                });
            });

            function toggleMenu(e) {
                var _this = $(e);
                var targetSelector = _this.attr('data-toggle-target');
                var toggleClass = _this.attr('data-toggle-class');
                var target = $(targetSelector);
                var hasClass = target.hasClass(toggleClass);
                log(targetSelector + (hasClass ? ' remove ' : ' add ') + toggleClass);
                if (hasClass) {
                    target.removeClass(toggleClass);
                } else {
                    target.addClass(toggleClass);
                }
            }
        }
    },
    form: {
        ajaxSubmit: function(form, success, tips) {
            msh.util.blockUI(tips || '保存');
            msh.ajax.doAjax({
                type: 'post',
                url: $(form).attr('action'),
                data: $(form).serialize(),
                success: function(result) {
                    success(result);
                }
            }, tips);
        },
        dialogSubmit: function(form, success, tips) {
            msh.util.blockUI(tips || '保存');
            msh.ajax.doAjax({
                type: 'post',
                url: $(form).attr('action'),
                data: $(form).serialize(),
                success: function(result) {
                    success(result);
                }
            }, tips);
        }
    },
    validation: {
        logged: false,
        isTelephone: function(val) {
            return /^0?1\d{10}$/.test(val);
        },
        isDecimal: function(val) {
            return /^(-?\d+)(\.\d+)?$/.test(val);
        },
        isInteger: function(val) {
            return /^(-?\d+)$/.test(val);
        },
        isDate: function(val) {
            return /^(\d{4}-\d{2}-\d{2})$/.test(val);
        },
        isPositiveInteger: function(val) {
            return /^(\d+)$/.test(val);
        },
        isPercentage: function(val) {
            return /^(\d[0-9]{1,2}|100)$/.test(val);
        },
        validTelephones: function (string) {
            if (isEmpty(string)) {
                return [];
            }
            var validTels = [];
            var regexp = /0?1\d{10}/ig;
            var result = string.match(regexp);
            if (result) {
                for (var i in result) {
                    var tel = result[i];
                    if (msh.validation.isTelephone(tel)) {
                        validTels.push(tel);
                    }
                }
            }
            return validTels;
        },
        isEmpty: function(text) {
            return null == text || "" == text;
        }
    },
    cookie: {
        writeCookie: function(key, value) {
            $.cookie(key, value, {path:'/'});
        },
        writeCookieDay: function(key, value, days) {
            $.cookie(key, value, {path: '/', expires: (days || 1)});
        },
        getCookie: function(key) {
            return ($.cookie) ? $.cookie(key) : '';
        },
        removeCookie: function(key) {
            $.removeCookie(key, {path: '/'});
        }
    },
    image: {
        lazyLoad: function() {
            if (jQuery.fn.lazyload) {
                $('img.lazy_load').lazyload({effect: 'fadeIn', load: function(a,b,c){
                    $(this).closest('.pic_lazy_bg').removeClass('pic_lazy_bg');
                }})
            }
        },
        tooltip: function(e, options){
            if(e.length == 0) return;
            var _opts = $.extend({}, msh.image.tooltipDefaults, options || {});
            $(e).tooltip({
                delay: 200,
                showURL: false,
                bodyHandler: function() {
                    var _this = $(this);
                    var background = (_this.is('img') ? 'url("' + _this.attr('src') + '") 50% 50% / cover no-repeat' : _this.css('background'));
                    return $("<div></div>").css({width: _opts.width, height: _opts.height, background: background, 'background-size': 'contain'});
                }
            });
        },
        tooltipDefaults: {width: 200, height: 200}
    },
    paging: {
        initSort: function(formId, sortName, sortType) {
            var sortNameHidden = $('<input type="hidden" name="sortName" value="' + sortName + '"/>');
            var sortTypeHidden = $('<input type="hidden" name="sortType" value="' + sortType + '"/>');
            $('#' + formId).append(sortNameHidden).append(sortTypeHidden);

            var sortable = $('.content_table').find('th[sort]');
            sortable.each(function(){
                var _this = $(this);
                var arrow = msh.paging.getArrow(_this, sortName, sortType);
                var columnName = _this.html();
                var newHtml = $('<a href="javascript:void(0);" onclick="msh.paging.doSort(\'' + formId + '\',this);">' + columnName + arrow + '</a>');
                newHtml.attr({title: '按' + columnName + '排序'});
                _this.html(newHtml);
            });
        },
        doSort: function(formId, e) {
            var form = $('#' + formId);
            var sortName = $(e).parent().attr('sort');
            var sortNameInput = form.find(':input[name=sortName]');
            var sortTypeInput = form.find(':input[name=sortType]');
            if (sortNameInput.val() != sortName) {
                sortNameInput.val(sortName);
                sortTypeInput.val('');
            } else {
                if (sortTypeInput.val() == 'desc') {
                    sortTypeInput.val('asc');
                } else {
                    sortTypeInput.val('desc');
                }
            }
            searchByPageNumber(form, 1, '', false);
        },
        getArrow: function (sortable, sortName, sortType) {
            var arrow = '';
            var _sortName = $(sortable).attr('sort');
            if (_sortName == sortName) {
                arrow = msh.paging.arrow.up;
                if (sortType == 'desc') {
                    arrow = msh.paging.arrow.down;
                }
            }
            return arrow;
        },
        arrow: {
            up: '↑',
            down: '↓'
        }
    },
    date: {
        format: function(date, format) {
            format = format || 'yyyy-MM-dd';
            var o = {
                "M+": date.getMonth() + 1,
                "d+": date.getDate(),
                "H+": date.getHours(),
                "m+": date.getMinutes(),
                "s+": date.getSeconds(),
                "q+": Math.floor((date.getMonth() + 3) / 3),
                "S": date.getMilliseconds(),
                "W": ['日', '一', '二', '三', '四', '五', '六'][date.getDay()]
            };

            if (/(y+)/.test(format)) {
                format = format.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
            }

            for (var k in o) {
                if (new RegExp('(' + k + ')').test(format)) {
                    format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length));
                }
            }
            return format;
        },
        dateStrGen: function(){
            return msh.date.format(new Date(), 'yyyyMMddHHmmssS');
        },
        now: function () {
            return msh.date.format(new Date(), 'yyyy-MM-dd HH:mm:ss.S');
        },
        addDay: function(date, days) {
            var num = days && !isNaN(days) ? days : 1;
            return date.add({days: num});
        }
    },
    hostName: '',
    ajax: {
        doAjax: function(opts, tips){
            var ajaxOptions = $.extend({}, msh.ajax.defaults, opts || {});
            if (tips) {msh.util.blockUI(tips);}
            ajaxOptions.success = function(result){
                if (ajaxOptions.datatype == 'json') { // Fixme 有些时候JSON会以字符串形式返回
                    if (typeof result == 'string') {
                        try {
                            result = $.parseJSON(result);
                        } catch (e) {
                            console.error(e)
                        }
                    }
                }
                if (typeof opts.success == 'function') {
                    opts.success(result);
                }
            };
            $.ajax(ajaxOptions);
        },
        defaults: {
            error:  function(x, s, e){
                msh.util.unblockUI();
                var errorMsg = '';
                if (s == 'timeout') {
                    errorMsg = '连接超时';
                } else {
                    var errorCode = x.status;
                    if (errorCode == '401') {
                        errorMsg = '401 - 访问被拒绝';
                    } else if (errorCode == '403') {
                        errorMsg = '403 - 禁止访问';
                    } else if (errorCode == '404') {
                        errorMsg = '404 - 未找到';
                    } else if (errorCode == '500') {
                        errorMsg = '500 - 内部服务器错误';
                    } else if (errorCode == '12029') {
                        errorMsg = '无法建立HTTP连接';
                    } else {
                        errorMsg = e;
                    }
                }
                if (!isEmpty(errorMsg)) msh.util.alert(errorMsg);
            }
        }
    },
    weiXin: {
        shareFriend: function(opts){
            // wxe7ed7912ce650899
            var params = $.extend({}, {appid: '', img_url: '', img_width: 640, img_height: 640, link: 'http://www.jrpy.cn/', desc: '', title: ''}, opts || {});
            WeixinJSBridge.invoke('sendAppMessage', params, function(e){
            });
        },
        shareTimeLine: function(opts){
            var params = $.extend({}, {img_url: '', img_width: 640, img_height: 640, link: 'http://www.jrpy.cn/', desc: '', title: ''}, opts || {});
            WeixinJSBridge.invoke('shareTimeline', params, function(e){
            });
        },
        shareWeiBo: function(opts){
            var params = $.extend({}, {content: '', url: 'http://www.jrpy.cn'}, opts || {});
            WeixinJSBridge.invoke('shareWeibo', params, function(e){
            });
        },
        generalShare: function(args, opts) {
            // wxe7ed7912ce650899
            var params = $.extend({}, {appid: '', img_url: '', img_width: 640, img_height: 640, link: 'http://www.jrpy.cn/', desc: '', title: ''}, opts || {});
            var scene = 0;
            switch(args.shareTo){
                case 'friend'  : scene = 1; break;
                case 'timeline': scene = 2; break;
                case 'weibo'   : scene = 3; break;
            }
            args.generalShare(params, function(res){});
        },
        addContact: function(){
            WeixinJSBridge.invoke('addContact', {webtype: "1", username: 'gh_945b75cfa202'}, function(e){
            });
        },
        profile: function() {
            WeixinJSBridge.invoke('profile',{
                'username':'gh_945b75cfa202',
                'scene':'57'
            });
        }
    },
    mobile: {
        rid: null,
        isLogged: function() {
            return !isEmpty(msh.cookie.getCookie('WE_CHAT_MEMBER_TOKEN'))
        }
    },
    event: {
        bindTo: function(t, e, fun) {
            var es = e.split(',');
            for (var i in es) {
                $(t).bind($.trim(es[i]), fun);
            }
        }
    },
    map: {
        x_pi: 3.14159265358979324 * 3000.0 / 180.0,
        bd2gg: function(point) {
            var lng = point.lng - 0.0065, lat = point.lat - 0.006;
            var z = Math.sqrt(lng * lng + lat * lat) - 0.00002 * Math.sin(lat * msh.map.x_pi);
            var theta = Math.atan2(lat, lng) - 0.000003 * Math.cos(lng * msh.map.x_pi);
            var gg_lon = z * Math.cos(theta);
            var gg_lat = z * Math.sin(theta);
            return {lat: gg_lat, lng: gg_lon};
        },
        gg2bd: function(point) {
            var lng = point.lng, lat = point.lat;
            var z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * msh.map.x_pi);
            var theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * msh.map.x_pi);
            var bd_lng = z * Math.cos(theta) + 0.0065;
            var bd_lat = z * Math.sin(theta) + 0.006;
            return {lat: bd_lat, lng: bd_lng};
        }
    },
    chart: {
        line: function(container, data) {
            var categories = [];
            for (var i in data.xAxis.categories) {
                categories.push(new Date(data.xAxis.categories[i]));
            }
            $(container).highcharts({
                chart: { height: 220, type: 'areaspline' },
                title: { text: '' },
                tooltip: {
                    headerFormat: '',
                    pointFormat: '<b>{point.name}{point.unit.x}</b><br/><b>{series.name}：</b> {point.y}{point.unit.y}'
                },
                plotOptions: {
                    areaspline: { borderWidth: 0, lineWidth: 1, fillOpacity: 0.1, states: { hover: { enabled: false } }, marker: { radius: 2} }
                },
                yAxis: {
                    title: {text: data.yAxis.title},
                    min: 0
                },
                xAxis: {
                    title: { text: data.xAxis.title},
                    categories: categories,
                    dateTimeLabelFormats: '%e日',
                    allowDecimals: false,
                    labels : {
                        formatter : function() {
                            return Highcharts.dateFormat('%e日', this.value, false);
                        }
                    }
                },
                series: data.data,
                legend: { enabled: data.data.length > 1, verticalAlign: 'top' },
                credits: { enabled: false}
            });
        },
        pie: function(container, data) {
            $(container).highcharts({
                chart: { height: 220, type: 'pie' },
                title: { text: '' },
                tooltip: {
                    headerFormat: '',
                    pointFormat: '<b>{point.name}：</b> {point.amount} {point.unit.y}<br/><b>占比：</b> {point.y}%'
                },
                plotOptions: {
                    pie: { allowPointSelect: true, dataLabels: { enabled: true, format: '<b>{point.name}:</b> {point.y}%' } }
                },
                series: data.data,
                legend: { enabled: false },
                credits: { enabled: false}
            });
        }
    }
};
/*window.onerror = function(msg, file, line){
 if (console) {
 console.log('错误：{msg:' + msg + ', file:' + file + ', line:' + line + '}');
 }
 return true;
 };*/

window.TechSupport = function(){
};
window.TechSupport.prototype = {
    renderTo: function (selector, options) {
        this.options = $.extend({}, window.TechSupport.defaults, options);
        var tech = $('<a href="/"></a>').html('&copy;' + this.options.info);
        var copyright = $('<div></div>').attr({'class': this.options.className});
        copyright.append(tech);
        ($(selector) || $('body')).append(copyright);
    }
};
window.TechSupport.defaults = {
    className: 'copyright',
    info: '迅手'
};


function initDistrict(select, selector) {
    var defaultValue = $(select).attr('defaultValue');
    $(select).val(defaultValue);
    loadDistrict(select, selector);
}

function loadMainDistrict(e, init) {
    $.ajax({
        type: 'get',
        url: '/common/district/load',
        datatype: 'json',
        data: {parentId:1, category:1},
        success: function(result){
            renderSelect(e, result.districtDTOs);
            if (init) {
                initDistrict(e, true);
            }
        }
    });
}

function loadDistrict(select, selector) {
    var parent = $(select);
    var targetId = parent.attr('target');
    var targetCategory = parent.attr('targetCategory');
    if (isEmpty(targetId) || isEmpty(targetCategory)) {
        return;
    }
    var target = selector ? parent.parent().find(targetId) : $('#' + targetId);
    var emptyText = target.attr('emptyText') || '请选择';
    var empty = $('<option value="">' + emptyText + '</option>');
    target.empty().append(empty);
    var parentId = parent.val();
    if (isEmpty(parentId)) {
        loadDistrict(target, selector);
        return;
    }
    msh.ajax.doAjax({
        type: 'get',
        url: '/common/district/load',
        datatype: 'json',
        data: {parentId:parentId, category:targetCategory},
        success: function(result){
            renderSelect(target, result.districtDTOs);
            if (!isEmpty(target)) {
                initDistrict(target, selector);
            }
        }
    });
}

function renderSelect(target, districts) {
    if (districts.length == 0) {
        return;
    }
    for (var i in districts) {
        $(target).append('<option value="' + districts[i].id + '">' + districts[i].name + '</option>');
    }
}

function ajaxErrorHandler(result, tabs) {
    var errors = result.errors;
    if (errors && errors.length > 0) {
        var error = errors[0];
        msh.util.alert(error.message, {ok:function(){
            var field = $(':input[name="' + error.field + '"]');
            if (tabs && tabs.length) {
                var tab = field.closest('.tab');
                $('.tab').each(function(i){
                    var _tab = $(this);
                    if (tab[0] == _tab[0]) {
                        tabs.tabs({selected: i});
                        return false;
                    }
                });
            }
            field.length > 0 && field.focus();
        }});
    }
}

/**
 * Format a number and return a string based on input settings
 * @param {Number} number The input number to format
 * @param {Number} decimals The amount of decimals
 * @param {String} decPoint The decimal point, defaults to the one given in the lang options
 * @param {String} thousandsSep The thousands separator, defaults to the one given in the lang options
 */
function numberFormat(number, decimals, decPoint, thousandsSep) {
    var lang = {decimalPoint: '.', thousandsSep: ','},
    // http://kevin.vanzonneveld.net/techblog/article/javascript_equivalent_for_phps_number_format/
        n = +number || 0,
        c = decimals === -1 ?
            (n.toString().split('.')[1] || '').length : // preserve decimals
            (isNaN(decimals = Math.abs(decimals)) ? 2 : decimals),
        d = decPoint === undefined ? lang.decimalPoint : decPoint,
        t = thousandsSep === undefined ? lang.thousandsSep : thousandsSep,
        s = n < 0 ? "-" : "",
        i = String(parseInt(n = Math.abs(n).toFixed(c))),
        j = i.length > 3 ? i.length % 3 : 0;

    return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) +
        (c ? d +  Math.abs(n - i).toFixed(c).slice(2) : "");
}

/*

function transformTozTreeFormat(array) {
    var nodes = [], nodeMap = [];
    for (var i = 0; i < array.length; i++) {
        var n = array[i];
        nodeMap[n.id] = n;
    }
    for (var j = 0; j < array.length; j++) {
        var node = array[j];
        var parent = nodeMap[node.pId];
        if (parent && node.id != node.pId) {
            var children = parent.children;
            if (!children) {
                children = [];
            }
            children.push(node);
        } else {
            nodes.push(node);
        }
    }
    return nodes;
}

function transformToArrayFormat(nodes) {
    if (!nodes) return [];
    var array = [];
    for (var i = 0; i < nodes.length; i++) {
        var node = nodes[i];
        array.push(node);
        node.children && (array = array.concat(transformToArrayFormat(node.children)));
    }
    return array
}*/
