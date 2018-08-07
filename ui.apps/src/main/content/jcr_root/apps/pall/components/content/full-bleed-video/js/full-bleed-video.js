var fullBleedVideo = {

    init: function() {
        this.$el = $('.full-bleed-video');
        var player = this.$el.find('.full-bleed-video__content__responsive-player');
        var videoId = player.data('videoid');
        var options = {
            videoURL: videoId,
            containment:'self',
            showControls: false,
            autoPlay: true,
            loop: true,
            mute: true,
            startAt: 0,
            opacity: 1,
            optimizeDisplay: true,
            showYTLogo: false,
            addRaster: false,
            quality:'default'
        };
        player.YTPlayer(options);
    },

    needToInit: function() {
        return $('.full-bleed-video').length > 0;
    }
};