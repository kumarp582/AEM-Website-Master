package com.pall.dotcom.service.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import com.day.cq.wcm.api.Page;

/**
 * 
 * @author 3|Share - David Truchet
 *
 */
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class EventDetail {

    @Inject
    public Page currentPage;
    
    private String countDownDate;

    @PostConstruct
    public void init() {
        PallArticlePage articlePage = currentPage.getContentResource().adaptTo(PallArticlePage.class);
        Calendar date = articlePage.getArticleDate();
        if(date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            countDownDate = formatter.format(date.getTime());;
        }
    }

    public String getCountDownDate() {
        return countDownDate;
    }
}
