package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import com.pall.dotcom.service.enums.Color;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MiniSolutionBlockContainer {

    @Inject @Named("block1")
    private Resource block1Res;

    @Inject @Named("block2")
    private Resource block2Res;

    @Inject
    private String bgColor;

    private Color color = Color.WHITE;

    private MiniSolutionBlock block1, block2;

    @PostConstruct
    public void init(){
        if(block1Res!=null) {
            block1 = block1Res.adaptTo(MiniSolutionBlock.class);
        }

        if(block2Res!=null) {
            block2 = block2Res.adaptTo(MiniSolutionBlock.class);
        }

        if (StringUtils.isNotEmpty(bgColor)) {
            this.color = Color.valueFromRGB(bgColor);
        }
    }

    public MiniSolutionBlock getBlock1() {
        return block1;
    }

    public MiniSolutionBlock getBlock2() {
        return block2;
    }

    public Color getColor() {
        return color;
    }
}
