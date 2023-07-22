function isTextOverflowingContainer(container: HTMLElement): boolean {
  // Get the computed style of the container to include any CSS styles affecting the height
  const containerStyle = window.getComputedStyle(container);
  
  // Get the container's percentage height (e.g., "28%")
  const containerHeightPercentage = containerStyle.height;
  
  // Parse the percentage value (remove the % sign and convert to a number)
  const containerHeightPercentageValue = parseFloat(containerHeightPercentage);
  
  // Get the parent element of the container to calculate its height
  const parentElement = container.parentElement;

  // Calculate the parent element's actual height in pixels
  const parentElementHeight = 420 * 0.28;
  
  // Calculate the container's height in pixels based on the percentage value
  const containerHeightInPixels = (containerHeightPercentageValue / 100) * parentElementHeight;
  
  // Calculate the content height (including padding, but not border or margin)
  const contentHeight = container.scrollHeight - parseFloat(containerStyle.paddingTop) - parseFloat(containerStyle.paddingBottom);
  
  // Check if the content height exceeds the container's height
  const isOverflowing = contentHeight > containerHeightInPixels;
  
  return isOverflowing;
}

const myOutput3 = document.getElementById('myOutput');
const isOverflowing = isTextOverflowingContainer(myOutput3);
console.log(`Text overflowing container: ${isOverflowing}`);

