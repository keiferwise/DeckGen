const scaleDownButton = document.getElementById('myButton') as HTMLButtonElement;
const scaleUpButton = document.getElementById('scaleUpButton') as HTMLButtonElement;

const myOutput = document.getElementById('myOutput') as HTMLDivElement;
let fontSize = 12.5; // Initial font size
scaleDownButton.addEventListener('click', () => {
    fontSize -= 0.5; // Increase font size by 1 pixel
    myOutput.style.fontSize = `${fontSize}px`;

const images = myOutput.getElementsByTagName('img');
for (let i = 0; i < images.length; i++) {
  const image = images[i] as HTMLImageElement;
  const currentWidth = image.width;
  const currentHeight = image.height;
  const newWidth = currentWidth * 0.95; // Decrease width by 10%
  const newHeight = currentHeight * 0.95; // Decrease height by 10%
  image.style.width = `${newWidth}px`;
  image.style.height = `${newHeight}px`;
}});

scaleUpButton.addEventListener('click', () => {
    fontSize += 0.5; // Increase font size by 1 pixel
    myOutput.style.fontSize = `${fontSize}px`;

const images = myOutput.getElementsByTagName('img');
for (let i = 0; i < images.length; i++) {
  const image = images[i] as HTMLImageElement;
  const currentWidth = image.width;
  const currentHeight = image.height;
  const newWidth = currentWidth * 1.05; // Decrease width by 10%
  const newHeight = currentHeight * 1.05; // Decrease height by 10%
  image.style.width = `${newWidth}px`;
  image.style.height = `${newHeight}px`;
}});