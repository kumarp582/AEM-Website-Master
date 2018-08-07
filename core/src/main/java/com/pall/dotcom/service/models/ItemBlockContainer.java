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
public class ItemBlockContainer {

    @Inject
    @Named("block1")
    private Resource block1Res;

    @Inject
    @Named("block2")
    private Resource block2Res;

    @Inject
    @Named("block3")
    private Resource block3Res;

    private ItemBlock block1, block2, block3;

    @Inject
    private String bgColor;

    private Color color = Color.WHITE;

    @PostConstruct
    public void init() {
        if (block1Res != null)
            block1 = block1Res.adaptTo(ItemBlock.class);
        if (block2Res != null)
            block2 = block2Res.adaptTo(ItemBlock.class);
        if (block3Res != null)
            block3 = block3Res.adaptTo(ItemBlock.class);

        if (StringUtils.isNotEmpty(bgColor)) {
            this.color = Color.valueFromRGB(bgColor);
        }
    }

    public ItemBlock getBlock1() {
        return block1;
    }

    public ItemBlock getBlock2() {
        return block2;
    }

    public ItemBlock getBlock3() {
        return block3;
    }

    public Color getColor() {
        return color;
    }

}
