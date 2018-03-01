function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
}
function sameback(shows) {
    var all = "";
    var strs = new Array();
    strs = shows.split(","); //字符分割 
    for (i = 0; i < strs.length; i++) {
        var title = "";
        if (strs[i].length > 19) {
            title = strs[i].substring(0, 18) + "......";
        } else {
            title = strs[i];
        }
        all += " <li><a href=\"show_car.aspx?name=" + strs[i] + "\">" + title + "</a></li>";

    }
    document.getElementById("allmessage").innerHTML = all;
}
$(document).ready(function () {
    showpage();
});
function showpage() {
    var myname = getQueryString("id");
    if (myname == "1") {
        var jsonObj = {
            type: "getcarr"
        }
        var postStr = JSON.stringify(jsonObj);
        mj_ajax("show.ashx", "json", postStr, getcarr);
        function getcarr(data) {
            if (data.result == "00") {
                var shows = data.show;
                sameback(shows);
            }
        }
    } else if (myname == "2") {
        var jsonObj = {
            type: "getrh"
        }
        var postStr = JSON.stringify(jsonObj);
        mj_ajax("show.ashx", "json", postStr, getrh);
        function getrh(data) {
            if (data.result == "01") {
                var shows = data.show;
                sameback(shows);
            }
        }
    } else if (myname == "3") {
        var jsonObj = {
            type: "getjx"
        }
        var postStr = JSON.stringify(jsonObj);
        mj_ajax("show.ashx", "json", postStr, getrh);
        function getrh(data) {
            if (data.result == "02") {
                var shows = data.show;
                sameback(shows);
            }
        }
    } else if (myname == "4") {
        var jsonObj = {
            type: "getyl"
        }
        var postStr = JSON.stringify(jsonObj);
        mj_ajax("show.ashx", "json", postStr, getrh);
        function getrh(data) {
            if (data.result == "03") {
                var shows = data.show;
                sameback(shows);
            }
        }
    } else if (myname == "5") {
        var jsonObj = {
            type: "getnl"
        }
        var postStr = JSON.stringify(jsonObj);
        mj_ajax("show.ashx", "json", postStr, getrh);
        function getrh(data) {
            if (data.result == "04") {
                var shows = data.show;
                sameback(shows);
            }
        }
    } else if (myname == "6") {
        var jsonObj = {
            type: "getls"
        }
        var postStr = JSON.stringify(jsonObj);
        mj_ajax("show.ashx", "json", postStr, getrh);
        function getrh(data) {
            if (data.result == "05") {
                var shows = data.show;
                sameback(shows);
            }
        }
    } else if (myname == "7") {
        var jsonObj = {
            type: "getwy"
        }
        var postStr = JSON.stringify(jsonObj);
        mj_ajax("show.ashx", "json", postStr, getrh);
        function getrh(data) {
            if (data.result == "06") {
                var shows = data.show;
                sameback(shows);
            }
        }
    } else if (myname == "8") {
        var jsonObj = {
            type: "getbc"
        }
        var postStr = JSON.stringify(jsonObj);
        mj_ajax("show.ashx", "json", postStr, getrh);
        function getrh(data) {
            if (data.result == "07") {
                var shows = data.show;
                sameback(shows);
            }
        }
    } else if (myname == "9") {
        var jsonObj = {
            type: "getbh"
        }
        var postStr = JSON.stringify(jsonObj);
        mj_ajax("show.ashx", "json", postStr, getrh);
        function getrh(data) {
            if (data.result == "08") {
                var shows = data.show;
                sameback(shows);
            }
        }
    } else if (myname == "10") {
        var jsonObj = {
            type: "getyc"
        }
        var postStr = JSON.stringify(jsonObj);
        mj_ajax("show.ashx", "json", postStr, getrh);
        function getrh(data) {
            if (data.result == "09") {
                var shows = data.show;
                sameback(shows);
            }
        }
    } else if (myname == "11") {
        var jsonObj = {
            type: "getht"
        }
        var postStr = JSON.stringify(jsonObj);
        mj_ajax("show.ashx", "json", postStr, getrh);
        function getrh(data) {
            if (data.result == "10") {
                var shows = data.show;
                sameback(shows);
            }
        }
    } else if (myname == "12") {
        var jsonObj = {
            type: "getcx"
        }
        var postStr = JSON.stringify(jsonObj);
        mj_ajax("show.ashx", "json", postStr, getrh);
        function getrh(data) {
            if (data.result == "11") {
                var shows = data.show;
                sameback(shows);
            }
        }
    } else if (myname == "13") {
        var jsonObj = {
            type: "getjz"
        }
        var postStr = JSON.stringify(jsonObj);
        mj_ajax("show.ashx", "json", postStr, getrh);
        function getrh(data) {
            if (data.result == "12") {
                var shows = data.show;
                sameback(shows);
            }
        }
    }
}