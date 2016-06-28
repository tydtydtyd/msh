/**
 * Created by tyd on 2016/3/8.
 */
$(function () {
    initMenu();
});

function initMenu() {
    var path = document.location.pathname;
    path = path.substring(0, path.lastIndexOf('\/'));
    var header_ul = $('#index_header_ul');
    header_ul.find("li").each(function () {
        var li = $(this);
        if (li.find('ul').length > 0) {
            li.find('ul').find('li').each(function () {
                var li_a = $(this).find("a");
                if (li_a.attr('href').indexOf(path) >= 0) {
                    $(this).addClass('active');
                    li.addClass('active').addClass('open');
                    return false;
                }
            });
        } else {
            var li_a = li.find("a");
            if (li_a.attr('href').indexOf(path) >= 0) {
                li.addClass('active').addClass('open');
                return false;
            }
        }
    });

    initNav();
}

function initNav() {
    var breadcrumb = $('#breadcrumb_ul');
    var indexLi = $('<li><i class="ace-icon fa fa-home home-icon"></i><a href="/system/login/index">首页</a></li>');
    breadcrumb.append(indexLi);
    var secondBreadcrumb;
    var thirdBreadcrumb;
    $('#index_header_ul').find('li ul li').each(function () {
        if ($(this).hasClass('active')) {
            thirdBreadcrumb = $(this).find('a').text();
            secondBreadcrumb = $(this).parent('.submenu').parent('li').find('a').find('span').text();
            return false;
        }
    });
    if (!isEmpty(secondBreadcrumb)) {
        var sli = $('<li></li>');
        sli.append(secondBreadcrumb);
        breadcrumb.append(sli);
    }
    if (!isEmpty(thirdBreadcrumb)) {
        var tli = $('<li class="active"></li>');
        tli.append(thirdBreadcrumb);
        breadcrumb.append(tli);
    }
}