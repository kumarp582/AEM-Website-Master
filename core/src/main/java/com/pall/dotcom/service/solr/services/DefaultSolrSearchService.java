package com.pall.dotcom.service.solr.services;

import java.util.Map;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.solr.client.solrj.SolrClient;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(label = "AEM Solr Search - Solr Search Service", description = "A service for performing searches against Solr", immediate = true, metatype = true)
@Service(DefaultSolrSearchService.class)
@Properties({ @Property(name = Constants.SERVICE_VENDOR, value = "3|Share"),
        @Property(name = Constants.SERVICE_DESCRIPTION, value = "A default implementation of the Solr.search.service") })
/**
 * DefaultSolrSearchService is responsible for obtaining the uri for a
 * particular solr instance identified by a core.
 * 
 * From headwirecom/aem-solr-search project
 */
public class DefaultSolrSearchService extends AbstractSolrSearchService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultSolrSearchService.class);

    @Reference
    SolrConfigurationService solrConfigService;

    @Activate
    protected void activate(final Map<String, String> config) {
        resetService(config);
    }

    @Modified
    protected void modified(final Map<String, String> config) {
        resetService(config);
    }

    @Override
    protected String getSolrServerURI() {
        assertSolrConfigService();
        return solrConfigService.getSolrEndPoint();
    }

    @Override
    protected String getSolrServerURI(String solrCore) {
        assertSolrConfigService();
        return formatSolrEndPointAndCore(solrConfigService.getSolrEndPoint(), solrCore);
    }

    @Override
    protected SolrClient getSolrIndexClient() {
        return solrConfigService.getIndexingSolrClient();
    }

    @Override
    protected SolrClient getSolrQueryClient() {
        return solrConfigService.getQueryingSolrClient();
    }

    private void resetService(final Map<String, String> config) {
        LOG.info("Resetting Solr search service using configuration: " + config);
    }

    private void assertSolrConfigService() throws IllegalStateException {
        if (null == solrConfigService) {
            LOG.error("Can't get SolrConfigurationService. Check that all OSGi bundles are active");
            throw new IllegalStateException("No solr configuration service.");
        }
    }
}
