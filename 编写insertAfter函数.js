function insertAfter(newElement,targetElement) {
    var parent = targetElement.parentNode;
    if (parent.lastChildChild == targetElement) {
        parent.appendChild(newElement);
    } else {
        parent.insertBefore(newElement,targetElement.nextSibling);
    }
}