package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import com.pall.dotcom.service.enums.Layout;

/**
 * 
 * @author 3|Share - David Truchet
 *
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ColumnControl {

    @Inject
    private String twoColumnSplit;

    @Inject
    private String threeColumnSplit;
    
    @Inject
    private String numberOfColumns;

    private Layout layout;

    @PostConstruct
    public void init() {
        if (twoColumnSplit != null &&
                numberOfColumns.equals("2-col")) {
            this.layout = Layout.valueFromLayout(twoColumnSplit);
        } else if (threeColumnSplit != null &&
                numberOfColumns.equals("3-col")) {
            this.layout = Layout.valueFromLayout(threeColumnSplit);
        } else {
            this.layout = Layout.TWOCOLOPTION1;
        }
    }

    public Layout getLayout() {
        return this.layout;
    }
}
