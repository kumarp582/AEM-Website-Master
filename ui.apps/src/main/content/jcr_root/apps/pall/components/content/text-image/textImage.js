/*global: use, granite*/
/**
 * Text and Image component JS Use-api script
 */
use(["/libs/wcm/foundation/components/utils/AuthoringUtils.js",
     "/libs/sightly/js/3rd-party/q.js"], function (AuthoringUtils, Q) {
    "use strict";//NOSONAR
    var textimage = {};
    
    var CONST = {
        PROP_ALIGNMENT: "alignment",
        PROP_TEXT: "text"
    };
    
    // The container CSS class name is what defines the alignment
    textimage.alignment = granite.resource.properties[CONST.PROP_ALIGNMENT] ||
            currentStyle.get(CONST.PROP_ALIGNMENT, "");
    
    var hasContentDeferred = Q.defer();
    if (granite.resource.properties[CONST.PROP_TEXT]) {
        hasContentDeferred.resolve(true);
    }
    granite.resource.resolve(granite.resource.path + "/image").then(function (imageResource) {
        if (imageResource.properties["fileReference"]) {
            hasContentDeferred.resolve(true);
        } else {
            granite.resource.resolve(granite.resource.path + "/image/file").then(function (localImage) {
                hasContentDeferred.resolve(true);
            }, function () {
                hasContentDeferred.resolve(false);
            });
        }
    }, function () {
        hasContentDeferred.resolve(false);
    });
    
    // Adding the constants to the exposed API
    textimage.CONST = CONST;
    
    textimage.isTouch = AuthoringUtils.isTouch;
    
    textimage.hasContent = hasContentDeferred.promise;
    
    return textimage;
    
});