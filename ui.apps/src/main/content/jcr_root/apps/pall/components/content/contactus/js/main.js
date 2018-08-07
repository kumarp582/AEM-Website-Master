var contactUs={

 

  init:function() {
	  
	  
	  if (!String.prototype.trim) {
	    (function() {
	      // Make sure we trim BOM and NBSP
	      var rtrim = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g; 
	      String.prototype.trim = function() {
	        return this.replace(rtrim, '');
	      };
	    })();
	  }

	  [].slice.call( document.querySelectorAll( '.form-group .form-control' ) ).forEach( function( inputEl ) {
	    // in case the input is already filled..
	    if( inputEl.value.trim() !== '' ) {
	      classie.add( inputEl.parentNode, 'input-filled' );
	    }

	    // events:
	    inputEl.addEventListener( 'focus', onInputFocus );
	    inputEl.addEventListener( 'blur', onInputBlur );
	  } );

	  function onInputFocus( ev ) {
	    classie.add( ev.target.parentNode, 'input-filled' );
	  }

	  function onInputBlur( ev ) {
	    if( ev.target.value.trim() === '' ) {
	      classie.remove( ev.target.parentNode, 'input-filled' );
	    }
	  }

      $('.industry-contact-us-wrapper').find('div[data-industry]').each(function(){

        var industryText =  $(this).attr('data-industry').replace(/[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi, '');
          industryText = industryText.replace(/ /g,'')
               $(this).attr('data-industry',industryText);
      });
      $('.form-control').on('focus blur', function (e) {
    $(this).parents('.form-group').toggleClass('focused', (e.type === 'focus' || this.value.length > 0));
}).trigger('blur');
      
      
     
      
     
   var industryGroup = $('div[data-industry]');
   industryGroup.each(function(){
	   var formGroup ='';
	   var self = $(this);   
	   self.find('form').find('input').each(function(){
		   var inputSelf = $(this);
    	  var placeholder = inputSelf.attr('placeholder');
    	  if(placeholder!=undefined && ''!=placeholder){
    		  inputSelf.removeAttr(placeholder);
    	  var label = "<label class='control-label requiredField' for='"+placeholder+"'>"+placeholder+"<span class='asteriskField'>*</span></label>"
    	var element=inputSelf[0];
    	  element = element.outerHTML;
    	   formGroup =formGroup + "<div class='form-group'>"+element+label+"</div>"
    	 
    	  
    	   inputSelf.remove();
    	  }
      });
      
      self.find('form').find('select').each(function(){
    	var element=$(this)[0];
    	  element = element.outerHTML;
    	   formGroup =formGroup + "<div class='form-group select-control'>"+element+"</div>"
    	 
    	  
    	   $(this).remove();
      });
      
      self.find('form').find('input').each(function(){
     	     if($(this).attr('type')=='submit'){
    	       	   formGroup =formGroup + "<div class='form-group submit'><div><button class='btn' name='submit' type='submit'><div><span class='icon-checkmark'></span> <span><span>"+$(this).val()+" <span>your information</span></span></span></div></button></div></div>"
     	 
     	  
     	   $(this).remove();
     	     }
       });
       
      
      
      self.find('form').prepend($(formGroup));
     self.find('form').find('select').addClass('select');
      self.find('form').find('select').addClass('form-control');
      
      self.find('form').find('select').each(function(){
     	
     	  var item =  $(this).closest('div[data-industry]')[0];
        if(item.style.backgroundColor!=undefined) {
  $(this).attr('style','background:'+item.style.backgroundColor);
        }

       });
  });
      
      $('.industry-contact-us-wrapper').find('form').find('br').remove();
      if($('.form-group').find('input').is("[placeholder]")){
    	  $('.form-group').find('input').removeAttr('placeholder');
	  }
      
      $('.industry-contact-us-wrapper').find('form').find('input').change(function(){
      	if($(this).val().length>0){
      		$(this).closest('.form-group').find('label').attr('style',"top: 0em;font-size: 11px;");
      		
      	} else{
      		$(this).closest('.form-group').find('label').removeAttr('style');
      	}
      	 
       });
      $('.industry-contact-us-wrapper').find('form').find('input').focusin(function(){
        	$(this).closest('.form-group').find('label').attr('style',"top: 0em;font-size: 11px;");
        		
        	
        	 
         });
      
      $('.industry-contact-us-wrapper').find('form').find('input').focusout(function(){
    		if($(this).val().length>0){
          		$(this).closest('.form-group').find('label').attr('style',"top: 0em;font-size: 11px;");
          		
          	} else{
          		$(this).closest('.form-group').find('label').removeAttr('style');
          	}
      		
      	
      	 
       });
    
    var industryTabs=$('.industry-tabs');  
    var indContactUs = $('.industry-contact-us-wrapper');
    industryTabs.on('click', 'a', function(e) {
      e.preventDefault();
   var industryTabsAnchor =$('.industry-tabs a');
   var contactUs = $('.industry-contact-us');
   industryTabsAnchor.removeClass('active');
   var self= $(this);
   industryTabsAnchor.attr("onmouseout","this.style.background='#FFF'");

       self.removeAttr('onmouseout');
       industryTabsAnchor.attr('style','background:#FFF');
        self.attr('style','background:'+self.data('color'));

      var industry =self.data('industry');
               var industryText = industry.replace(/[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi, '');
          industryText = industryText.replace(/ /g,'');
      self.addClass('active');


      contactUs.addClass('hidden');
      indContactUs.addClass(industryText);
      indContactUs.find("div[data-industry]").removeClass('show')
      indContactUs.find("div[data-industry]").addClass('hidden')
      indContactUs.find("div[data-industry="+industryText+"]").removeClass('hidden');
      indContactUs.find("div[data-industry="+industryText+"]").addClass('show');
	  var item = indContactUs.find("div[data-industry="+industryText+"]")[0];
      if(item.style.backgroundColor!=undefined) {
    	  indContactUs.attr('style','background:'+item.style.backgroundColor);
      }
    });
    
var indSelectDropDown = $('.industry-select-dropdown');
indSelectDropDown.on('change', '#industry-select', function(e) {
	var self = $(this);
      var industry = self.val();
         var industryText = industry.replace(/[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi, '');
          industryText = industryText.replace(/ /g,'');

          contactUs.addClass('hidden');

          indSelectDropDown.find('option').each(function(){
               var industry = $(this).val();
         var industryText = industry.replace(/[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi, '');
          industryText = industryText.replace(/ /g,'');
          indContactUs.removeClass(industryText);
             });
         indContactUs.addClass(industryText);
         indContactUs.find("div[data-industry="+industryText+"]").removeClass('hidden');
         indContactUs.find("div[data-industry="+industryText+"]").addClass('show');
        var item = indContactUs.find("div[data-industry="+industryText+"]")[0];
        if(item.style.backgroundColor!=undefined) {
        	indContactUs.attr('style','background:'+item.style.backgroundColor);
        }
    });
      var firstElement =  $('.industry-tabs a')[0];
    $(firstElement).click();
      var item = indContactUs.find("div[data-industry]")[0];
        if(item.style.backgroundColor!=undefined) {
        	indContactUs.attr('style','background:'+item.style.backgroundColor);
        }



  }

}
