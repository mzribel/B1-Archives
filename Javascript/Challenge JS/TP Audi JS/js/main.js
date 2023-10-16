// Defines valid customisation values and their respective prices.
const VALID_RIMS = {"medium": 2500, "premium": 3000, "normal": 2000};
const VALID_COLORS = {"blue": 80000, "nacre": 82000};

// Keeps track of the current customisation values.
let currentColor = "blue";
let currentRims = "normal";

// Watches the changes of the radio buttons, launches the function 
// with the correct parameters.
let radioBtns = document.querySelectorAll(".customisation");
if (radioBtns) {
    radioBtns.forEach(btn => {
        btn.addEventListener("change", function() {
            btn.name == "rims" ? 
                changeCarAttributes(currentColor, btn.value) : changeCarAttributes(btn.value, currentRims);
            updateCarPrice(currentColor, currentRims);
        });
    });
};

// Updates the src attribute of the car img according to params.
function changeCarAttributes(color=currentColor, rims=currentRims) {
    // Basic security check
    if (!VALID_RIMS.hasOwnProperty(rims) || !VALID_COLORS.hasOwnProperty(color)) {
        console.log("è_é")
    }
    let carDiv = document.getElementById("car-img");
    carDiv.src = `img/cars/e-tron-gt/${color}_${rims}.png`;
    // Updates the global variables:
    currentColor = color; currentRims = rims;
}

// Updates the price of the chosen car.
function updateCarPrice(color, rims) {
    priceDiv = document.getElementById("car-price");
    priceDiv.innerHTML = VALID_RIMS[rims] + VALID_COLORS[color];
}
// Base initialisation of the car price.
updateCarPrice(currentColor, currentRims);