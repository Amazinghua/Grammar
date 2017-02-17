//多个页面加载程序  
function addLoadEvent(func){  
    var oldonload=window.onload;  
    if(typeof window.onload !='function'){  
        window.onload=func;  
    }else{  
        window.onload=function(){  
            oldonload();  
            func();  
        };  
    }  
}  
//在某个元素后面插入新元素  
function insertAfter(newElement,targetElement){  
    var parent=targetElement.parentNode;  
    if (parent.lastChild==targetElement){  
        parent.appentChild(newElement);  
    }else{  
        parent.insertBefore(newElement,targetElement.nextSibling);  
    }  
}  
//创建显示部分并插入到点击列表的后面  
function preparePlaceholder(){  
    if(!document.createElement) return false;  
    if(!document.createTextNode) return false;  
    if(!document.getElementById) return false;  
    if(!document.getElementById("imagegallery")) return false;  
    var placeholder=document.createElement("img");  
    placeholder.setAttribute("id","placeholder");  
    placeholder.setAttribute("src","img/liuchutian3.jpg");  
    placeholder.setAttribute("alt","my image gallery");  
    var description=document.createElement("p");  
    description.setAttribute("id","description");  
    var desctext=document.createTextNode("Choose an image");  
    description.appendChild(desctext);  
    var gallery=document.getElementById("imagegallery");  
    insertAfter(placeholder,gallery);  
    insertAfter(description,placeholder);  
}  
//对每个链接添加点击事件  
function prepareGallery(){  
    if(!document.getElementsByTagName) return false;  
    if(!document.getElementById) return false;  
    if(!document.getElementById("imagegallery")) return false;  
    var gallery= document.getElementById("imagegallery");  
    var links=gallery.getElementsByTagName("a");  
    for(var i=0;i<links.length;i++){  
        links[i].onclick = function() {  
        return showPic(this);  
    }  
    links[i].onkeypress = links[i].onclick;  
   }  
}  
//替换显示图片和文字  
function showPic(whichpic) {  
  if (!document.getElementById("placeholder")) return true;  
  var source = whichpic.getAttribute("href");  
  var placeholder = document.getElementById("placeholder");  
  placeholder.setAttribute("src",source);  
  if (!document.getElementById("description")) return false;  
  if (whichpic.getAttribute("title")) {  
    var text = whichpic.getAttribute("title");  
  } else {  
    var text = "";  
  }  
  var description = document.getElementById("description");  
  if (description.firstChild.nodeType == 3) {  
    description.firstChild.nodeValue = text;  
  }  
  return false;  
}  
addLoadEvent(preparePlaceholder);  
addLoadEvent(prepareGallery);  