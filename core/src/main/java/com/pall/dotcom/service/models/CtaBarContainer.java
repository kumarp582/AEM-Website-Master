package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CtaBarContainer {
	@Inject @Named("ctabar1")
	private Resource ctabar1Res;
	
	@Inject @Named("ctabar2")
    private Resource ctabar2Res;
	
	@Inject @Named("ctabar3")
    private Resource ctabar3Res;
	
	@Inject @Named("ctabar4")
    private Resource ctabar4Res;
	
	private CtaBarItem cta1, cta2, cta3, cta4;
	
	@PostConstruct
	public void init(){
		
		if(ctabar1Res!=null){
			 cta1 = ctabar1Res.adaptTo(CtaBarItem.class);
		}
		if(ctabar2Res!=null)
            cta2 = ctabar2Res.adaptTo(CtaBarItem.class);
		if(ctabar3Res!=null)
            cta3 = ctabar3Res.adaptTo(CtaBarItem.class);
		if(ctabar4Res!=null)
            cta4 = ctabar4Res.adaptTo(CtaBarItem.class);
	}

	public CtaBarItem getCta1() {
		return cta1;
	}

	public CtaBarItem getCta2() {
		return cta2;
	}

	public CtaBarItem getCta3() {
		return cta3;
	}

	public CtaBarItem getCta4() {
		return cta4;
	}

  

}
