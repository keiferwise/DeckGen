var myButton = document.getElementById('myButton');
var myOutput = document.getElementById('myOutput');
var fontSize = 12.5; // Initial font size
myButton.addEventListener('click', function () {
    fontSize -= 0.5; // Increase font size by 1 pixel
    myOutput.style.fontSize = "".concat(fontSize, "px");
    var images = myOutput.getElementsByTagName('img');
    for (var i = 0; i < images.length; i++) {
        var image = images[i];
        var currentWidth = image.width;
        var currentHeight = image.height;
        var newWidth = currentWidth * 0.9; // Decrease width by 10%
        var newHeight = currentHeight * 0.9; // Decrease height by 10%
        image.style.width = "".concat(newWidth, "px");
        image.style.height = "".concat(newHeight, "px");
    }
});
