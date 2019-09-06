/**
 *
 * @requires jQuery
 *
 * @author：AaronZhou
 * 将form表单元素的值序列化成对象，当前只解决了multiselect控件的问题，并且控件中必须加入：data-role="multiselect"
 * 资料来源：http://marsvaadin.iteye.com/blog/1717188
 *
 * @returns Json对象
 */
(function($){
    $.fn.serializeJson=function(){
        var serializeObj={};
        var array=this.serializeArray();
        var thisForm=$(this);
        $(array).each(function(){
            //是否checkbox
            var checkBox=thisForm.find("[name='"+this.name+"']").attr("type");
            var isCheckBox=false;
            //是否多选框
            var multiSelect=thisForm.find("[name='"+this.name+"']").data("role");
            var isMultiSelect=false;

            if(checkBox){
                isCheckBox = checkBox.toLowerCase()=="checkbox";
            }

            if(multiSelect){
                isMultiSelect = multiSelect.toLowerCase()=="multiselect";
            }

            if(isCheckBox|| isMultiSelect){
                if($.isArray(serializeObj[this.name])){
                    serializeObj[this.name].push(this.value);
                }
                else{
                    serializeObj[this.name]=[this.value];
                }
            }
            else{
                serializeObj[this.name]=this.value;
            }
        });
        return serializeObj;
    };
})(jQuery);

/**
 *
 * @requires jQuery *
 *
 * * 生成一个GUID
 */
$.GUID = function() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the
    // clock_seq_hi_and_reserved
    // to 01
    s[8] = s[13] = s[18] = s[23] = "";

    return s.join("");
}

/**
 *
 * @requires jQuery
 *
 * 将form表单元素的值序列化成对象
 *
 * @returns object
 */
$.serializeObject = function(form) {
    var o = {};
    $.each(form.serializeArray(), function(index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
        } else {
            o[this['name']] = this['value'];
        }
    });
    return o;
};
/**
 *
 * @requires jQuery
 *
 * 将form表单元素的值序列化成对象
 *
 * @returns object
 */
$.serializeObject2 = function(form) {
    var o = {};
    $.each(form.serializeArray(), function(index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
        } else {
            if(this['value']){
                o[this['name']] = this['value'];
            }

        }
    });
    return o;
};

/**
 *
 * @requires jQuery
 *
 * @author：
 *
 * @date:2017-1-11
 * jquery 实现下载文件,导出Excel(通过jquery异步，后台将datatable数据写进excel，然后生成excel文件保存到服务器，然后返回文件名到前台，提示是否下载文件)
 */
//Ajax 文件下载
jQuery.ajaxDownload = function (url, data, method) {
    // 获取url和data
    if (url && data) {
        // data 是 string 或者 array/object
        data = typeof data == 'string' ? data : decodeURIComponent(jQuery.param(data));
        // 把参数组装成 form的  input
        var inputs = '';
        jQuery.each(data.split('&'), function () {
            var pair = this.split('=');
            inputs += '<input type="hidden" name="' + pair[0] + '" value="' + pair[1] + '" />';
        });
        // request发送请求
        jQuery('<form action="' + url + '" method="' + (method || 'post') + '">' + inputs + '</form>').appendTo('body').submit().remove();
    };
};