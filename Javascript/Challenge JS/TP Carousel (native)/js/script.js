var currSlideIndex = 0;
var carouselBody = document.querySelector(".carousel-body")
var slideContainer = document.querySelector(".slide-container")
var slideItem = document.querySelectorAll(".slide-item")
var slideIcons = Array.from(document.querySelector(".slide-icons").children)
var slideWidth = carouselBody.offsetWidth

var switchAllowed = true

document.querySelector(".prev").addEventListener("click", function() {switchSlide(-1)})
document.querySelector(".next").addEventListener("click", function() {switchSlide(1)})
slideContainer.addEventListener("transitionend", function() {
    switchAllowed = true
})


function switchSlide(direction) {
    slideContainer.classList.add("transition-allowed")
    if (!switchAllowed) {
        return
    }
    if (direction == 1 && currSlideIndex != slideItem.length - 1) {
        slideContainer.style.left = slideContainer.offsetLeft - slideWidth + "px" 
        currSlideIndex++
        clearSlideIcons(currSlideIndex)
        switchAllowed = false;
    }
    if (direction == -1 && currSlideIndex != 0) {
        slideContainer.style.left = slideContainer.offsetLeft + slideWidth + "px"
        currSlideIndex--
        clearSlideIcons(currSlideIndex)
        switchAllowed = false;
    }
    
}

function clearSlideIcons(currentIndex) {
    if (slideIcons.length != 0) {
        slideIcons.forEach(element => {
            element.classList.remove("icon-active")
        });
        slideIcons[currentIndex].classList.add("icon-active")
    }
}

function shortcutSwitch(iconID) {
    slideContainer.classList.add("transition-allowed")


    if (!switchAllowed) {
        return
    }
    if (iconID > slideItem.length -1 || iconID == currSlideIndex) {
        return
    }

    slideContainer.style.left = "-" +(iconID * slideWidth) + "px"
    currSlideIndex = iconID
    clearSlideIcons(currSlideIndex)
    switchAllowed = false;
}


for (let i = 0; i < slideIcons.length; i++) {
    slideIcons[i].addEventListener("click", function() {
        shortcutSwitch(i)
    })
}

window.addEventListener("resize", function() {
    slideWidth = getElementSize(carouselBody)
    slideContainer.classList.remove("transition-allowed")
    slideContainer.style.left = "-" +(currSlideIndex * slideWidth) + "px"
    
})

function getElementSize(element) {
    return element.offsetWidth
}