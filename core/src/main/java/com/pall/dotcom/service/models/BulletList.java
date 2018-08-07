package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;

import com.day.cq.wcm.api.Page;
import com.pall.dotcom.service.enums.ResourceType;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BulletList {

	@Inject
	public Page currentPage;
	
	@Inject @Via("resource")
    private Resource image;

	@Inject @Via("resource")
    private Resource backgroundImage;

    private String imagePath, bgImagePath;
    
    private PallIndustryPage industry;
    
	private String icon1;
	private String icon2;
	private String icon3; 
	
    @PostConstruct
    public void init() {
        if (image != null) {
            ValueMap props = image.adaptTo(ValueMap.class);
            this.imagePath = props.get("fileReference", String.class);
        }
        if (backgroundImage != null) {
            ValueMap props = backgroundImage.adaptTo(ValueMap.class);
            this.bgImagePath = props.get("fileReference", String.class);
        }    
        
        this.industry = currentPage.getContentResource().adaptTo(PallIndustryPage.class);
        PallBasePage page = currentPage.getContentResource().adaptTo(PallBasePage.class);
        boolean isHome = page.getPageContent().getSlingResourceType().equals(ResourceType.HOMEPAGE.getTemplate());
       
        this.icon1 = isHome ? page.getIcon1() : industry.getIcon1();
        this.icon2 = isHome ? page.getIcon2() : industry.getIcon2();
        this.icon3 = isHome ? page.getIcon3() : industry.getIcon3();       
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getBgImagePath() {
        return bgImagePath;
    }
    public PallIndustryPage getIndustry(){
        return this.industry;
    }
    
	public String getIcon1() {
		return icon1;
	}

	public String getIcon2() {
		return icon2;
	}

	public String getIcon3() {
		return icon3;
	}
}
