function isTextOverflowingContainer(container) {
    // Get the computed style of the container to include any CSS styles affecting the height
    var containerStyle = window.getComputedStyle(container);
    // Get the container's percentage height (e.g., "28%")
    var containerHeightPercentage = containerStyle.height;
    // Parse the percentage value (remove the % sign and convert to a number)
    var containerHeightPercentageValue = parseFloat(containerHeightPercentage);
    // Get the parent element of the container to calculate its height
    var parentElement = container.parentElement;
    // Calculate the parent element's actual height in pixels
    var parentElementHeight = 420 * 0.28;
    // Calculate the container's height in pixels based on the percentage value
    var containerHeightInPixels = (containerHeightPercentageValue / 100) * parentElementHeight;
    // Calculate the content height (including padding, but not border or margin)
    var contentHeight = container.scrollHeight - parseFloat(containerStyle.paddingTop) - parseFloat(containerStyle.paddingBottom);
    // Check if the content height exceeds the container's height
    var isOverflowing = contentHeight > containerHeightInPixels;
    return isOverflowing;
}
var myOutput3 = document.getElementById('myOutput');
var isOverflowing = isTextOverflowingContainer(myOutput3);
console.log("Text overflowing container: ".concat(isOverflowing));
