// GSAP animation for right part cube.
var tl = gsap.timeline({repeat: -1, repeatDelay: 0})

// Removes one step to avoid flickering between animations
let position = 100 - (100 / 132);
tl.to("#test", {y:`-${position}%`, duration: 5, ease: SteppedEase.config(131)})
