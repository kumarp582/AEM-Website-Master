package com.pall.dotcom.service.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class WebinarDetail {    
    
    private String time;
   
    @Inject  @Via("resource")
    private Date webinartime; 

    @PostConstruct
    public void init() {
           SimpleDateFormat formatterh = new SimpleDateFormat("hh:mm a");
           if(webinartime != null) {
        	   time = formatterh.format(webinartime);
           }
    }

    public String getTime() {
        return time;
    }
}