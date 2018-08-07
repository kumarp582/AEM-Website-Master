package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import com.pall.dotcom.service.services.GlobalConfigService;

/**
 * 
 * @author 3|Share - David Truchet
 *
 */
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PallConfig {

    @Inject
    private GlobalConfigService globalConfig;

    @PostConstruct
    public void init() {}
    
    public String getYouTubeAPIKey() {
        return this.globalConfig.getYouTubeAPIKey();
    }
}
