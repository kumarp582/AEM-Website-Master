package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.day.cq.commons.jcr.JcrConstants;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FeaturedSolutionsBlock {

    @Inject 
    private String blockType;

    @Inject 
    private String productName;

    @Inject 
    private String productDescription;

    @Inject 
    private String productPartNumber;

    @Inject 
    private String productPrice;

    @Inject 
    private String productButtonLink;

    @Inject 
    private String productImagePath;

    @Inject 
    private String segmentName;

    @Inject 
    private String segmentDescription;

    @Inject 
    private String learnMoreLink;

    @Inject 
    private String learnMoreLinkSubtitle;

    private PallSegmentPage segmentPage;

    @Self
    private Resource resource;
    
    @PostConstruct
    public void init(){
        //Identify the segment page
        if(!StringUtils.isEmpty(learnMoreLink)){
            Resource segmentPageRes = resource.getResourceResolver().getResource(learnMoreLink + "/" + JcrConstants.JCR_CONTENT);
            if(segmentPageRes!=null) {
                segmentPage = segmentPageRes.adaptTo(PallSegmentPage.class);

                if (this.segmentName == null) {
                    this.segmentName = segmentPage.getPageContent().getTitle();
                }

                if (this.segmentDescription == null) {
                    this.segmentDescription = segmentPage.getHeaderDescription();
                }
            }
        }

    }

    public String getBlockType() {
        return blockType;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductPartNumber() {
        return productPartNumber;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductButtonLink() {
        return productButtonLink;
    }

    public String getProductImagePath() {
        return productImagePath;
    }

    public String getSegmentName() {
        return segmentName;
    }

    public String getSegmentDescription() {
        return segmentDescription;
    }

    public String getLearnMoreLink() {
        return learnMoreLink;
    }

    public String getLearnMoreLinkSubtitle() {
        return learnMoreLinkSubtitle;
    }

    public PallSegmentPage getSegmentPage() {
        return segmentPage;
    }
   
}