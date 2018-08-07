var topicsTabContainer = {

    init: function() {
        var _this = this;
        // Topic Tabs Selection
        $('.topics-tab__container__tabs__topic').on(
                'click',
                'a.topic-selection',
                function(e) {
                    e.preventDefault();
                    var $this = $(this);
                    var topic = $this.attr('href');

                    var activeFontColorValue = $(this)
                            .data('active-font-color');

                    // Toggle Active Marker for Topics
                    var $activeTab = $('.topics-tab__container__tabs__topic a.topic-selection');

                    $activeTab.removeClass('active');

                    $activeTab
                            .each(
                                    function() {
                                        var $_this = $(this);
                                        var nonActiveFontColorValue = $_this
                                                .data('nonactive-font-color');
                                        $_this.css('color',
                                                nonActiveFontColorValue);
                                    });

                    $this.addClass('active');

                    $this.css('color', activeFontColorValue);

                    // Show / Hide Topics
                    $('.topics-tab__topic').hide();
                    $(topic).css("opacity", 0);
                    $(topic).fadeIn(150, function() {
                        // ////////////////////////////////////////////////////////////////////////////
                        // Create Slick Slider for each topic
                        _this.initSlickSliders(topic);
                        $(topic).css("opacity", 1);
                    });
                });

        // Init Sliders on pageload
        $('.topics-tab__topic:first').fadeIn(150, function() {
            _this.initSlickSliders('.topics-tab__topic');
        });
        
        $(window).resize(function() {
            var selectedTopic = $('.topic-selection.active').attr("href");
            $(selectedTopic).show();
        });
    },

    initSlickSliders : function( selector ) {
        // Check if the slick slider is already initialized so we don't try to
        // do it twice
        if (!($(selector).find('.carousel').hasClass('slick-initialized'))) {

            // Events defined before slick slider created
            $(selector).find('.carousel').on(
                    'init',
                    function(event, slick) {
                        var itemLink = $(slick.$slides.get(0)).find('a.btn')
                                .attr('href');
                        $(this)
                            .siblings('.topics-tab__topic__shared')
                            .find('.topics-tab__topic__shared__learn-more a.btn')
                            .attr('href', itemLink);
                    });

            $(selector).find('.carousel').on(
                    'afterChange',
                    function(event, slick, currentSlide, nextSlide) {
                        var itemLink = $(slick.$slides.get(currentSlide)).find(
                                'a.btn').attr('href');
                        $(this)
                            .siblings('.topics-tab__topic__shared')
                            .find('.topics-tab__topic__shared__learn-more a.btn')
                            .attr('href', itemLink);
                    });

            $(selector).find('.carousel').slick({
                infinite : false,
                slidesToShow : 3,
                slidesToScroll : 3,
                arrows : false,
                autoplay : false,
                dots : false,
                responsive : [ {
                    breakpoint : 840,
                    settings : {
                        slidesToShow : 1,
                        slidesToScroll : 1,
                        dots : true
                    }
                } ]
            });
        }

        // Refresh slick slider since they are usually hidden and it cannot
        // calculate the width/height because of the DOM
        $(selector).find('.carousel')[0].slick.refresh();
    },
    
    needToInit: function() {
        return $('.topics-tab__container').length > 0 && (typeof $.fn.slick !== 'undefined');
    }
};