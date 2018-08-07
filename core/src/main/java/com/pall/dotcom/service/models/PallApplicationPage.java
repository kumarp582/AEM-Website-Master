package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;

import com.pall.dotcom.service.models.exception.SlingModelsException;

/**
 * 
 * @author 3|Share - Upen
 *
 */
@Model(adaptables = Resource.class, adapters = {PallContentType.class, PallApplicationPage.class})
public class PallApplicationPage extends PallBasePage {
    
    @Inject @Default
    private String imagePath;
    
    @Inject @Default
    private String desc;
    
    @Inject @Default 
    private String link;
    
    @PostConstruct
    public void init() throws SlingModelsException {

    }

    public String getDesc() {
        return desc;
    }

    public String getLink() {
        return link;
    }

    public String getImagePath() {
        return imagePath;
    }

}
