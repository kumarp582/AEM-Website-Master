var externalVideoPlayer = {

    ytAPIURL: 'https://www.googleapis.com/youtube/v3/videos?id={YTID}&key={YTAPIKEY}&fields=items(id,snippet(description))&part=snippet',

    ytPlayer: null,

    init: function() {
        this.$el = $('.external-video-player__container__content__text-box');
        $( document ).on( 'YTIFAPIReady', this.onIFrameReady.bind(this));
    },

    onIFrameReady: function() {
        ytPlayer = new YT.Player('youtube-iframe', {
            events: {
              'onReady': this.onPlayerReady.bind(this),
              'onStateChange': this.onVideoChange.bind(this)
            }
        });
    },

    onPlayerReady: function() {
        this.setupVideoTitle();
        this.setupVideoDescription();
    },

    onVideoChange: function(event) {
        if(event.data && event.data == -1) {
            this.setupVideoTitle();
            this.setupVideoDescription();
        }
    },

    setupVideoTitle: function() {
        var videoTitle = ytPlayer.getVideoData().title;
        this.$el.find('.external-video-player__container__content__text-box__title').text(videoTitle);      
    },

    setupVideoDescription: function() {
        var youTubeURL = this.ytAPIURL.replace('{YTID}', ytPlayer.getVideoData().video_id)
                                        .replace('{YTAPIKEY}', PallConfig.youTubeAPIKey);
        $.ajax({
            'async': true,
            'context': this,
            'global': false,
            'url': youTubeURL,
            'dataType': "json",
            'success': function(data) {
                if(data && data.items[0] && data.items[0].snippet.description) {
                    var description = data.items[0].snippet.description;
                    this.$el.find('.external-video-player__container__content__text-box__text').text(description);
                }
                
            }
        });
    },

    needToInit: function() {
        return $('#youtube-iframe').length > 0 &&
        $('.external-video-player__container__content__text-box').length > 0;
    }
};