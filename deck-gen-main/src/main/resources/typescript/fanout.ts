// Get all the card elements
const cards = document.querySelectorAll<HTMLElement>('.b-card');
// Calculate the rotation angle for each card
const angle = 20; // Adjust this value to change the fan-out angle
const totalCards = cards.length;
const initialRotation = -angle * (totalCards - 1) / 2;
console.log("hello");
console.log(cards.length);
// Calculate the radius of the arch
const radius = 200; // Adjust this value to change the radius of the arch

// Add click event listener to each card
cards.forEach((card, index) => {
  const rotation = initialRotation + angle * index;
  const radians = rotation * (Math.PI / 180);
  const x = Math.cos(radians) * radius;
  const y = Math.sin(radians) * radius;
  console.log(x + ", "+y);

  card.style.transform = `translate(calc(50% + ${x}px), calc(50% + ${y}px)) rotate(${rotation}deg)`;

  card.addEventListener('click', () => {
    // Remove 'active' class from all cards
    cards.forEach((c) => c.classList.remove('active'));

    // Add 'active' class to the clicked card
    card.classList.add('active');
  });
});