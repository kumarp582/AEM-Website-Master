package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;

import com.day.cq.wcm.api.Page;
import com.pall.dotcom.service.enums.ResourceType;
import com.pall.dotcom.service.models.exception.SlingModelsException;

/**
 * 
 * @author 3|Share - Damian Irigaray
 *
 */
@Model(adaptables = Resource.class, adapters = { PallContentType.class, PallSegmentPage.class })
public class PallSegmentPage extends PallBasePage {

    private String headerDescription;

    @PostConstruct
    public void init() throws SlingModelsException {
        super.init();
        if (!pageContent.getSlingResourceType().equals(ResourceType.SEGMENT.getTemplate())) {
            Page currentPage = jcrResource.adaptTo(Page.class);
            if (currentPage == null) {
                currentPage = getPageManager().getContainingPage(jcrResource);
            }
            Resource resource = getParentPage(currentPage, ResourceType.SEGMENT);
            if (resource != null) {
                this.jcrResource = resource;
                super.init();
            } else {
                return;
            }
        }
        Resource industryHeaderRes = jcrResource.getChild("pall-header");
        if(industryHeaderRes != null) {
            ValueMap properties = industryHeaderRes.adaptTo(ValueMap.class);
            this.headerDescription = properties.get("description", String.class);
        }
    }

    public String getHeaderDescription() {
        return headerDescription;
    }
    
}