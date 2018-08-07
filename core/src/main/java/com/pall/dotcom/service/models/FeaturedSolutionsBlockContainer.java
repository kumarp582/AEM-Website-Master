package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FeaturedSolutionsBlockContainer {
	
	@Inject @Named("block1")
	private Resource block1Res;
	
	@Inject @Named("block2")
    private Resource block2Res;
	
	private FeaturedSolutionsBlock block1, block2;
	
	@PostConstruct
	public void init(){
		if(block1Res!=null)
		    block1 = block1Res.adaptTo(FeaturedSolutionsBlock.class);
		if(block2Res!=null)
            block2 = block2Res.adaptTo(FeaturedSolutionsBlock.class);
	}

    public FeaturedSolutionsBlock getBlock1() {
        return block1;
    }

    public FeaturedSolutionsBlock getBlock2() {
        return block2;
    }

}
