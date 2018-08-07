package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TopicsTabTopic {

    @Inject 
    private Resource image;
    
    @Self
    private Resource resource;

    private String title, description, link;

    private String imagePath;
    
    @PostConstruct
    public void init(){
        if(image!=null){
            ValueMap props = image.adaptTo(ValueMap.class);
            this.imagePath = props.get("fileReference", String.class);
        }

        ValueMap props = resource.adaptTo(ValueMap.class);
        if(props!=null){
            this.title = props.get("title", String.class);
            this.link = props.get("link", String.class);
            this.description = props.get("description", String.class);
        }
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

}
