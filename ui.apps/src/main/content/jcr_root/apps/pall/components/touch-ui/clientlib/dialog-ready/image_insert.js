(function ($, $document) {
    "use strict";

    $document.on("dialog-ready", function(e) {
        insertImages($('.cq-dialog-tiles-radiogroup'));
    });

    function insertImages(radio_tiles){
        if(radio_tiles){
            $(radio_tiles).find('.coral-Radio').each(function(){
                //fix style
                $(this).css("height","220px");
                //find id number to get the correct image layout
                var id_number = $(this).find('input:radio').attr('id');
                //insert layout image into radio element
                var target = $(this).find('.coral-Radio-description');
                $("<img src='/etc/designs/pall/img/quilt-layouts/"+id_number+"-tiles.png' style='height: 200px; width: 350px;'>").insertAfter(target);
            });
        }       
    }

})($, $(document));