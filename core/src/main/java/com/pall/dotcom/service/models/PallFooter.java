package com.pall.dotcom.service.models;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;

import com.day.cq.wcm.api.Page;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

/**
 * 
 * @author 3|Share - Upen
 *
 */
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PallFooter {

    @Inject @Via("resource") 
    private Resource image;
    
    @Inject @Via("resource") @Named("links1")
    private List<Resource> pallLinks;
    
    @Inject @Via("resource") @Named("links2")
    private List<Resource> industriesLinks;
    
    private String logoImagePath;
    
    @Inject
    Page currentPage;
    
    private PallIndustryPage industryPage;
    
    private PallHomePage homePage;

    @PostConstruct
    public void init() {
        Resource res = currentPage.getContentResource();
        //Adapt the page to industry page first to see whether this is under an Industry Page
        if(res!=null){
            industryPage = res.adaptTo(PallIndustryPage.class);
            homePage = res.adaptTo(PallHomePage.class);
        }
        if (image != null) {
            ValueMap props = image.adaptTo(ValueMap.class);
            this.logoImagePath = props.get("fileReference", String.class);
        }
    }

    public PallIndustryPage getIndustryPage() {
        return industryPage;
    }

    public PallHomePage getHomePage() {
        return homePage;
    }

    public String getLogoImagePath(){
        return this.logoImagePath;
    }

    public List<Resource> getPallLinks() {
        return pallLinks;
    }

    public List<Resource> getIndustriesLinks() {
        return industriesLinks;
    }

    public List<Resource> getIndustriesLinksFirst() {
        return industriesLinks!=null?industriesLinks.subList(0,(industriesLinks.size()/2)+industriesLinks.size()%2):null;
    }

    public List<Resource> getIndustriesLinksSecond() {
        return industriesLinks!=null?industriesLinks.subList((industriesLinks.size()/2)+industriesLinks.size()%2,industriesLinks.size()):null;
    }
}
