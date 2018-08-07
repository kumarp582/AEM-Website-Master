package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;

import com.pall.dotcom.service.enums.Color;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CtaBar {
	 
	@Inject @Via("resource")
	private String backgroundColor;
	
	@Inject @Via("resource")
	private String alternate1;
	@Inject @Via("resource")
	private String alternate2;
	@Inject @Via("resource")
	private String alternate3;
	@Inject @Via("resource")
	private String alternate4;
    
    private Color color;
    
    
    @PostConstruct
    public void init() {
        if(backgroundColor != null) {
            this.color = Color.valueFromRGB(backgroundColor);            
        }
    }

    public Color getColor() {
        return this.color;
    }
  
}
