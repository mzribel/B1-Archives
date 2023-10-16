$(document).ready(function(){
  $('.slick-container').slick({
    slidesToShow: 5,
    infinite: true,
    speed: 300,
    dots: true,
    responsive: [
      {
        breakpoint: 950,
        settings: {
          slidesToShow: 3,
        }
      }, 
      {
        breakpoint: 550,
        settings: {
          slidesToShow: 2
        }
      }
    ]
  });
});
	


window.addEventListener("resize", function() {
  $('.slick-container').slick("refresh")
})