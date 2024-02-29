
// Get all the card elements
const cards = document.querySelectorAll('.card');

// Calculate the rotation angle for each card
const angle = 20; // Adjust this value to change the fan-out angle
const totalCards = cards.length;
const initialRotation = -angle * (totalCards - 1) / 2;

// Add click event listener to each card
cards.forEach((card, index) => {
  const rotation = initialRotation + angle * index;
  card.style.transform = `translate(-50%, -50%) rotate(${rotation}deg)`;

  card.addEventListener('click', () => {
    // Remove 'active' class from all cards
    cards.forEach((c) => c.classList.remove('active'));

    // Add 'active' class to the clicked card
    card.classList.add('active');
  });
});

