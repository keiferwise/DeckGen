// Get all the card elements
const cards = document.querySelectorAll<HTMLElement>('.b-card');
// Calculate the rotation angle for each card
const angle = 10; // Adjust this value to change the fan-out angle
const totalCards = cards.length;
const initialRotation = -angle * (totalCards - 1) / 2;
console.log("hello");
console.log(cards.length);
// Calculate the radius of the arch
const radius = 400; // Adjust this value to change the radius of the arch
const startX = 1;
const startY = 200;
// Add click event listener to each card
cards.forEach((card, index) => {
  const rotation = initialRotation + angle * index;
  const radians = rotation * (Math.PI / 180);
  //const x = Math.cos(radians) * radius;
  var y = 0;
  
  const x = startX + (90*index);

  if(index<5){
    var y =  startY - (25*index);

  }
  else {
    var y =  startY-100 + (25*(index-4));

  }
  console.log(x + ", "+y);

  card.style.transform = `translate(calc(50% + ${x}px), calc(10% + ${y}px)) rotate(${rotation}deg)`;

  card.addEventListener('click', () => {
    // Remove 'active' class from all cards
    cards.forEach((c) => c.classList.remove('active'));

    // Add 'active' class to the clicked card
    card.classList.add('active');
  });
});