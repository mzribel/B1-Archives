const swiper = new Swiper('.swiper', {
    // Optional parameters
    direction: 'horizontal',
    loop: true,
  
    // If we need pagination
    pagination: {
      el: '.swiper-pagination',
    },
  
    slidesPerView: 2,
    // Navigation arrows
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
  
    breakpoints: {

        550: {
            slidesPerView: 3
        },
        950: {
            slidesPerView: 5
        },
    },
    
    observer: true,
    observeParents: true,

    spaceBetween: 10,
  });

window.addEventListener("resize", function() {
    console.log("resizing")
    swiper.update()
})