$(document).ready(function(){
	cambiaP("pes1");
	/*** menu **/
	$(".menuWeb").on("click", function(){
		if($(this).hasClass("menuAct")){
			$(this).removeClass("menuAct");
			$(".contMenu a").addClass("fin").css({"opacity" : "0"});
			setTimeout(function(){
				$(".contMenu a").removeClass("tiempo");
			}, 300);
		}
		else{
			$(this).addClass("menuAct");
			$(".contMenu a").removeClass("fin").addClass("inicio");
			setTimeout(function(){
				$(".contMenu a").addClass("tiempo").removeClass("inicio").css({"opacity" : "1"});
			}, 200);
		}
	});
	$(".menuMov").on("click", function(){
		
		if($(this).hasClass("menuAct")){
			$(this).removeClass("menuAct");
			$(".contMenuMov").fadeOut();
		}
		else{
			$(this).addClass("menuAct");
			$(".contMenuMov").fadeIn();
		}
	});
	$(".contOpcMov a").on("click", function(){
		$(".contMenuMov").fadeOut();
		$(".menuMov").removeClass("menuAct");
	});
	$(".lista").on("click", function(){
		cambiaP($(this).attr("id"));
	});
	function cambiaP (id) {
		$(".lista").removeClass("activo");
		$("#" + id).addClass("activo");
		$(".opcList").hide();
		$("." + id).show();
	}
});
$(window).load(function(){
	$(".contLoading").animate({"opacity" : "0"}, 1500, function(){
		$(".contGenPag").css({"overflow" : "visible"});
		$(".contLoading").css({"z-index" : "1"});
	});
	$('.flexslider').flexslider({
    	animation: "fade"
  	});
  	$('.SlideClientes').owlCarousel({
	    loop: true,
	    margin: 0,
	    items: 1,
	    autoplay:true,
	    autoplayTimeout: 8000,
	    autoplayHoverPause:true
	})
	var owl = $('.SlideClientes').data('owlCarousel');
	$('.slidNext').on('click', function(){
	  owl.next();
	});
	$('.slidPrev').on('click', function(){
	  owl.prev();
	});
	$('.contLogos span').click(function() {
    	$('.SlideClientes').trigger('to.owl.carousel', [($(this).attr("data-slide") - 1),300]);
	})
});
/*** Efecto del Scroll Down ***/
$(function(){
     $('a[href*=#]').click(function() {
     if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'')
        && location.hostname == this.hostname) {
            var $target = $(this.hash);
            $target = $target.length && $target || $('[name=' + this.hash.slice(1) +']');
            if ($target.length) {
                var targetOffset = $target.offset().top;
                $('html,body').animate({scrollTop: targetOffset}, 1000);
                return false;
            }
       }
   });
});
/*** Control del los menus WEB Y MOBILE ***/ 
$(window).resize(function() {
  if($(window).width() < 1195){
  	$(".menuWeb").removeClass("menuAct");
	$(".contMenu a").addClass("fin").css({"opacity" : "0"});
	setTimeout(function(){
		$(".contMenu a").removeClass("tiempo");
	}, 300);
  }
  else{
  	$(".menuMov").removeClass("menuAct");
	$(".contMenuMov").fadeOut();
  }
});






















