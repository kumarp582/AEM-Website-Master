package com.pall.dotcom.service.utils;

import java.util.Iterator;

import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.wcm.api.Page;

/**
 * 
 * @author 3|Share - David Truchet
 *
 */
public class PallPageUtils {

    private PallPageUtils() {
    }

    public static Page retriveIndustryPage(String homePage, String industryTitle, ResourceResolver resolver) {
        Page home = resolver.getResource(homePage).adaptTo(Page.class);
        Iterator<Page> it = home.listChildren(new IndustryPageFilter());
        while (it.hasNext()) {
            Page industry = (Page) it.next();
            String words[] = industryTitle.split(" ", 2);
            String firstWord = words[0];
            if (industry.getTitle().contains(firstWord)) {
                return industry;
            }
        }
        return null;
    }

    public static Page retriveSegmentPage(Page industryPage, String segmentPage, ResourceResolver resolver) {
        Iterator<Page> it = industryPage.listChildren(new SegmentPageFilter());
        while (it.hasNext()) {
            Page segment = (Page) it.next();
            if (segment.getTitle().equalsIgnoreCase(segmentPage)) {
                return segment;
            }
        }
        return null;
    }
}