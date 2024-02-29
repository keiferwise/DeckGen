// Get all the card elements
var cards = document.querySelectorAll('.b-card');
// Calculate the rotation angle for each card
var angle = 10; // Adjust this value to change the fan-out angle
var totalCards = cards.length;
var initialRotation = -angle * (totalCards - 1) / 2;
console.log("hello");
console.log(cards.length);
// Calculate the radius of the arch
var radius = 400; // Adjust this value to change the radius of the arch
var startX = 1;
var startY = 200;
// Add click event listener to each card
cards.forEach(function (card, index) {
    var rotation = initialRotation + angle * index;
    var radians = rotation * (Math.PI / 180);
    //const x = Math.cos(radians) * radius;
    var y = 0;
    var x = startX + (90 * index);
    if (index < 5) {
        var y = startY - (25 * index);
    }
    else {
        var y = startY - 100 + (25 * (index - 4));
    }
    console.log(x + ", " + y);
    card.style.transform = "translate(calc(50% + ".concat(x, "px), calc(10% + ").concat(y, "px)) rotate(").concat(rotation, "deg)");
    card.addEventListener('click', function () {
        // Remove 'active' class from all cards
        cards.forEach(function (c) { return c.classList.remove('active'); });
        // Add 'active' class to the clicked card
        card.classList.add('active');
    });
});
