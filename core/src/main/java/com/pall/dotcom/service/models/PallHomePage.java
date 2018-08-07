package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import com.day.cq.wcm.api.Page;
import com.pall.dotcom.service.enums.ResourceType;
import com.pall.dotcom.service.models.exception.SlingModelsException;

/**
 * 
 * @author 3|Share - Upen
 *
 */
@Model(adaptables = Resource.class, adapters = {PallContentType.class, PallHomePage.class})
public class PallHomePage extends PallBasePage {
    
    @PostConstruct
    public void init() throws SlingModelsException {
        super.init();
        if(!pageContent.getSlingResourceType().equals(ResourceType.HOMEPAGE.getTemplate())) {
            Page currentPage = jcrResource.adaptTo(Page.class);
            if(currentPage == null) {
                currentPage = getPageManager().getContainingPage(jcrResource);
            }
            Resource resource = getParentPage(currentPage, ResourceType.HOMEPAGE);
            if (resource != null) {
                this.jcrResource = resource;
                super.init();
            } else {
                return;
            }
        }
    }

}
