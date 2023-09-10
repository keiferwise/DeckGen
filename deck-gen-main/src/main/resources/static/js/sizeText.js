function getNumberOfLinesInContainer(container) {
    // Get the computed style of the container to include any CSS styles affecting the height
    var containerStyle = window.getComputedStyle(container);
    // Calculate the container's total height (content + padding + border)
    var containerHeight = container.offsetHeight;
    // Create a temporary element to calculate the height of a single line of text
    var tempElement = document.createElement('span');
    tempElement.innerText = 'Test'; // Add some sample text to measure
    // Apply the same font properties as the container
    tempElement.style.font = containerStyle.font;
    tempElement.style.lineHeight = containerStyle.lineHeight;
    console.log();
    // Add the temporary element to the DOM (but make it invisible)
    tempElement.style.position = 'absolute';
    tempElement.style.visibility = 'hidden';
    document.body.appendChild(tempElement);
    // Calculate the height of a single line of text
    var lineHeight = tempElement.offsetHeight;
    console.log(lineHeight);
    // Remove the temporary element from the DOM
    document.body.removeChild(tempElement);
    // Calculate the number of lines in the container
    var numberOfLines = Math.round(containerHeight / lineHeight);
    return numberOfLines;
}
var myOutput2 = document.getElementById('myOutput');
var numberOfLines = getNumberOfLinesInContainer(myOutput);
console.log("Number of lines in the container: ".concat(numberOfLines));
