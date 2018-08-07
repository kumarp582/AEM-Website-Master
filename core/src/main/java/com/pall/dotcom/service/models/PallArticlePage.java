package com.pall.dotcom.service.models;

import java.util.Calendar;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import com.pall.dotcom.service.enums.ResourceType;
import com.pall.dotcom.service.models.exception.SlingModelsException;


/**
 * 
 * @author 3|Share - Upen
 *
 */
@Model(adaptables = Resource.class, adapters = {PallContentType.class, PallArticlePage.class})
public class PallArticlePage extends PallBasePage {
    
    private Calendar articleDate;
    
    private Calendar articleToDate;
    
    @PostConstruct
    public void init() throws SlingModelsException {
        super.init();
        ResourceType resourceType= ResourceType.valueFromTemplate(pageContent.getSlingResourceType());
        if(!ResourceType.isArticle(resourceType)) {
            Page currentPage = jcrResource.adaptTo(Page.class);
            if(currentPage == null) {
                currentPage = getPageManager().getContainingPage(jcrResource);
            }
            Resource resource = getParentPage(currentPage, ResourceType.ARTICLE);
            if (resource != null) {
                this.jcrResource = resource.getChild(JcrConstants.JCR_CONTENT);
                super.init();
            } else {
                return;
            }
        }
        ValueMap properties = this.jcrResource.adaptTo(ValueMap.class);
        this.articleDate = properties.get("articleDate", Calendar.class);
        this.articleToDate = properties.get("articleToDate", Calendar.class);
    }
    
    public Calendar getArticleDate() {
        return articleDate;
    }

    public Calendar getArticleToDate() {
        return articleToDate;
    }
}
