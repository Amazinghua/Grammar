function gettxt(str) {
    var rst = "";
    $.ajax({
        type: "GET",
        async: false,
        url: str,
        dataType: "text",
        success: function (data) {
            rst = data;
        },
        error: function (e, t, k) {

        },
        complete: function (xhr, e) {


        }
    });
    return rst;
}

//    yyyy/08/06 00:00   
//    yyyy-08-06T00:00
function stringToDate(str) {
    var rst = new Date();
    str = str.replace(/-/g, "/");
    str = str.replace(/T/g, " ");
    str = str.substr(0, 19);
    rst.setTime(Date.parse(str));
    if (!isnullorempty(str)) {
        return rst;
    }
    else { return "" }
}

function isnullorempty(obj) {
    if (obj != null && obj != undefined && $.trim(obj) != "")
    { return false }
    else {
        return true;
    }
}

function DateToString(date, format) {
    if (!isnullorempty(date)) {
        if (format.indexOf("yyyy") >= 0) {
            format = format.replace(/yyyy/g, date.getFullYear());
        }

        if (format.indexOf("yy") >= 0) {

            format = format.replace(/yy/g, date.getFullYear().toString().subStr(2, 2));
        }

        if (format.indexOf("MM") >= 0) {
            format = format.replace(/MM/g, dateNumStr(date.getMonth() + 1));
        }

        if (format.indexOf("dd") >= 0) {
            format = format.replace(/dd/g, dateNumStr(date.getDate()));
        }

        if (format.indexOf("hh") >= 0) {
            format = format.replace(/hh/g, dateNumStr(date.getHours()));
        }

        if (format.indexOf("HH") >= 0) {
            format = format.replace(/HH/g, dateNumStr(date.getHours() % 12));
        }

        if (format.indexOf("mm") >= 0) {
            format = format.replace(/mm/g, dateNumStr(date.getMinutes()));
        }
        if (format.indexOf("ss") >= 0) {
            format = format.replace(/ss/g, dateNumStr(date.getSeconds()));
        }
    }
    else {
        format = "";
    }

    function dateNumStr(str) {
        if (str.toString().length == 1) {
            str = "0" + str;
        }
        return str;
    }
    return format;
}

function dateformatChange(str, format) {
    if (isnullorempty(format)) {
        return DateToString(stringToDate(str), 'yyyy-MM-dd hh:mm:ss');
    }
    else {
        return DateToString(stringToDate(str), format);

    }
}

//异步ajax
function qyw_ajax(jsonOrtext, postdata, fang_fa) {
    try {
        $.ajax({
            type: "POST",
            url: "getdata.aspx?",
            async: false,
            cache: false,
            contentType: "application/json; charset=utf-8",
            data: (postdata),
            datatype: jsonOrtext,
            error: function (result) {
                alert(result.readyState);
            },
            success: function (data) {
                try {
                    var date = JSON.parse(data);
                } catch (ex) { }

                fang_fa(date);
            }
        })
    }
    catch (Error) {
        alert(Error.Message);
    }
}

//获取URL的参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

//检验是否为空
function isUndefinedNullOrEmpty(s) {
    return (s === undefined || s === null || s === "");
}

// json的解析方法共有兩種：1. eval() ; 2.JSON.parse()。具體使用方法如下 
function stringToJson(stringValue) {
    try {
        eval("var theJsonValue = " + stringValue);
        return theJsonValue;
    }
    catch (ex) { }

}
//适配ie8以下获取name对象
function getByName(Name) {
    var i = document.getElementsByName(Name)
    if (i > 0) {
        return i;
    } else {
        var aEle = document.getElementsByTagName('*');
        var arr = [];
        for (var i = 0; i < aEle.length; i++) {
            if (aEle[i].getAttribute("name") == Name) {
                arr.push(aEle[i])
            }
        }
        return arr;
    }
}