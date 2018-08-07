package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import com.pall.dotcom.service.enums.Layout;

/**
 * 
 * @author 3|Share - Upen
 *
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LongFormContentBlock {

    @Inject
    private String numberOfColumns;

    private Layout layout;

    @PostConstruct
    public void init() {
        if (StringUtils.equals(numberOfColumns, "2-col")) 
            this.layout = Layout.TWOCOLOPTION2;
        else
            this.layout = Layout.THREECOLOPTION1;
    }

    public Layout getLayout() {
        return this.layout;
    }
}
