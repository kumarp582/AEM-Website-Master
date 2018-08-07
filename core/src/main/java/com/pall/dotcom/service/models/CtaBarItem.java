package com.pall.dotcom.service.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CtaBarItem {
	@Self
	private Resource resource;
	
	@Inject
    private String title;
	
	@Inject
    private String subtitle;
    
    @Inject
    private String link;
    
    @Inject
    private String icon;
    
    @Inject
    private String alternate;



	public String getTitle() {
		return title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public String getLink() {
		return link;
	}

	public String getIcon() {
		return icon;
	}

	public String getAlternate() {
		return alternate;
	}
}
