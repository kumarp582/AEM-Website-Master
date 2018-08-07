var basicHeader = {

    init: function() {
        this.setUpRibbon();
        $( window ).on('resize', this.setUpRibbon.bind(this));
    },

    setUpRibbon: function() {
        var $headerTextBox = $('.header-basic__content__text-box__container')
        var _leftOffSet = $headerTextBox.offset().left;
        var _ribbonOffSet = 30;
        //change polygon size for desktop
        if (!window.matchMedia("(max-width: 480px)").matches) {
            _ribbonOffSet = _ribbonOffSet * 2;
            
        }
        var _polygon = 'polygon(0 0, 100% 0, 100% 90%, '+ (_leftOffSet + _ribbonOffSet * 2) 
                +'px 90%, ' + (_leftOffSet + _ribbonOffSet)+'px 100%, '+ _leftOffSet 
                + 'px 90%, 0 90%)';
        //setup ribbon for basic header
        $('.header-basic__content').css('-webkit-clip-path', _polygon);
    },

    needToInit: function() {
        return $('.header-basic__content__text-box__container').length > 0;
    }
};