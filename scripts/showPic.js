 function ShowPic(Whichpic)
        whichpic.getAttribute("href")
        var source = whichpic.getAttribute("href")
        var placeholder = document.getElementById("placeholder");
        placeholder.setAttribute("src",source);