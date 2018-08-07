package com.pall.dotcom.service.models;

import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.day.cq.commons.jcr.JcrConstants;
import com.pall.dotcom.service.enums.Color;

/**
 * 
 * @author 3|Share - Upen
 *
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class QuiltIndustry extends QuiltMember {

    @Inject
    private String industryPage;

    @Inject
    private String industryTitle;

    @Inject
    private String industryDesc;

    @Inject
    private String industryColor;

    @Inject
    private Resource image;

    @Inject
    private String icon;

    private String industryImagePath;

    @Inject
    private ResourceResolver resourceResolver;
    
    @Self
    private Resource resource;
    
    private int order;
    
    private QuiltContainerLevel2 container;
    
    @PostConstruct
    public void init() {
        if (image != null) {
            ValueMap props = image.adaptTo(ValueMap.class);
            this.industryImagePath = props.get("fileReference", String.class);
        }
        
        if (!StringUtils.isEmpty(industryPage)) {
            Resource indPageRes = resourceResolver.getResource(industryPage + "/" + JcrConstants.JCR_CONTENT);
            if (null != indPageRes) {
                // If the Quilt page is selected with any of manual values are empty
                if(foundPartialOrNoManualInput()) {
                    parseIndustryPage(indPageRes);
                    parseIndustryHeader(indPageRes);
                }
                parseQuiltTiles(indPageRes);
            }
        }
        //find index
        if(resource!=null){
            Resource parent = resource.getParent();
            if(parent!=null){
                Iterator<Resource> itr = parent.listChildren();
                int count = 0;
                while(itr.hasNext()){
                    Resource res = itr.next();
                    if(res.getName().startsWith("quilt_industry"))
                        count++;
                    if(res.getName().equals(resource.getName())){
                        this.setOrder(count);
                        break;
                    }
                }
            }
        }
    }

    /**
     * @param none
     *            This method checks whether any of the industry related
     *            information is authored
     * @return true - if any of the manual input is left empty, so that the
     *         missing input can be retrieved from the industry page
     */
    private boolean foundPartialOrNoManualInput() {
        return StringUtils.isEmpty(industryTitle) || StringUtils.isEmpty(industryDesc)
                || StringUtils.isEmpty(industryImagePath) || StringUtils.isEmpty(icon);
    }

    /**
     * @param Resource
     *            indPageRes This method locates the industry header component
     *            to retrieve description and background image
     * @return
     */
    private void parseIndustryHeader(Resource indPageRes) {
        Resource headerResource = indPageRes.getChild("pall-header");
        if (null != headerResource) {
            ValueMap headerProps = headerResource.adaptTo(ValueMap.class);
            if (null!=headerProps && StringUtils.isEmpty(industryDesc))
                this.industryDesc = headerProps.get("description", String.class);
            if (null!=headerProps && StringUtils.isEmpty(industryImagePath))
                this.industryImagePath = headerProps.get("backgroundImage", String.class);
        }
    }

    /**
     * @param Resource indPageRes 
     * This method locates the industry page to locate the title, icon and color values from it.
     * @return
     */
    private void parseIndustryPage(Resource indPageRes) {
        PallIndustryPage industryPage = indPageRes.adaptTo(PallIndustryPage.class);
        if(null!=industryPage) {
            PallPageContent pallPage = industryPage.getPageContent();
            if (null != pallPage) {
                if (StringUtils.isEmpty(industryTitle))
                    this.industryTitle = pallPage.getTitle();
            }
            if (null != industryPage) {
                this.industryColor = industryPage.getColor().getCssClass();
                if (StringUtils.isEmpty(icon))
                    this.icon = industryPage.getIcon();
            }
        }
    }
    
    /**
     * @param Resource quiltPageRes 
     * This method locates the industry header component to retrieve description and background image
     * @return
     */
    private void parseQuiltTiles(Resource indPageRes) {
        PallIndustryPage industryPage = indPageRes.adaptTo(PallIndustryPage.class);
        if(null!=industryPage) {
            String quiltPage = industryPage.getQuiltPage();
            if(!StringUtils.isEmpty(quiltPage)) {
                Resource quiltPageRes = resourceResolver.getResource(quiltPage + "/" + JcrConstants.JCR_CONTENT);
                if(null!=quiltPageRes) {
                    Resource container = quiltPageRes.getChild("parsys/container");
                    if(null!=container) {
                        QuiltContainerLevel2 quiltLevel2 = container.adaptTo(QuiltContainerLevel2.class);
                        if(quiltLevel2!=null) 
                            this.container = quiltLevel2;
                    }
                }
            }
        }
    }

    public String getIndustryTitle() {
        return industryTitle;
    }

    public String getIndustryImagePath() {
        return industryImagePath;
    }

    public String getIndustryDesc() {
        return industryDesc;
    }

    public String getIndustryColor() {
        if (industryColor != null && industryColor.indexOf("rgb") > -1)
            industryColor = Color.valueFromRGB(industryColor).getCssClass();
        return industryColor;
    }

    public String getIcon() {
        return icon;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public QuiltContainerLevel2 getContainer() {
        return this.container;
    }
}
