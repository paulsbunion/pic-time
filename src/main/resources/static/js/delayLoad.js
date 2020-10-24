jQuery(document).ready(function(){
    delay();
});

function delay() {
    var secs = 1;
    setTimeout('initFadeIn()', secs);
}

function initFadeIn() {
    jQuery("body").css("visibility","visible");
    jQuery("body").css("display","none");
    jQuery("body").fadeIn(1200);
}
