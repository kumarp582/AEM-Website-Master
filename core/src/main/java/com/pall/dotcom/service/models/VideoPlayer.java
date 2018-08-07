package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;

import com.day.cq.wcm.api.Page;
import com.pall.dotcom.service.enums.Color;

/**
 * 
 * @author 3|Share - David Truchet
 *
 */
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class VideoPlayer {

    @Inject
    private Page currentPage;
    
    @Inject @Via("resource")
    private String backgroundColor;
    
    private Color color;
    
    @PostConstruct
    public void init() {
        PallIndustryPage industryPage = currentPage.getContentResource().adaptTo(PallIndustryPage.class);
        if(backgroundColor != null) {
            this.color = Color.valueFromRGB(backgroundColor);
            if(!validColor(industryPage.getColor())) {
                this.color = industryPage.getColor();
            }
        }
    }
    
    private Boolean validColor(Color industryColor) {
        String cssClass = color.getCssClass();
        return cssClass.equalsIgnoreCase(industryColor.getCssClass()) ||
                cssClass.equalsIgnoreCase(Color.WHITE.getCssClass()) ||
                cssClass.equalsIgnoreCase(Color.CHARCOAL1.getCssClass());
    }
    
    public Color getColor() {
        return this.color;
    }
}
