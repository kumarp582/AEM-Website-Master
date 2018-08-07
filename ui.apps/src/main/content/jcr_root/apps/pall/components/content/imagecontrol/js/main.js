imageControl = {
		init : function(){
			 var id=".eyeimage";
			 $(id).each(function(){
				 var self = $(this);
				 self.vc3dEye({
			 
			        imagePath:self.attr('data-imagepath')+"/"+self.attr('data-prefix'),
			        totalImages:parseInt(self.attr('data-imagecount')),
			        imageExtension:self.attr('data-extension')
			    });
				 
			 });
		}
}