var parallax = {

    // makes the parallax elements
    parallaxIt: function() {

        // create variables
        var $fwindow = $(window);
        var scrollTop = window.pageYOffset || document.documentElement.scrollTop;

        // on window scroll event
        $fwindow.on('scroll resize', function() {
            scrollTop = window.pageYOffset || document.documentElement.scrollTop;
        });

        // for each of content parallax element
        $('[data-type="content"]').each(function(index, e) {
            var $contentObj = $(this);
            var fgOffset = parseInt($contentObj.offset().top);
            var yPos;
            var speed = ($contentObj.data('speed') || 1);

            var objHeight = $contentObj.height();
            var isSmall = $contentObj.parent().hasClass('parallax-component--small');
            var isMobile = window.matchMedia("(max-width: 480px)").matches;

            $fwindow.on('scroll resize', function() {
                yPos = fgOffset - scrollTop - 100;
                if (isSmall) {
                    yPos -= objHeight;
                }
                if (isMobile) {
                    yPos -= 100;
                }
                if (yPos < 0) {
                    yPos = 0;
                }
                $contentObj.css('top', yPos);
            });
        });

        // for each of background parallax element
        $('[data-type="background"]').each(function() {
            var $backgroundObj = $(this);
            var bgOffset = parseInt($backgroundObj.offset().top);
            var yPos;
            var coords;
            var speed = ($backgroundObj.data('speed') || 0);

            $fwindow.on('scroll resize', function() {
                yPos = -((scrollTop - bgOffset) / speed);

                coords = '50% ' + yPos + 'px';

                $backgroundObj.css({
                    backgroundPosition: coords
                });
            });
        });

        // triggers winodw scroll for refresh
        $fwindow.trigger('scroll');
    },

    init: function() {
        $parallax = $('.parallax-component__image');
        $parallax.each(function(){
            var $parallaxObj = $(this);
            var pathToImage = $parallaxObj.data('image-src');
            $parallaxObj.css('background-image', 'url("' + pathToImage + '")');
        });
        this.parallaxIt();
    },

    needToInit: function() {
        return $('.parallax-component').length > 0;
    },
};
