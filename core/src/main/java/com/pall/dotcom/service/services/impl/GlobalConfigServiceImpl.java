package com.pall.dotcom.service.services.impl;

import java.util.Map;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.framework.Constants;

import com.pall.dotcom.service.services.GlobalConfigService;

/**
 * 
 * @author 3|Share - David Truchet
 *
 */
@Component(immediate = true, metatype = true, label = "Pall Global Config Service")
@Service(value = GlobalConfigService.class)
@Properties({ @Property(name = Constants.SERVICE_VENDOR, value = "3|Share"),
    @Property(name = Constants.SERVICE_DESCRIPTION, value = "Provides a service with Pall dotcom global configurations")})
public class GlobalConfigServiceImpl implements GlobalConfigService {

    private String youTubeAPIKey;
    @Property(label = "YouTube API Key", description = "YouTube V3 API Key")
    private static final String YOUTUBE_API_KEY = "youtube.apikey";

    @Activate
    protected void update(final Map<String, Object> properties) {
        this.youTubeAPIKey = PropertiesUtil.toString(properties.get(YOUTUBE_API_KEY), "nokey");
    }

    @Override
    public String getYouTubeAPIKey() {
        return this.youTubeAPIKey;
    }

}
