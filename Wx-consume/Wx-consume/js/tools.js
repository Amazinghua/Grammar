/*获取url地址栏的参数值
@param {string} container 目标页面链接所带的参数集合(截取url后的参数内容，不包含？)
@param {string} name 参数键名
@return {string} 返回对应的键值
*/
function GetQueryString(container, name) {
    container = "?" + container;
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = container.substr(1).match(reg);
    if (r != null) return (r[2]); return null;
}
/*获取url地址栏的参数值 (只能获取当前页面)
@param {string} name 参数键名
@return {string} 返回对应的键值
*/
function GetQueryString2(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

/*获取源URL
@param {Document} document 页面Dom对象
@return {string} 返回页面链接
*/
function getUrl(document) {
    var url = '';
    if (document.referrer.length > 0) {
        url = document.referrer;
    }
    try {
        if (url.length == 0 && opener.location.href.length > 0) {
            url = opener.location.href;
        }
    } catch (e) { }
    return url
}


function useAjax(tgurl, flag, json, method) {
    var result = "";
    try {
        $.ajax({
            type: 'POST',
            async: flag,
            cache: false,
          contentType: "application/json; charset=utf-8",
           // contentType: "application/octet-stream",
            url: tgurl,
            data: json,
            datatype: 'json',
            success: function (data) {
                data = JSON.parse(data);
                if (flag == false) { result = data; }
                else { method(data); }
            },
            error: function (ex) {
                //alert(ex.responseText);
            }
        });
    } catch (ex) { }
    return result;
}

/*AJAX请求(jsonp 跨域请求)
@param {string} tgUrl 请求页面地址
@param {bool} flag true:异步，false:同步
@param {string} json 请求数据
@param {string} method 异步方式数据成功回达时所执行的(匿名)函数
@return {method} 若成功便执行目标方法，否则弹窗提示错误
*/
function useAjaxC(tgUrl, flag, json, method) {
    var result = "";
    $.ajax({
        type: "get",
        async: flag,
        url: tgUrl,
        dataType: "jsonp",
        data: json,
        jsonp: "callback",
        success: function (date) {
            if (flag == false) { result = date; }
            else { method(date); }
        },
        error: function (e) {
            alert(e.responseText)
        }
    });
}

/*采用正则方式将字符串的前后空白去掉
@param {string} str 目标字符串
@return {string} 字符串
Trim
*/
function trim(str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

/*检查字符串是否为合法手机号码 
@param {String} phone 手机号码 
@return {bool} true:合法，false:不合法 
*/
function isPhone(phone) {
    var bool = RegExp(/^(0|86|17951)?(13[0-9]|15[012356789]|18[0-9]|14[57])[0-9]{8}$/).test(phone);
    if (bool) {
        return true;
    }
    else
        return false;
}

/*检查字符串是否为合法邮件地址 
@param {String} email 邮件地址 
@return {bool} true:合法，false:不合法
*/
function isEmail(email) {
    var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    var bool = RegExp(reg).test(email);
    if (bool) {
        return true;
    }
    else {
        return false;
    }
}

/*将字符串转换为字节数组（JS并没有处理字节流的能力）
@param {String} str 字符串
@return {byte} 字节流数组
*/
function stringToBytes(str) {
    var ch, st, re = [];
    for (var i = 0; i < str.length; i++) {
        ch = str.charCodeAt(i);  // get char   
        st = [];                 // set up "stack"  
        do {
            st.push(ch & 0xFF);  // push byte to stack  
            ch = ch >> 8;          // shift value down by 1 byte  
        }
        while (ch);
        // add stack contents to result  
        // done because chars have "wrong" endianness  
        re = re.concat(st.reverse());
    }
    // return an array of bytes  
    return re;
}

/*延迟按钮点击事件
@param {int} interval 定时器发生间隔
@param {int} continueTime 定时器持续时间
@param {object} obj 定时器所执行的对象
@param {object} timer 定时器对象
*/
function timeTip(interval, continueTime, obj, timer) {
    var lastTime = new Date().getTime() + continueTime;
    timer = window.setInterval(function () {
        var timeNow = new Date().getTime();  //获取当前时间的毫秒数
        var count = Math.round((lastTime - timeNow) / 1000);  //获取剩余的秒数
        if (count == 0) {
            $(obj).attr("disabled", false);
            $(obj).val("Get Email Code");
            window.clearInterval(timer);
        } else {
            $(obj).attr("disabled", true)
            $(obj).val("Seng Again After " + count + "s");
            count--;
        }
    }, interval)
}

/*获取json中对象的个数
@param {String} json json字符串
*/
function getJsonNum(json) {
    var k;
    var num = 0;
    for (k in json) {
        num++;
    }
    return num;
}

/*获取json的长度
@param {String} json json字符串
*/
function getJsonLength(jsonData) {
    var jsonLength = 0;
    for (var item in jsonData) {
        jsonLength++;
    }
    return jsonLength;
}

/**
 * 获取随机数字组合
 * @param n 随机字符长度
 * @returns {string}
 */
function getRandom(n) {
    var result = "";
    for (var j = 0; j < n; j++) {
        var i = Math.random() //获得0-1的随机数
        var r = Math.ceil(i * 10) //乘以10并向上去整
        if (r == 10) {
            r = 9;
        }
        result += r;
    }
    return result;
}

/*在当前页面打开一个新页面，不带referer
@link {String} link  新页面链接
*/
function open_without_referrer(link) {
    document.body.appendChild(document.createElement('iframe')).src = 'javascript:"<script>top.location.replace(\'' + link + '\')<\/script>"';
}

/*在新窗口打开一个新页面，不带referer（bug:万恶的微信浏览器不支持 也许跟它只有单标签页的造成的）
@link {String} link  新页面链接
*/
function open_new_window(full_link) {
    window.open('javascript:window.name;', '<script>location.replace("' + full_link + '")<\/script>');
}

/*转换布尔值函数（主要为了让整数0返回true）
@value {Mixed} value 被转换的字段
*/
function parseBool(value) {
    if (typeof (value) == 'string') { value = trim(value.toString()); }
    if (value === 0) { return true; } //避免""自动转换为0，所以使用全等运算符
    else { return Boolean(value); }
}

/* 停止冒泡
*/
function stopFunc(e) {
    e.stopPropagation ? e.stopPropagation() : e.cancelBubble = true;
}

/* 检测是否存在特殊字符（正则）
@param {String} str 需要检测的字符串
*/
function checkSpecial(str) {
    var bool = RegExp(/[@/'\"#$%&^*~!\\;\{\}\[\]:'\<\>,.\?]/).test(str);
    return bool;
}
/* 检测是否存在中文字符（正则）
@param {String} str 需要检测的字符串
@return {Bool} true:contain;false:noContain
*/
function checkChinese(str) {
    var bool = RegExp("[\\u4E00-\\u9FFF]+").test(str);
    return bool;
}