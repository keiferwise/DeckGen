let fontSize2 = 12.5; // Initial font size

let myOutput3 = document.getElementById('myOutput');
let isOverflowing = isTextOverflowingContainer(myOutput3);
console.log(`Text overflowing container: ${isOverflowing}`);
let maxTries = 10;
let count= 0 ;
let images = myOutput3.getElementsByTagName('img');

while(isTextOverflowingContainer(myOutput3) && count<maxTries){
  console.log('still overflowing');
  images = myOutput3.getElementsByTagName('img');
  scaleDown(images);
  //isOverflowing = isTextOverflowingContainer(myOutput3);
  count++;
  myOutput3 = document.getElementById('myOutput');
}
function scaleDown(images: HTMLCollectionOf<HTMLImageElement>) {
  fontSize2 -= 0.5; // 
  myOutput3.style.fontSize = `${fontSize2}px`;
  for (let i = 0; i < images.length; i++) {
    const image = images[i] as HTMLImageElement;
    const currentWidth = image.width;
    const currentHeight = image.height;
    const newWidth = currentWidth * 0.95; // Decrease width by 10%
    const newHeight = currentHeight * 0.95; // Decrease height by 10%
    image.style.width = `${newWidth}px`;
    image.style.height = `${newHeight}px`;
  }
}


function isTextOverflowingContainer(container: HTMLElement): boolean {
  // Get the computed style of the container to include any CSS styles affecting the height
  const containerStyle = window.getComputedStyle(container);
  
  // Get the container's percentage height (e.g., "28%")
  const containerHeightPercentage = containerStyle.height;
  console.log(containerHeightPercentage);
  // Parse the percentage value (remove the % sign and convert to a number)
  const containerHeightPercentageValue = parseFloat(containerHeightPercentage);
  
  // Get the parent element of the container to calculate its height
  const parentElement = container.parentElement;

  // Calculate the parent element's actual height in pixels
  const parentElementHeight = 420;
  console.log(parentElementHeight);
  // Calculate the container's height in pixels based on the percentage value
  const containerHeightInPixels = 119;
  console.log(containerHeightInPixels);

  // Calculate the content height (including padding, but not border or margin)
  const contentHeight = container.scrollHeight - parseFloat(containerStyle.paddingTop) - parseFloat(containerStyle.paddingBottom);
  // Check if the content height exceeds the container's height
  const isOverflowing = contentHeight > containerHeightInPixels;
  console.log(`Text overflowing is ${isOverflowing} because: ${contentHeight} is greater than ${containerHeightInPixels} `);

  return isOverflowing;
}



