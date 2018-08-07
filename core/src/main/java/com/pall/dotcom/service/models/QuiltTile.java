package com.pall.dotcom.service.models;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

/**
 * 
 * @author 3|Share - Upen
 *
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class QuiltTile implements Cloneable{

    private final String DEFAULT_SIZE = "1x1";

    private final String DEFAULT_IMAGE_PATH = "/content/dam/pall/quilt/";

    private final String DEFAULT_IMAGE_EXTN = ".png";

    @Self
    protected Resource resource;

    @Inject
    private String heading;

    @Inject
    private String mobileHeading;

    @Inject
    private String title;

    @Inject
    private String desc;

    @Inject
    private String link;

    @Inject
    private String size;

    @Inject
    protected Resource image;

    private String bgImagePath;

    @PostConstruct
    public void init() {
        if (image != null) {
            ValueMap props = image.adaptTo(ValueMap.class);
            this.bgImagePath = props.get("fileReference", String.class);
        }
    }

    public String getBgImagePath() {
        if (!StringUtils.isEmpty(this.bgImagePath))
            return this.bgImagePath;
        else
            return this.DEFAULT_IMAGE_PATH + getSize() + this.DEFAULT_IMAGE_EXTN;
    }

    public String getSize() {
        if (!StringUtils.isEmpty(size))
            return this.size;
        else
            return this.DEFAULT_SIZE;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getLink() {
        return link;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getHeading() {
        return this.heading;
    }

    public String getMobileHeading() {
        return mobileHeading;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("Title : ").append(this.getTitle())
        .append("Desc : ").append(this.desc)
        .append("Image : ").append(this.image)
        .append("ImagePath : ").append(this.bgImagePath)
        .append("Link : ").append(this.link);
        return sb.toString();
    }
    
    @Override
    public QuiltTile clone() {
        QuiltTile tile = new QuiltTile();
        tile.size = this.size;
        tile.desc = this.desc;
        tile.link = this.link;
        tile.title = this.title;
        tile.bgImagePath = this.bgImagePath;
        tile.heading = this.heading;
        tile.mobileHeading = this.mobileHeading;
        return tile;
    }
    
}
