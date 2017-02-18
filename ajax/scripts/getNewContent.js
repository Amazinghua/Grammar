function getNewContent() {
    var request = getHTTPObject();
    if (request) {
        request.open("GET","example.txt",true );
        request.onreadystatechange = function() {
        if (request.readyState == 4) {
            var para = document.createElement("p");
            var txt = document.createTextNode(request.reponseText);
            para.appendChild(txt);
            document.getElementById('new').appendChild(para);
        }
    };
    request.send(null);
} else {
    alert('sorry,your browser doesn\'t support XMLHTTPRequest');
}
alert("Function Done");
}
addLoadEvent(getNewContent);