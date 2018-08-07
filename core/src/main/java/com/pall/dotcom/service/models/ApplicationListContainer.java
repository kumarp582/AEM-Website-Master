package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ApplicationListContainer {
    
    @Inject 
    private Resource bgImage;
    
    @Inject
    private String segmentPath1;
    
    @Inject
    private String segmentPath2;
    
    @Inject
    private String segmentPath3;
    
    private String bgImagePath;
    
    private PallIndustryPage industry;
    
    private ApplicationList applicationList1, applicationList2, applicationList3;
    
    @Self
    private Resource resource;
    
    @Inject
    private ResourceResolver resourceResolver;
    
    @PostConstruct
    public void init(){
        if(bgImage!=null){
            ValueMap props = bgImage.adaptTo(ValueMap.class);
            this.bgImagePath = props.get("fileReference", String.class);
        }
        this.industry = resource.adaptTo(PallIndustryPage.class);
        if(!StringUtils.isEmpty(segmentPath1))
            applicationList1 = resourceResolver.getResource(segmentPath1).adaptTo(ApplicationList.class);
        if(!StringUtils.isEmpty(segmentPath2))
            applicationList2 = resourceResolver.getResource(segmentPath2).adaptTo(ApplicationList.class);
        if(!StringUtils.isEmpty(segmentPath3))
            applicationList3 = resourceResolver.getResource(segmentPath3).adaptTo(ApplicationList.class);
    }
    
    public PallIndustryPage getIndustry(){
        return this.industry;
    }

    public String getBgImagePath(){
        return this.bgImagePath;
    }
    
    public ApplicationList getApplicationList1() {
        return applicationList1;
    }
    
    public ApplicationList getApplicationList2() {
        return applicationList2;
    }
    
    public ApplicationList getApplicationList3() {
        return applicationList3;
    }

}
