var bgImageHelper = {

    transparency: 0.5,
    colorPallete: {
        'bg-pallblue': 'rgba(24, 77, 157,0.6)',
        'bg-aerospace': 'rgba(0,174,239,0.6)',
        'bg-power': 'rgba(233,111,20,0.6)',
        'bg-biopharma': 'rgba(83,46,98,0.6)',
        'bg-food': 'rgba(58,126,7,0.6)',
        'bg-chemicals': 'rgba(89,157,118,0.6)',
        'bg-industrial': 'rgba(33,42,74,0.6)',
        'bg-medical': 'rgba(27,139,234,0.6)',
        'bg-oilgas': 'rgba(84,24,1,0.6)',
        'bg-microelec': 'rgba(128,166,4,0.6)',
        'bg-laboratory': 'rgba(59,168,184,0.6)'
    },

    init: function() {
        $('.bg-image-helper').each(function() {
            var $bgContainer = $(this);
            var pathToImage = $bgContainer.data('image-src');
            $bgContainer.css('background-image', 'url("' + pathToImage + '")');
            $bgContainer.css('background-repeat', 'no-repeat');
        });
    },

    setBgImageAndColor: function($bgContainer, colorKey) {
        var pathToImage = $bgContainer.data('image-src');
        $bgContainer.css('background', 'url("' + pathToImage + '") 0 0/cover ' + this.colorPallete[colorKey]);
    },

    setBgImageRemoveColor: function($bgContainer) {
        var pathToImage = $bgContainer.data('image-src');
        $bgContainer.css('background', 'url("' + pathToImage + '") 0 0/cover');
    },

    needToInit: function() {
        return $('.bg-image-helper').length > 0;
    }
};
