var quilt = {

    animationSpeed: 230,
    $mainContainer: $('.quilt--background'),
    $quiltMain: $('.quilt-main'),
    $level1: $('.quilt--level-1'),
    $level2: $('.quilt--level-2'),
    $level1Tiles: $('.quilt--level-1 .quilt__tile'),
    $level2Tiles: $('.quilt--level-2 .quilt__tile'),
    $level1TilesWrapper: $('.quilt__tile--level-1 > .quilt__tile__wrapper'),
    $level2TilesWrapper: $('.quilt__tile--level-2 > .quilt__tile__wrapper'),
    isMobile: window.matchMedia("(max-width: 767px)").matches,

    init: function() {
        var _self = this;
        bgImageHelper.setBgImageAndColor(_self.$mainContainer, 'bg-pallblue');
        this.$level1Tiles.on('click', function(e) {
            if ($(e.target).hasClass('quilt__tile__button') || $(e.target).parents('.quilt__tile__button').length) {
                return;
            }
            var $quiltToShow;
            if (_self.isMobile) {
                $quiltToShow = $(this).data('level-id-mobile');
                _self.animationSpeed = 10;
            } else {
                $quiltToShow = $(this).data('level-id-desktop');
            }
            if (!$quiltToShow) {
                return;
            }
            $quiltToShow = $('#' + $quiltToShow);
            _self.performAnimation($quiltToShow);
        });
        $('.quilt__return').on('click', function() {
            _self.performAnimation()
        });
    },

    needToInit: function() {
        return $('.quilt-container').length > 0;
    },

    performAnimation: function($quiltToShow = null) {
        var _self = this;
        _self.$quiltMain.toggleClass('quilt-main--level-2');
        var $level2Tiles = $quiltToShow ? $quiltToShow.find('.quilt__tile') : null;
        var $level2TilesWrapper = $quiltToShow ? $quiltToShow.find('.quilt__tile__wrapper') : null;
        var showSecondLevel = $quiltToShow ? true : false;
        if (showSecondLevel) {
            _self.$level1TilesWrapper.toggleClass('inactive');
        } else {
            _self.$level2TilesWrapper.addClass('inactive');
        }
        setTimeout(function() {
            if (showSecondLevel) {
                _self.$level1Tiles.toggleClass('inactive');
                _self.$level1.toggleClass('inactive');
            } else {
                _self.$level2Tiles.addClass('inactive');
                _self.$level2.addClass('inactive');
            }
            if (_self.isMobile) {
                _self.$mainContainer.toggleClass('quilt--background-level-2');
            }
            setTimeout(function() {
                if (showSecondLevel) {
                    $level2Tiles.toggleClass('inactive');
                    bgImageHelper.setBgImageAndColor(_self.$mainContainer, $('.quilt__return:not(.inactive)').data('color-key'));
                    if (!_self.isMobile) {
                        _self.$level2.masonry({
                            itemSelector: '.quilt__tile--level-2'
                        });
                    }
                } else {
                    _self.$level1Tiles.toggleClass('inactive');
                    bgImageHelper.setBgImageAndColor(_self.$mainContainer, 'bg-pallblue');
                }
                setTimeout(function() {
                    if (showSecondLevel) {
                        $level2TilesWrapper.toggleClass('inactive');
                        _self.$level2.toggleClass('inactive');
                    } else {
                        _self.$level1TilesWrapper.toggleClass('inactive');
                        _self.$level1.toggleClass('inactive');
                    }
                }, _self.animationSpeed);
            }, _self.animationSpeed);
        }, _self.animationSpeed);
    }
}
