package com.pall.dotcom.service.listeners;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.apache.solr.client.solrj.SolrClient;
import org.osgi.framework.Constants;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.PageEvent;
import com.day.cq.wcm.api.PageModification;
import com.pall.dotcom.service.models.PallBasePage;
import com.pall.dotcom.service.models.PallContentType;
import com.pall.dotcom.service.solr.services.DefaultSolrSearchService;
import com.pall.dotcom.service.solr.services.SolrConfigurationService;

/**
 * 
 * @author 3|Share - David Truchet
 *
 */
@Component(immediate = true, metatype = true)
@Service(value = EventHandler.class)
@Properties({ @Property(name = Constants.SERVICE_VENDOR, value = "3|Share"),
        @Property(name = Constants.SERVICE_DESCRIPTION, value = "Provides a service listening changes in pages to update solr each change"),
        @Property(name = EventConstants.EVENT_TOPIC, value = { PageEvent.EVENT_TOPIC }) })
public class SolrPageModificationListener extends DefaultSolrSearchService implements EventHandler {
    private static final Logger LOG = LoggerFactory.getLogger(SolrPageModificationListener.class);
    
    @Property(name = "listener.disabled", boolValue = true)
    private boolean disabled = false;

    @Property(name = "solr.core", value = "collection1")
    private String core = "collection1";

    @Property(name = "listener.paths", value = { "/content" }, cardinality = Integer.MAX_VALUE)
    private String[] basePaths = new String[0];

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    private SolrConfigurationService solrConfigService;

    public void handleEvent(final Event event) {
        if (disabled)
            return;

        PageEvent pageEvent = PageEvent.fromEvent(event);
        if (pageEvent == null)
            return;

        ResourceResolver resourceResolver = null;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put(ResourceResolverFactory.SUBSERVICE, this.getClass().getName());
        try {
            resourceResolver = resolverFactory.getServiceResourceResolver(param);
            for (Iterator<PageModification> iter = pageEvent.getModifications(); iter.hasNext();)
                handlePageModification(iter.next(), resourceResolver);
        } catch (Exception e) {
            LOG.error("Could not get ResourceResolver instance or handle page modification", e);
            return;
        } finally {
            if (resourceResolver != null && resourceResolver.isLive())
                resourceResolver.close();
        }
    }

    protected void handlePageModification(PageModification mod, ResourceResolver resourceResolver) {
        String pagePath = mod.getPath();
        boolean isAllowedPath = false;
        for (String basePath : basePaths) {
            isAllowedPath |= pagePath.startsWith(basePath);
        }

        if (!isAllowedPath) {
            LOG.debug("Page event not on one of the base paths. Ignoring event.");
            return;
        }
        Resource pageRes = resourceResolver.getResource(pagePath);
        SolrClient solr = getSolrIndexClient();

        LOG.info("Handling valid page modification " + mod);
        switch (mod.getType()) {
        case CREATED:
        case MODIFIED:
        case RESTORED:
            addOrUpdatePage(pageRes, solr);
            break;
        case DELETED:
            removePage(pagePath, solr);
            break;
        case MOVED:
            removePage(pagePath, solr);
            pageRes = resourceResolver.getResource(mod.getDestination());
            addOrUpdatePage(pageRes, solr);
            break;
        }
    }

    protected void addOrUpdatePage(Resource pageRes, SolrClient solr) {
        if (pageRes == null) {
            LOG.error("Page does not exist to add/update in solr");
            return;
        }
        PallContentType dataPage = pageRes.adaptTo(PallBasePage.class);
        try {
            LOG.info("Adding/updating page " + pageRes.getPath());
            solr.add(dataPage.getSolrDoc());
            solr.commit();
        } catch (Exception e) {
            LOG.error("Failure to add/update page " + pageRes.getPath(), e);
        }
    }

    protected void removePage(String id, SolrClient solr) {
        try {
            LOG.info("Removing page " + id);
            solr.deleteById(id);
            solr.commit();
        } catch (Exception e) {
            LOG.error("Failure to remove page " + id, e);
        }
    }

    @Activate
    protected void activateService(Map<String, Object> params) {
        super.activate(getMapAsStringValues(params));
        setup(params);
    }

    @Modified
    protected void modifiedService(Map<String, Object> params) {
        super.modified(getMapAsStringValues(params));
        setup(params);
    }

    protected void setup(Map<String, Object> params) {
        
        disabled = PropertiesUtil.toBoolean(params.get("listener.disabled"), false);
        core = PropertiesUtil.toString(params.get("solr.core"), core);
        basePaths = PropertiesUtil.toStringArray(params.get("listener.paths"));

        LOG.debug("Service disabled? " + disabled);
        LOG.debug("Using core: " + core);
        LOG.debug("Base paths:");
        for (String path : basePaths)
            LOG.debug("  " + path);
    }

    private Map<String, String> getMapAsStringValues(Map<String, Object> original) {
        Map<String, String> retVal = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry : original.entrySet())
            retVal.put(entry.getKey(), entry.getValue().toString());
        return retVal;
    }
}
