package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.pall.dotcom.service.enums.Color;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TopicsTab {

    @Inject @Named("topic1")
    private Resource topic1Res;

    @Inject @Named("topic2")
    private Resource topic2Res;
    
    @Inject @Named("topic3")
    private Resource topic3Res;

    @Inject
    private String tabName;

    @Inject
    private String activeFontColor;

    @Inject
    private String nonActiveFontColor;

    private Color activeSelectedFontColor;

    private Color nonActiveSelectedFontColor;

    private Color backgroundColor;

    @Self
    private Resource resource;

    private String nodeName;

    private TopicsTabTopic topic1, topic2, topic3;

    @PostConstruct
    public void init(){
        if(topic1Res!=null)
            topic1 = topic1Res.adaptTo(TopicsTabTopic.class);
        if(topic2Res!=null)
            topic2 = topic2Res.adaptTo(TopicsTabTopic.class);
        if(topic3Res!=null)
            topic3 = topic3Res.adaptTo(TopicsTabTopic.class);

        if( !StringUtils.isEmpty(activeFontColor) ) {
            this.activeSelectedFontColor = Color.valueFromRGB(activeFontColor);
        } else {
            this.activeSelectedFontColor = Color.WHITE;
        }

        if( !StringUtils.isEmpty(nonActiveFontColor) ) {
            this.nonActiveSelectedFontColor = Color.valueFromRGB(nonActiveFontColor);
        } else {
            this.nonActiveSelectedFontColor = Color.WHITE;
        }

        nodeName = resource.getName();

        Resource parentRes = resource.getParent();
        ValueMap props = parentRes.adaptTo(ValueMap.class);
        String bgColor = "";
        if( props!=null) {
            bgColor = props.get("bgColor", String.class);
        }

        if( !StringUtils.isEmpty(bgColor) ) {
            this.backgroundColor = Color.valueFromRGB(bgColor);
        } else {
            this.backgroundColor = Color.WHITE;
        }

    }

    public TopicsTabTopic getTopic1() {
        return topic1;
    }

    public TopicsTabTopic getTopic2() {
        return topic2;
    }

    public TopicsTabTopic getTopic3() {
        return topic3;
    }

    public String getActiveFontColor() {
        return activeSelectedFontColor.getCssClass();
    }

    public String getActiveFontColorValue() {
        return activeFontColor;
    }

    public String getNonActiveFontColor() {
        return nonActiveSelectedFontColor.getCssClass();
    }

    public String getNonActiveFontColorValue() {
        return nonActiveFontColor;
    }

    public String getBackgroundColor() {
        return backgroundColor.getCssClass();
    }

    public String getTabName() {
        return tabName;
    }

    public String getNodeName() {
        return nodeName;
    }
}
