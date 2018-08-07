package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.solr.common.SolrInputDocument;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.pall.dotcom.service.enums.ResourceType;
import com.pall.dotcom.service.models.exception.SlingModelsException;
/**
 * Sling model representing Pall Base Page.
 *
 */
@Model(adaptables = Resource.class, adapters = {PallContentType.class, PallBasePage.class})
public class PallBasePage implements PallContentType {

    protected PallPageContent pageContent;

    @Self
    protected Resource jcrResource;

    @PostConstruct
    public void init() throws SlingModelsException {
        if(jcrResource!=null && jcrResource.adaptTo(Page.class) == null) {
            this.jcrResource = getPageManager().getContainingPage(jcrResource).getContentResource();
        }
        pageContent = jcrResource.adaptTo(PallPageContent.class);
    }

    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder("PallBasePage{");
        if(null != pageContent){
            sb.append(pageContent.toString());
        }
        sb.append('}');
        return sb.toString();
    }

    @Override
    public JSONObject getJson() throws JSONException {
        return pageContent != null ? pageContent.getJson() : new JSONObject();
    }

    @Override
    public SolrInputDocument getSolrDoc() {
        return pageContent != null ? pageContent.getSolrDoc() : null;
    }

    public PallPageContent getPageContent() {
        return pageContent;
    }
    
    protected PageManager getPageManager() {
        return jcrResource.getResourceResolver().adaptTo(PageManager.class);
    }
    
    public String getResourceType() {
        return ResourceType.valueFromTemplate(pageContent.getSlingResourceType()).name();
    }

    public Resource getParentPage(Page currentPage, ResourceType resType) {
        if(currentPage != null && currentPage.getParent() != null) {
            Page parentPage = currentPage.getParent();
            Resource resourcePage = parentPage.getContentResource();
            ValueMap properties = resourcePage.adaptTo(ValueMap.class);
            String resourceType = properties.get(ResourceResolver.PROPERTY_RESOURCE_TYPE, String.class);
            if(resourceType != null) {
                if (resourceType.equals(resType.getTemplate()) || (ResourceType.isArticle(resType) &&
                        ResourceType.isArticle(ResourceType.valueFromTemplate(resourceType)))) {
                    return resourcePage;
                } else {
                    return getParentPage(parentPage, resType);
                }
            }
        }
        return null;
    }
    
    public String getLinkFacebook() {
        return pageContent.linkFacebook;
    }
    
    public String getLinkTwitter() {
        return pageContent.linkTwitter;
    }
    
    public String getLinkYoutube() {
        return pageContent.linkYoutube;
    }
    
    public String getLinkGooglePlus() {
        return pageContent.linkGooglePlus;
    }
    
    public String getLinkLinkedin() {
        return pageContent.linkLinkedin;
    }
    
	public String getIcon1() {
		return pageContent.icon1;
	}

	public String getIcon2() {
		return pageContent.icon2;
	}

	public String getIcon3() {
		return pageContent.icon3;
	}
}
