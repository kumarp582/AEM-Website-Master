var topNav = {

    $subNav: $('.topnav__nav__container__items__item--industries__subnav'),
    $subNavLinks: $('.topnav__nav__container__items__item--industries__subnav__item__link'),
    $subNavLinksLevel3: $('.topnav__nav__container__items__item--industries__subnav__item__third-level'),
    $overlay: $('.topnav__nav__overlay'),
    sticky: false,

    init: function() {
        // Mobile - subnav menu
        $('.subnav__hook').on(
            'click',
            this.toggleSubnav.bind(this)
        );


        // Mobile button back
        $('.button-back').on(
            'click',
            this.goBack.bind(this)
        );

        // Mobile button search
        $('.topnav__nav__container--mobile__items__item--search').on(
            'click',
            this.toggleSearch.bind(this)
        );

        $('.topnav__nav__container__items__item--industries__link').on(
            'click',
            topNav.onIndustryMainButtonClick
        );

        topNav.$overlay.on(
            'click',
            //topNav.onIndustryMainButtonClick
            topNav.closeAll
        );

        topNav.$subNavLinks.on('mouseover', function(event) {
            topNav.$subNavLinksLevel3.removeClass('active');
            $(event.currentTarget).parent()
                .find('.topnav__nav__container__items__item--industries__subnav__item__third-level')
                .addClass('active');
        });
        topNav.$subNavLinksLevel3.on('mouseleave', function(event) {
            $(event.currentTarget).parent()
                .find('.topnav__nav__container__items__item--industries__subnav__item__third-level')
                .removeClass('active');
        });

        //sticky header
        $(window).on('scroll', function(e) {
            if ($(this).scrollTop() > 70 && !topNav.sticky) {
                topNav.sticky = true;
                $('.topnav__nav').addClass('topnav__nav--sticky-transition');
                setTimeout(function() {
                    $('.topnav__nav').removeClass('topnav__nav--sticky-transition');
                    if ($(this).scrollTop() > 70) {
                        $('.topnav__nav').addClass('topnav__nav--sticky');
                        $('header').css({
                            'padding-top': '40px'
                        });
                    }
                }, 100);
            } else {
                if ($(this).scrollTop() < 70) {
                    $('.topnav__nav').removeClass('topnav__nav--sticky');
                    $('header').css({
                        'padding-top': '0'
                    });
                    topNav.sticky = false;
                }

            }
        })
    },

    closeAll: function() {
        $('.topnav__nav').find('.active').toggleClass('active');

        $('.topnav__nav')
            .find('.topnav__nav__overlay--mobile--visible')
            .toggleClass('topnav__nav__overlay--mobile--visible');

        topNav.$overlay.toggleClass('topnav__nav__overlay--visible');
        topNav.toggleMainNavState();
    },

    toggleMainNavState: function() {
        var $mainNavButton = $('.topnav__nav__container--mobile__items__item--menu__icon');
        if ($mainNavButton.is(':visible')) {
            $mainNavButton
                .removeClass('fa-close')
                .addClass('fa-navicon');
        }
    },

    goBack: function(event) {
        event.stopPropagation();
        var $subNav = $(event.currentTarget).closest('.active');

        $subNav.removeClass('active');

        var $overlay = $subNav.closest('.active').children('.topnav__nav__overlay--mobile');

        if (!$subNav.hasClass('first')) {
            $overlay.toggleClass('topnav__nav__overlay--mobile--visible');
        }
    },

    toggleSearch: function(event) {
        event.stopPropagation();
        var $clickedItem = $(event.currentTarget);
        var $searchContainer = $clickedItem.children('.topnav__nav__container__search--mobile');

        if ($searchContainer.hasClass('active')) {
            $searchContainer.removeClass('active');
        } else {
            $searchContainer.addClass('active');
        }
        topNav.$overlay.toggleClass('topnav__nav__overlay--visible');
    },

    toggleSubnav: function(event) {
        event.stopPropagation();
        var $clickedItem = $(event.currentTarget);
        var $subNav = $clickedItem.children('.topnav__nav__container--mobile__subnav');
        var $ovelay = $clickedItem.parent().children('.topnav__nav__overlay--mobile');

        if ($subNav.hasClass('active')) {
            $subNav.removeClass('active');
            if ($clickedItem.hasClass('first')) {
                $clickedItem.children('span')
                    .removeClass('fa-close')
                    .addClass('fa-navicon');
                topNav.$overlay.toggleClass('topnav__nav__overlay--visible');
            }
        } else {
            $subNav.addClass('active');
            if ($clickedItem.hasClass('first')) {
                $clickedItem.children('span')
                    .removeClass('fa-navicon')
                    .addClass('fa-close');
                topNav.$overlay.toggleClass('topnav__nav__overlay--visible');
            } else {
                $ovelay.toggleClass('topnav__nav__overlay--mobile--visible');
            }
        }
    },

    onIndustryMainButtonClick: function(event) {
        event.preventDefault();
        topNav.$overlay.toggleClass('topnav__nav__overlay--visible');
        topNav.$subNav.toggleClass('active');
        topNav.$subNavLinksLevel3.removeClass('active');
    },

    needToInit: function() {
        return $('.topnav__nav').length > 0;
    }
};
