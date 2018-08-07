window.ns = window.ns || {};
ns.utils = ns.utils || {};
ns.components = ns.components || {};

/**
 * [Global Youtube onReady method]
 *
 */
window.onYouTubeIframeAPIReady = function() {
	//Triggering Custom YouTube IFrame Event
	if( !externalVideoPlayer.YTAPIReady ) {
		externalVideoPlayer.YTAPIReady = true;
		$( document ).trigger( "YTIFAPIReady" );
	}

	//Triggering Background Video Event
	if( !ytp.YTAPIReady ) {
		ytp.YTAPIReady = true;
		$( document ).trigger( "YTAPIReady" );
	}
}

$(document).ready(function() {
    countDown.needToInit() && countDown.init();
    parallax.needToInit() && parallax.init();
    externalVideoPlayer.needToInit() && externalVideoPlayer.init();
    fullBleedVideo.needToInit() && fullBleedVideo.init();
    bgImageHelper.needToInit() && bgImageHelper.init();
    quilt.needToInit() && quilt.init();
    miniSolutionBlock.needToInit() && miniSolutionBlock.init();
    topicsTabContainer.needToInit() && topicsTabContainer.init();
    topNav.needToInit() && topNav.init();
    basicHeader.needToInit() && basicHeader.init();
});