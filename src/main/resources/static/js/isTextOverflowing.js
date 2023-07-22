var fontSize2 = 12.5; // Initial font size
var myOutput3 = document.getElementById('myOutput');
var isOverflowing = isTextOverflowingContainer(myOutput3);
console.log("Text overflowing container: ".concat(isOverflowing));
var maxTries = 10;
var count = 0;
var images = myOutput3.getElementsByTagName('img');
while (isTextOverflowingContainer(myOutput3) && count < maxTries) {
    console.log('still overflowing');
    images = myOutput3.getElementsByTagName('img');
    scaleDown(images);
    //isOverflowing = isTextOverflowingContainer(myOutput3);
    count++;
    myOutput3 = document.getElementById('myOutput');
}
function scaleDown(images) {
    fontSize2 -= 0.5; // 
    myOutput3.style.fontSize = "".concat(fontSize2, "px");
    for (var i = 0; i < images.length; i++) {
        var image = images[i];
        var currentWidth = image.width;
        var currentHeight = image.height;
        var newWidth = currentWidth * 0.95; // Decrease width by 10%
        var newHeight = currentHeight * 0.95; // Decrease height by 10%
        image.style.width = "".concat(newWidth, "px");
        image.style.height = "".concat(newHeight, "px");
    }
}
function isTextOverflowingContainer(container) {
    // Get the computed style of the container to include any CSS styles affecting the height
    var containerStyle = window.getComputedStyle(container);
    // Get the container's percentage height (e.g., "28%")
    var containerHeightPercentage = containerStyle.height;
    console.log(containerHeightPercentage);
    // Parse the percentage value (remove the % sign and convert to a number)
    var containerHeightPercentageValue = parseFloat(containerHeightPercentage);
    // Get the parent element of the container to calculate its height
    var parentElement = container.parentElement;
    // Calculate the parent element's actual height in pixels
    var parentElementHeight = 420;
    console.log(parentElementHeight);
    // Calculate the container's height in pixels based on the percentage value
    var containerHeightInPixels = 119;
    console.log(containerHeightInPixels);
    // Calculate the content height (including padding, but not border or margin)
    var contentHeight = container.scrollHeight - parseFloat(containerStyle.paddingTop) - parseFloat(containerStyle.paddingBottom);
    // Check if the content height exceeds the container's height
    var isOverflowing = contentHeight > containerHeightInPixels;
    console.log("Text overflowing is ".concat(isOverflowing, " because: ").concat(contentHeight, " is greater than ").concat(containerHeightInPixels, " "));
    return isOverflowing;
}
