
// Get all the card elements
const cards = document.querySelectorAll<HTMLElement>('.b-card');

// Calculate the rotation angle for each card
const angle = 10; // Adjust this value to change the fan-out angle
const totalCards = cards.length;
const initialRotation = -angle * (totalCards - 1) / 2;

// Calculate the radius of the arch
const radius = 400; // Adjust this value to change the radius of the arch
const startX = 1;
const startY = 200;

// Add click event listener to each card
cards.forEach((card, index) => {
  const rotation = initialRotation + angle * index;
  const radians = rotation * (Math.PI / 180);
  const x = startX + (90 * index);
  let y = 0;

  if (index < 5) {
    y = startY - (25 * index);
  } else {
    y = startY - 100 + (25 * (index - 4));
  }

  card.style.transform = `translate(calc(50% + ${x}px), calc(10% + ${y}px)) rotate(${rotation}deg)`;

  card.addEventListener('click', () => {
    if (card.classList.contains('active')) {
      // Remove 'active' class from the clicked card
      card.classList.remove('active');
      card.style.transform = `translate(calc(50% + ${x}px), calc(10% + ${y}px)) rotate(${rotation}deg)`;
    } else {
      // Remove 'active' class from all cards except the clicked card
      cards.forEach((c) => {
        if (c !== card) {
          c.classList.remove('active');
        }
      });

      // Add 'active' class to the clicked card
      card.classList.add('active');
      card.style.transform = `translate(50%, 50%) scale(1.3) rotate(0deg)`;
    }
  });
});
