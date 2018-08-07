package com.pall.dotcom.service.models;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import com.pall.dotcom.service.enums.Color;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

/**
 * 
 * @author 3|Share - Upen
 *
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class QuiltProduct extends QuiltMember{

    @Inject
    private String productLink;

    @Inject
    private String productTitle;

    @Inject
    private String productDesc;

    @Inject
    private Resource image;

    @Inject
    private String productColor;
    
    @Inject 
    private String icon; 
    
    @Inject
    private String buttonTitle;
    
    @Inject
    private String buttonStyle;
    
    @Inject 
    private Boolean isVideo;

    private String productImagePath;
    
    private String size;

    @PostConstruct
    public void init() {
        if (image != null) {
            ValueMap props = image.adaptTo(ValueMap.class);
            this.productImagePath = props.get("fileReference", String.class);
        }
    }

    public String getProductLink() {
        return productLink;
    }

    public String getProductColor() {
        if (productColor != null && productColor.indexOf("rgb") > -1)
            productColor = Color.valueFromRGB(productColor).getCssClass();
        return productColor;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductLink(String productLink) {
        this.productLink = productLink;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }

    public String getProductImagePath() {
        return this.productImagePath;
    }

    public String getIcon() {
        return icon;
    }

    public String getButtonTitle() {
        return buttonTitle;
    }

    public String getButtonStyle() {
        return buttonStyle;
    }

    public boolean isVideo() {
        return isVideo!=null?isVideo.booleanValue():false;
    }
    
    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}
