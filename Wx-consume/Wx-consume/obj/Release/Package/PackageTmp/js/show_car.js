function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
}
$(document).ready(function () {
    showpage();
});
function showpage() {
    var myname = getQueryString("name");
    var jsonObj = {
        type: "gethtml",
        name: myname
    }
    var postStr = JSON.stringify(jsonObj);
    useAjax("show.ashx", true, postStr, showlist);
}
function showlist(data) {
    var all = "";
    if (data.result == "100") {
        var pages = data.show;
        document.getElementById("showPage").innerHTML = pages;
    }
}