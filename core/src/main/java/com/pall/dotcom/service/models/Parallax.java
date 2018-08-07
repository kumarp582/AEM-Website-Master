package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import com.pall.dotcom.service.enums.Color;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Parallax {

	@Inject 
    private Resource image;
	
	@Inject 
	private String color;
	
	private String imagePath;
	
	private Color txtColor;
	
	@PostConstruct
	public void init(){
		if(image!=null){
			ValueMap props = image.adaptTo(ValueMap.class);
			this.imagePath = props.get("fileReference", String.class);
		}
		if(!StringUtils.isEmpty(color))
		    this.txtColor = Color.valueFromRGB(color);
		else
		    this.txtColor = Color.WHITE;
	}

	public String getImagePath() {
		return imagePath;
	}

    public String getColor() {
        return txtColor.getCssClass();
    }

}
