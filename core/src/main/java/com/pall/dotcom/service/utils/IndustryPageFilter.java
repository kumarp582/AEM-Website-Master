package com.pall.dotcom.service.utils;

import com.day.cq.commons.Filter;
import com.day.cq.wcm.api.Page;
import com.pall.dotcom.service.enums.ResourceType;
import com.pall.dotcom.service.models.PallIndustryPage;

public class IndustryPageFilter implements Filter<Page> {

    @Override
    public boolean includes(Page page) {
        if (page.getContentResource().adaptTo(PallIndustryPage.class) != null) {
            PallIndustryPage pallIndustry = page.getContentResource().adaptTo(PallIndustryPage.class);
            return (pallIndustry.getPageContent().getSlingResourceType().equals(ResourceType.INDUSTRY.getTemplate()));
        }
        return false;
    }
}
