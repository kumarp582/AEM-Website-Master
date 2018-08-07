package com.pall.dotcom.service.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.pall.dotcom.service.enums.Color;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TopicsTabContainer {

    @Inject
    private Resource image;

    @Inject
    private String bgColor;

    private String imagePath;

    private Color backgroundColor;

    @Self
    private Resource container;

    private int tabCount;

    private List<TopicsTab> tabs = new ArrayList<TopicsTab>();

    @PostConstruct
    public void init(){
        if ( image!=null ) {
            ValueMap props = image.adaptTo(ValueMap.class);
            this.imagePath = props.get("fileReference", String.class);
        }

        if( !StringUtils.isEmpty(bgColor) ) {
            this.backgroundColor = Color.valueFromRGB(bgColor);
        } else {
            this.backgroundColor = Color.WHITE;
        }

        tabCount = 0;

        if ( null != container ) {
            Iterator<Resource> itr = container.getChildren().iterator();
            while ( itr.hasNext() ) {
                Resource res = itr.next();
                ValueMap props = res.adaptTo(ValueMap.class);
                if( props!=null && tabCount < 4 ) {
                    String resType = props.get("sling:resourceType", String.class);
                    if ( StringUtils.equals(resType, "pall/components/content/topics-tab-container/topics-tab")) {
                        tabCount++;
                        tabs.add(res.adaptTo(TopicsTab.class));
                    }
                }
            }
        }
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getBgColor() {
        return backgroundColor.getCssClass();
    }
    
    public int getTabCount(){
        return this.tabCount;
    }

    public List<TopicsTab> getTabs() {
        return tabs;
    }
}
