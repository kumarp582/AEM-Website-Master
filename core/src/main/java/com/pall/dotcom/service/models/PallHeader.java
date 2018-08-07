package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.day.cq.wcm.api.Page;
import com.pall.dotcom.service.enums.ResourceType;

/**
 * 
 * @author 3|Share - David Truchet
 *
 */
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PallHeader {

    private String ribbonState = "enable";

    private String template = "industry";

    @Self
    private Resource industryHeader;

    @Inject
    public Page currentPage;

    private String description;

    private String backgroundImage;
    
    private String tactileGraphic;
    
    private Boolean showTactile;
    
    private Boolean isBasic;
    
    private Boolean isSolutionDetail;
    
    private Boolean isArticle;
    
    private PallBasePage pallPage;

    @PostConstruct
    public void init() {
        this.pallPage = currentPage.getContentResource().adaptTo(PallBasePage.class);
        boolean isIndustry = pallPage.getPageContent().getSlingResourceType().equals(ResourceType.INDUSTRY.getTemplate());
        boolean isSegment = pallPage.getPageContent().getSlingResourceType().equals(ResourceType.SEGMENT.getTemplate());
        this.isArticle = ResourceType.isArticle(ResourceType.valueFromTemplate(pallPage.getPageContent().getSlingResourceType()));
        this.isSolutionDetail = pallPage.getPageContent().getSlingResourceType().equals(ResourceType.SOLUTION_DETAIL.getTemplate());
        this.ribbonState = isIndustry ? "enable" : (isSegment ? "disable" : pallPage.getPageContent().getShowRibbon());
        this.template = (isIndustry || StringUtils.equals(ribbonState, "enable")) ? "industry" : "basic";
        this.showTactile = isIndustry || isSegment || StringUtils.equals("true", pallPage.getPageContent().getShowTactile());
        this.isBasic = !isIndustry && !isSegment && !StringUtils.equals(ribbonState, "enable");
        if(null!=industryHeader){
            ValueMap properties = this.industryHeader.adaptTo(ValueMap.class);
            this.description = properties.get("description", String.class);
            this.backgroundImage = properties.get("backgroundImage", String.class);
            this.tactileGraphic = properties.get("tactileGraphic", String.class);
        }
    }

    public String getRibbonState() {
        return ribbonState;
    }

    public Resource getIndustryHeader() {
        return this.industryHeader;
    }

    public String getDescription() {
        return description;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public String getTactileGraphic() {
        return tactileGraphic;
    }

    public String getTemplate() {
        return template;
    }
    
    public Boolean showTactile() {
        return this.showTactile;
    }

    public Boolean isBasic() {
        return this.isBasic;
    }

    public Boolean getIsSolutionDetail() {
        return isSolutionDetail;
    }
    
    public Boolean getIsArticle() {
        return isArticle;
    }
    
    public PallBasePage getPallPage() {
        return this.pallPage;
    }
}
