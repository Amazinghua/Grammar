$(document).ready(function () {
    show();
});

function show() {
    var jsonObj = {
        type: "getWarn"
    }
    var postStr = JSON.stringify(jsonObj);
    mj_ajax("show.ashx", "json", postStr, gaiing);
    //useAjax("show.ashx", true, postStr, gaiing);
}

function gaiing(data) {

    var all = "";
    if (data.result == "444") {
        var shows = data.show;
        var strs = new Array(); //定义一数组 
        strs = shows.split(","); //字符分割 

        for (i = 0; i < strs.length; i++) {
            var title = "";
            if (strs[i].length > 19) {
                title = strs[i].substring(0, 18) + "......";
            } else {
                title = strs[i];
            }
            all += " <li><a href=\"show_warn.aspx?name=" + strs[i] + "\">" + title + "</a></li>";

        }
        document.getElementById("allmessage").innerHTML = all;
    } else {
        alert("获取数据失败。。。");
    }
}