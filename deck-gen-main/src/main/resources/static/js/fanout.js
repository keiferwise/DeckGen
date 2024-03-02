// Get all the card elements
var cards = document.querySelectorAll('.b-card');
// Calculate the rotation angle for each card
var angle = 10; // Adjust this value to change the fan-out angle
var totalCards = cards.length;
var initialRotation = -angle * (totalCards - 1) / 2;
// Calculate the radius of the arch
var radius = 400; // Adjust this value to change the radius of the arch
var startX = 1;
var startY = 200;
// Add click event listener to each card
cards.forEach(function (card, index) {
    var rotation = initialRotation + angle * index;
    var radians = rotation * (Math.PI / 180);
    var x = startX + (90 * index);
    var y = 0;
    if (index < 5) {
        y = startY - (25 * index);
    }
    else {
        y = startY - 100 + (25 * (index - 4));
    }
    card.style.transform = "translate(calc(50% + ".concat(x, "px), calc(10% + ").concat(y, "px)) rotate(").concat(rotation, "deg)");
    card.addEventListener('click', function () {
        if (card.classList.contains('active')) {
            // Remove 'active' class from the clicked card
            card.classList.remove('active');
            card.style.transform = "translate(calc(50% + ".concat(x, "px), calc(10% + ").concat(y, "px)) rotate(").concat(rotation, "deg)");
        }
        else {
            // Remove 'active' class from all cards except the clicked card
            cards.forEach(function (c) {
                if (c !== card) {
                    c.classList.remove('active');
                }
            });
            // Add 'active' class to the clicked card
            card.classList.add('active');
            card.style.transform = "translate(50%, 50%) scale(1.3) rotate(0deg)";
        }
    });
});
