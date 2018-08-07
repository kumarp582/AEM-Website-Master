package com.pall.dotcom.service.utils;

import com.day.cq.commons.Filter;
import com.day.cq.wcm.api.Page;
import com.pall.dotcom.service.enums.ResourceType;
import com.pall.dotcom.service.models.PallSegmentPage;

public class SegmentPageFilter implements Filter<Page> {

    @Override
    public boolean includes(Page page) {
        if (page.getContentResource().adaptTo(PallSegmentPage.class) != null) {
            PallSegmentPage pallSegment = page.getContentResource().adaptTo(PallSegmentPage.class);
            return (pallSegment.getPageContent().getSlingResourceType().equals(ResourceType.SEGMENT.getTemplate()));
        }
        return false;
    }
}
