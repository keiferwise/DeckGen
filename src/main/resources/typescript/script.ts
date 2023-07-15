const myButton = document.getElementById('myButton') as HTMLButtonElement;
const myOutput = document.getElementById('myOutput') as HTMLDivElement;
let fontSize = 12.5; // Initial font size
myButton.addEventListener('click', () => {
    fontSize -= 0.5; // Increase font size by 1 pixel
    myOutput.style.fontSize = `${fontSize}px`;

const images = myOutput.getElementsByTagName('img');
for (let i = 0; i < images.length; i++) {
  const image = images[i] as HTMLImageElement;
  const currentWidth = image.width;
  const currentHeight = image.height;
  const newWidth = currentWidth * 0.9; // Decrease width by 10%
  const newHeight = currentHeight * 0.9; // Decrease height by 10%
  image.style.width = `${newWidth}px`;
  image.style.height = `${newHeight}px`;
}
});