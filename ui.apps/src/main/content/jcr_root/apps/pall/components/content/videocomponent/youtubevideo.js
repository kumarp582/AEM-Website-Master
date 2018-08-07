"use strict";
use(function () {
    var youtubevideos = {};


    youtubevideos.youtubeData = granite.resource.properties["multivideos"];
     youtubevideos.style = granite.resource.properties["height"];
    if(youtubevideos.style && youtubevideos.style>0) {
        youtubevideos.style="height:"+youtubevideos.style+"px";
    }else {
        youtubevideos.style="padding-bottom:56.23%";
    }


    if(granite.resource.properties["backgroundstyle"]=='backgroundimage'){
    youtubevideos.backgroundStyle="background-image:url("+granite.resource.properties["backgroundImage"]+")";
    } else {
        youtubevideos.backgroundStyle="background-color:"+granite.resource.properties["color"];
    }
var width = granite.resource.properties["width"];
    youtubevideos.playerWidth = width*3/4;
    youtubevideos.suggestionWidth = width*1/4;

	youtubevideos.youtubeItems = [];
    youtubevideos.playlist='';
    var playData = '';


    if (youtubevideos.youtubeData) 
    {

      //  youtubevideos.firstVideo=JSON.parse(youtubevideos.youtubeData[0]);
        // youtubevideos.firstVideo = youtubevideos.firstVideo.youtubemultilink;

        for (var i = 0; i < youtubevideos.youtubeData.length; i++)
        {
            var video = JSON.parse(youtubevideos.youtubeData[i]);
            if(i+0<youtubevideos.youtubeData.length) {
            playData=playData.concat(video.youtubemultilink+",");
            } 
            else {
playData=playData.concat(video.youtubemultilink);
            }
        }
    }


    youtubevideos.playlist=playData;
    return youtubevideos;
});