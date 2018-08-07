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
public class MiniSolutionBlock {

    @Inject
    private String viewProductLink;

    @Inject
    private String title;

    @Inject
    private String description;

    @Inject
    private String learnMoreLink;

    @Inject
    private String learnMoreLinkSubtitle;

    @Inject
    private Resource prodImage;

    @Inject
    private Resource bgImage;

    @Self
    private Resource resource;

    private String prodImagePath, bgImagePath;

    private PallIndustryPage industry;

    @PostConstruct
    public void init() {
        if (prodImage != null) {
            ValueMap props = prodImage.adaptTo(ValueMap.class);
            this.prodImagePath = props.get("fileReference", String.class);
        }
        if (bgImage != null) {
            ValueMap props = bgImage.adaptTo(ValueMap.class);
            this.bgImagePath = props.get("fileReference", String.class);
        }
        // Identify the industry from the solution page.
        if (!StringUtils.isEmpty(learnMoreLink)) {
            Resource indPageRes = resource.getResourceResolver()
                    .getResource(learnMoreLink + "/" + JcrConstants.JCR_CONTENT);
            if (indPageRes != null)
                industry = indPageRes.adaptTo(PallIndustryPage.class);
        }
        // If industry is not identified via solution page, identify the
        // industry from the product page.
        if (null == industry && !StringUtils.isEmpty(viewProductLink)) {
            Resource indPageRes = resource.getResourceResolver()
                    .getResource(viewProductLink + "/" + JcrConstants.JCR_CONTENT);
            if (indPageRes != null)
                industry = indPageRes.adaptTo(PallIndustryPage.class);
        }
    }

    public String getProdImagePath() {
        return prodImagePath;
    }

    public String getBgImagePath() {
        return bgImagePath;
    }

    public PallIndustryPage getIndustry() {
        return this.industry;
    }

    public String getViewProductLink() {
        return viewProductLink;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLearnMoreLink() {
        return learnMoreLink;
    }

    public String getLearnMoreLinkSubtitle() {
        return learnMoreLinkSubtitle;
    }

}