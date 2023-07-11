function scaleContent(container: HTMLElement) {
  const contentElements = container.querySelectorAll('span, img');
  const maxFontSize = 100; // Maximum font size
  const maxImageSize = 200; // Maximum image size in pixels

  for (let i = 0; i < contentElements.length; i++) {
    const element = contentElements[i];

    if (element instanceof HTMLSpanElement) {
      // Scale text
      let fontSize = 30; // Initial font size

      const dummyElement = document.createElement('span');
      dummyElement.style.fontSize = `${fontSize}px`;
      dummyElement.style.visibility = 'hidden';
      dummyElement.innerText = element.innerText;

      container.appendChild(dummyElement);

      // Reduce font size until the text fits within the container
      while (dummyElement.offsetWidth > container.offsetWidth || dummyElement.offsetHeight > container.offsetHeight) {
        fontSize--;

        if (fontSize <= 0 || fontSize <= maxFontSize) {
          break; // Exit the loop if font size is too small or reaches the maximum
        }

        dummyElement.style.fontSize = `${fontSize}px`;
      }

      container.removeChild(dummyElement);
      element.style.fontSize = `${fontSize}px`;
    } else if (element instanceof HTMLImageElement) {
      // Scale images
      let imageSize = Math.min(element.naturalWidth, element.naturalHeight, maxImageSize);

      while (imageSize > maxImageSize || imageSize > container.offsetWidth || imageSize > container.offsetHeight) {
        imageSize--;

        element.style.width = `${imageSize}px`;
        element.style.height = `${imageSize}px`;
      }
    }
  }
}
