
$(function() {
    //Tab 创建 删除  切换  关闭操作等相关事件代码
    //新增  tab   原【c】
    function addTab(option) {
        var o = option.data.adr || $(this).attr("href");
        var m =  $(this).data("index") || $(".J_menuTab",".J_menuTabs").length,
            l = option.data.text || $.trim($(this).text()),
            k = true;
        var parent= option.data.parent || null;
        if (o == undefined || $.trim(o).length == 0) {
            return false;
        }
        $(".J_menuTab").each(function() {
            if ($(this).data("id") == o) {
                if (!$(this).hasClass("active")) {
                    //刷新iframe
                    var _url=$(".J_mainContent .J_iframe[data-id='"+o+"']").attr("src");
                    _url=_url.indexOf("?") != -1 ? _url+"&cache="+Math.random() : _url+"?cache="+Math.random();
                    $(".J_mainContent .J_iframe[data-id='"+o+"']").attr("src",_url);
                    
                    $(this).addClass("active").siblings(".J_menuTab").removeClass("active");                    
                    switchTheTab(this);                      
                    $(".J_mainContent .J_iframe").each(function() {
                        if ($(this).data("id") == o) {
                            $(this).show().siblings(".J_iframe").hide();
                            return false;
                        }
                    })
                }
                k = false;
                return false;
            }
        });
        if (k) {
            var p = '<a href="javascript:;" class="active J_menuTab" '+(parent?"data-parent='"+parent+"'":"")+'  data-index="'+m+'" data-id="' + o + '">' + l + ' <i class="fa fa-times-circle"></i></a>';
            
            $(".J_menuTab").removeClass("active");
            var n = '<iframe class="J_iframe" name="iframe' + m + '" width="100%" height="100%" src="' + o + '" frameborder="0" data-id="' + o + '" seamless></iframe>';
            $(".J_mainContent").find("iframe.J_iframe").hide().parents(".J_mainContent").append(n);
            $(".J_menuTabs .page-tabs-content").append(p);
            switchTheTab($(".J_menuTab.active"));  
        }
        return false;
    }

    //获取传入 dom 的整体宽度  原【f】方法
    function getDomFullWidth(l) {
        var k = 0;
        $(l).each(function() {
            k += $(this).outerWidth(true);
        });
        return k;
    }
      
    //跳转到 对应 tab   原【g】
    function switchTheTab(n) {  
        var o = getDomFullWidth($(n).prevAll()),
            q = getDomFullWidth($(n).nextAll());
        var l = getDomFullWidth($(".content-tabs").children().not(".J_menuTabs"));
        var k = $(".content-tabs").outerWidth(true) - l;
        var p = 0;
        if ($(".page-tabs-content").outerWidth() < k) {
            p = 0;
        } else {
            if (q <= (k - $(n).outerWidth(true) - $(n).next().outerWidth(true))) {
                if ((k - $(n).next().outerWidth(true)) > q) {
                    p = o;
                    var m = n;
                    while ((p - $(m).outerWidth()) > ($(".page-tabs-content").outerWidth() - k)) {
                        p -= $(m).prev().outerWidth();
                        m = $(m).prev();
                    }
                }
            } else {
                if (o > (k - $(n).outerWidth(true) - $(n).prev().outerWidth(true))) {
                    p = o - $(n).prev().outerWidth(true);
                }
            }
        }
        $(".page-tabs-content").animate({
            marginLeft: 0 - p + "px"
        }, "fast");
    }
    //往左查看的tab 
    $(".J_tabLeft").on("click", function(){
        var o = Math.abs(parseInt($(".page-tabs-content").css("margin-left")));
        var l = getDomFullWidth($(".content-tabs").children().not(".J_menuTabs"));
        var k = $(".content-tabs").outerWidth(true) - l;
        var p = 0;
        if ($(".page-tabs-content").width() < k) {
            return false;
        } else {
            var m = $(".J_menuTab:first");
            var n = 0;
            while ((n + $(m).outerWidth(true)) <= o) {
                n += $(m).outerWidth(true);
                m = $(m).next();
            }
            n = 0;
            if (getDomFullWidth($(m).prevAll()) > k) {                  
                while ((n + $(m).outerWidth(true)) < (k) && m.length > 0) {
                    n += $(m).outerWidth(true);
                    m = $(m).prev();
                }
                p = getDomFullWidth($(m).prevAll());
            }
        }
        $(".page-tabs-content").animate({
            marginLeft: 0 - p + "px"
        }, "fast");
    });
    
    
    //往右查看的tab 
    $(".J_tabRight").on("click", function(){
        
        var o = Math.abs(parseInt($(".page-tabs-content").css("margin-left")));
        var l = getDomFullWidth($(".content-tabs").children().not(".J_menuTabs"));
        var k = $(".content-tabs").outerWidth(true) - l;
        var p = 0;
        if ($(".page-tabs-content").width() < k) {
            return false
        } else {
            var m = $(".J_menuTab:first");
            var n = 0;
            while ((n + $(m).outerWidth(true)) <= o) {
                n += $(m).outerWidth(true);
                m = $(m).next();
            }
            n = 0;
            while ((n + $(m).outerWidth(true)) < (k) && m.length > 0) {
                n += $(m).outerWidth(true);
                m = $(m).next();
            }
            p = getDomFullWidth($(m).prevAll());
            if (p > 0) {
                var np=0-p;
                $(".page-tabs-content").animate({
                    marginLeft: np + "px"
                }, "fast");
            }
        }
    });
    
    //左侧导航栏 菜单项 点击事件
    $("#side-menu").on("click",".J_menuItem",{adr:null,text:null}, addTab);
    
    $(".J_menuTabs").on("dblclick", ".J_menuTab", function() {
        var l = $('.J_iframe[data-id="' + $(this).data("id") + '"]');
        var k = l.attr("src");
    });
    
    //切换到该tab
    $(".J_menuTabs").on("click", ".J_menuTab", function(){
        if (!$(this).hasClass("active")) {
            var k = $(this).data("id");
            $(".J_mainContent .J_iframe").each(function() {
                if ($(this).data("id") == k) {
                    $(this).show().siblings(".J_iframe").hide();
                    return false;
                }
            });
            $(this).addClass("active").siblings(".J_menuTab").removeClass("active");
            switchTheTab(this);
        }
    });
    
    //关闭  该 tab
    $(".J_menuTabs").on("click", ".J_menuTab i", function(){ 
        var m = $(this).parents(".J_menuTab").data("id");
        var l = $(this).parents(".J_menuTab").width();
        if ($(this).parents(".J_menuTab").hasClass("active")) {
            if ($(this).parents(".J_menuTab").next(".J_menuTab").size()) {
                var k = $(this).parents(".J_menuTab").next(".J_menuTab:eq(0)").data("id");
                $(this).parents(".J_menuTab").next(".J_menuTab:eq(0)").addClass("active");
                $(".J_mainContent .J_iframe").each(function() {
                    if ($(this).data("id") == k) {
                        $(this).show().siblings(".J_iframe").hide();
                        return false
                    }
                });
                var n = parseInt($(".page-tabs-content").css("margin-left"));
                if (n < 0) {
                    $(".page-tabs-content").animate({
                        marginLeft: (n + l) + "px"
                    }, "fast")
                }
                $(this).parents(".J_menuTab").remove();
                $(".J_mainContent .J_iframe").each(function() {
                    if ($(this).data("id") == m) {
                        $(this).remove();
                        return false;
                    }
                })
            }
            if ($(this).parents(".J_menuTab").prev(".J_menuTab").size()) {
                var k = $(this).parents(".J_menuTab").prev(".J_menuTab:last").data("id");
                $(this).parents(".J_menuTab").prev(".J_menuTab:last").addClass("active");
                $(".J_mainContent .J_iframe").each(function() {
                    if ($(this).data("id") == k) {
                        $(this).show().siblings(".J_iframe").hide();
                        return false
                    }
                });
                $(this).parents(".J_menuTab").remove();
                $(".J_mainContent .J_iframe").each(function() {
                    if ($(this).data("id") == m) {
                        $(this).remove();
                        return false
                    }
                })
            }
        } else {
            $(this).parents(".J_menuTab").remove();
            $(".J_mainContent .J_iframe").each(function() {
                if ($(this).data("id") == m) {
                    $(this).remove();
                    return false
                }
            });
            switchTheTab($(".J_menuTab.active"))
        }
        return false;
    });
    
    
//  $(".J_tabCloseOther").click(i);
    
    $(".J_tabShowActive").on("click", function(){switchTheTab($(".J_menuTab.active"));});
    
     //无调用方法
     //关闭所有 tab
    $(".J_tabCloseAll").on("click", function() {
        $(".page-tabs-content").children("[data-id]").not(":first").each(function() {
            $('.J_iframe[data-id="' + $(this).data("id") + '"]').remove();
            $(this).remove()
        });
        $(".page-tabs-content").children("[data-id]:first").each(function() {
            $('.J_iframe[data-id="' + $(this).data("id") + '"]').show();
            $(this).addClass("active")
        });
        $(".page-tabs-content").css("margin-left", "0")
    })
    
    //关闭 其他所有tab
    $(".J_tabCloseOther").on("click", function(){
        $(".page-tabs-content").children("[data-id]").not(":first").not(".active").each(function() {
            $('.J_iframe[data-id="' + $(this).data("id") + '"]').remove();
            $(this).remove()
        });
        $(".page-tabs-content").css("margin-left", "0")
    });
        
    $(".J_menuItem").each(function(k) {
        if (!$(this).attr("data-index")) {
            $(this).attr("data-index", k)
        }
    });
    
    //刷新当前iframe
    $(".J_tabRefresh").on("click",function(){
        var src=$("iframe:visible").prop("src");
        $(".J_iframe:visible").prop("src",src);
    });
    
    
  //人工打开一下TAB页面
    //o为菜单路径
    //l为菜单名称
    //m为索引号
    $.extend({ 
        InnerAddTab:function (o,l){
            var index=$(".J_menuTab.active").data("index");
            var option={data:{adr:o,text:l,parent:index}};
            addTab(option);
        },
        CloseTab:function(isRefresh){
            var cur=$(".J_menuTab.active");            
            if(isRefresh){
                var parent=cur.data("parent");
                if(parent){
                    var tab=$(".J_menuTab[data-index='"+parent+"']");
                    $("iframe[name='iframe"+parent+"']").prop("src",tab.data("id"));
                    tab.click();
                }
            }
            $("i",cur).click();
        }
   });
});

