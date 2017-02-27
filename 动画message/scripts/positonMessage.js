function positionMessage() {
    if (!document.getElementById) return false;
    if (!document.getElementById("message")) return false;
    var elem = document.getElementById("messsage");
    elem.style.position = "absolute";
    elem.style.left = "50px";
    elem.style.top = "100px";
    movement = setTimeout("message",200.100,10);
}
addLoadEvent(positionMessage);