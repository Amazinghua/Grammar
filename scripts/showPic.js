 function ShowPic(Whichpic) {
        whichpic.getAttribute("href")
        var source = whichpic.getAttribute("href")
        var placeholder = document.getElementById("placeholder");
        placeholder.setAttribute("src",source);
 }
 function counBodyChildren() {
     var body_element = Document.getElementByTagName("body")[0];
     alert (body_element.childNodes.length);
 }