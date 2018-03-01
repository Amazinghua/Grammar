function mj_ajax(url, jsonOrtext, postdata, fang_fa) {
    try {
        $.ajax({
            type: "POST",
            url: url,
            async: false,
            cache: false,
            contentType: "application/json; charset=utf-8",
            data: (postdata),
            dataType: jsonOrtext,
            Error: function (result) {
                alert(result.status);
            },
            success: function (data) {
                try {
                    data = JSON.parse(data);
                }
                catch (e) { }
                fang_fa(data);
            }
        })
    }
    catch (Error) {
        alert(Error.Message);
    }
}
//异步ajax
function mj_js_ajax(jsonOrtext, postdata, fang_fa) {
    try {
        $.ajax({
            type: "Get",
            url: "getdata.ashx?",
            async: false,
            cache: false,
            contentType: "application/json; charset=utf-8",
            data: (postda),
            dataType: jsonOrtext,
            error: function (result) {
                alert(result.readyState);
            },
            success: function (data) {
                var data = JSON.parse(data);
                fang_fa(data);
            }
        })
    }
    catch (Error) {
        alert(Error.Message);
    }
}