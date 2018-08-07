package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ItemBlock {

    @Inject
    private Resource image;

    @Inject
    private Resource bgImage;

    @Self
    private Resource resource;

    private String imagePath, bgImagePath;

    @PostConstruct
    public void init() {
        if (image != null) {
            ValueMap props = image.adaptTo(ValueMap.class);
            this.imagePath = props.get("fileReference", String.class);
        }
        if (bgImage != null) {
            ValueMap props = bgImage.adaptTo(ValueMap.class);
            this.bgImagePath = props.get("fileReference", String.class);
        }
    }

    public Resource getResource() {
        return this.resource;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getBgImagePath() {
        return bgImagePath;
    }

}
