/**
 * 
 */
package com.pall.dotcom.service.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

/**
 * @author upemanic
 *
 */
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ArticleDate {

    @Inject
    private PallArticlePage articlePage;

    private Calendar articleDate;
    private Calendar articleToDate;

    @PostConstruct
    public void init() {
        this.articleDate = articlePage.getArticleDate();
        this.articleToDate = articlePage.getArticleToDate();
    }

    public Boolean getDatePeriod() {
        return articleToDate != null;
    }
    
    public Calendar getArticleToDate() {
        return articleToDate;
    }

    public String getFromDay() {
        return getDay(articleDate);
    }

    public String getToDay() {
        return getDay(articleToDate);
    }

    private String getDay(Calendar date) {
        if (null != date) {
            SimpleDateFormat formatter = new SimpleDateFormat("EEE");
            return formatter.format(date.getTime());
        }
        return "DAY";
    }

    public String getFromDate() {
        return getDate(articleDate);
    }

    public String getToDate() {
        return getDate(articleToDate);
    }

    private String getDate(Calendar date) {
        if (null != date) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd");
            return formatter.format(date.getTime());
        }
        return "DD";
    }

    public String getFromMonth() {
        return getMonth(articleDate);
    }

    public String getToMonth() {
        return getMonth(articleToDate);
    }
    
    public String getMonth(Calendar date) {
        if (null != date) {
            SimpleDateFormat formatter = new SimpleDateFormat("MMM");
            return formatter.format(date.getTime());
        }
        return "MMM";
    }

    public String getFromYear() {
        return getYear(articleDate);
    }

    public String getToYear() {
        return getYear(articleToDate);
    }
    
    public String getYear(Calendar date) {
        if (null != date) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
            return formatter.format(date.getTime());
        }
        return "YYYY";
    }
}
