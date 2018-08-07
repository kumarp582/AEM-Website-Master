"use strict";
use(function () {
    var youtubevideos = {};


    youtubevideos.youtubeData = granite.resource.properties["multivideos"];
    
    if(granite.resource.properties["backgroundstyle"]=='backgroundimage'){
    youtubevideos.backgroundStyle="background-image:url("+granite.resource.properties["backgroundImage"]+")";
    } else {
        youtubevideos.backgroundStyle="background-color:"+granite.resource.properties["color"];
    }


	youtubevideos.youtubeItems = [];
    youtubevideos.playlist='';
    var playData = '';

    if (youtubevideos.youtubeData) {
        console.log('first step');
        youtubevideos.firstVideo=JSON.parse(youtubevideos.youtubeData[0]);
         youtubevideos.firstVideo = youtubevideos.firstVideo.youtubemultilink;
        console.log('second step'+youtubevideos.firstVideo);
        for (var i = 1; i < youtubevideos.youtubeData.length; i++) {
            var video = JSON.parse(youtubevideos.youtubeData[i]);
            if(i+1<youtubevideos.youtubeData.length) {
            playData=playData.concat(video.youtubemultilink+",");
            } else {
playData=playData.concat(video.youtubemultilink);
            }
        }
    }

console.log('third step'+playData);
    youtubevideos.playlist=playData;
    return youtubevideos;
});