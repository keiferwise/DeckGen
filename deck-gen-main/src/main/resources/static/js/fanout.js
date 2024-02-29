// Get all the card elements
var cards = document.querySelectorAll('.b-card');
// Calculate the rotation angle for each card
var angle = 20; // Adjust this value to change the fan-out angle
var totalCards = cards.length;
var initialRotation = -angle * (totalCards - 1) / 2;
console.log("hello");
console.log(cards.length);
// Calculate the radius of the arch
var radius = 200; // Adjust this value to change the radius of the arch
// Add click event listener to each card
cards.forEach(function (card, index) {
    var rotation = initialRotation + angle * index;
    var radians = rotation * (Math.PI / 180);
    var x = Math.cos(radians) * radius;
    var y = Math.sin(radians) * radius;
    console.log(x + ", " + y);
    card.style.transform = "translate(calc(50% + ".concat(x, "px), calc(50% + ").concat(y, "px)) rotate(").concat(rotation, "deg)");
    card.addEventListener('click', function () {
        // Remove 'active' class from all cards
        cards.forEach(function (c) { return c.classList.remove('active'); });
        // Add 'active' class to the clicked card
        card.classList.add('active');
    });
});
