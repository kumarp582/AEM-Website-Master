package com.pall.dotcom.service.solr.services;

import org.apache.felix.scr.annotations.*;
import org.apache.felix.scr.annotations.Properties;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import static com.pall.dotcom.service.solr.services.ProxyConfigurationServiceAdminConstants.*;

@Component(label = "AEM Solr Search - Solr Proxy Service", description = "A service for configuring the search proxy", immediate = true, metatype = true)
@Service(ProxyConfigurationService.class)
@Properties({ @Property(name = Constants.SERVICE_VENDOR, value = "3|Share"),
        @Property(name = Constants.SERVICE_DESCRIPTION, value = "CQ Search proxy configuration service") })
/**
 * ProxyConfigurationService provides services for setting and getting proxy
 * configuration information.
 * 
 * From headwirecom/aem-solr-search project
 */
public class ProxyConfigurationService {

    private static final Logger LOG = LoggerFactory.getLogger(ProxyConfigurationService.class);

    public static final Integer DEFAULT_HTTP_CONN_TIMEOUT = 10000;
    public static final Integer DEFAULT_HTTP_SO_TIMEOUT = 30000;

    @Property(name = HTTP_CONN_TIMEOUT, intValue = 10000, label = "Connection Timeout", description = "Connection timeout in ms")
    private Integer httpConnTimeout;

    @Property(name = HTTP_SO_TIMEOUT, intValue = 30000, label = "Socket Timeout", description = "Socket timeout in ms")
    private Integer httpSoTimeout;

    public Integer getHttpConnTimeout() {
        return httpConnTimeout;
    }

    public Integer getHttpSoTimeout() {
        return httpSoTimeout;
    }

    @Activate
    protected void activate(final Map<String, Object> config) {
        resetService(config);
    }

    @Modified
    protected void modified(final Map<String, Object> config) {
        resetService(config);
    }

    private synchronized void resetService(final Map<String, Object> config) {
        LOG.info("Resetting CQ Search proxy configuration service using configuration: " + config);

        httpConnTimeout = config.containsKey(HTTP_CONN_TIMEOUT) ? (Integer) config.get(HTTP_CONN_TIMEOUT)
                : DEFAULT_HTTP_CONN_TIMEOUT;

        httpSoTimeout = config.containsKey(HTTP_SO_TIMEOUT) ? (Integer) config.get(HTTP_SO_TIMEOUT)
                : DEFAULT_HTTP_SO_TIMEOUT;
    }
}
