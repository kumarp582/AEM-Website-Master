package com.pall.dotcom.service.services.impl;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.text.csv.Csv;
import com.pall.dotcom.service.enums.ResourceType;
import com.pall.dotcom.service.services.PageImportService;
import com.pall.dotcom.service.utils.PallPageUtils;

/**
 * 
 * @author 3|Share - David Truchet
 *
 */
@Service(value = PageImportService.class)
@Component(immediate = true)
@Properties({ @Property(name = Constants.SERVICE_VENDOR, value = "3|Share"),
        @Property(name = Constants.SERVICE_DESCRIPTION, value = "Service responsable to start the page import process.") })
public class PageImportServiceImpl implements PageImportService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String HOME = "/content/pall/global/en";
    private static final String SEGMENT_TEMPLATE = "/apps/pall/templates/segment";
    private static final String SEGMENT_APPLICATION = "/apps/pall/templates/application";
    private final Long DEFAULT_SLEEP_TIME = 5000l;

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Override
    public String startImportProcess(String filePath) {

        logger.info("*******       Starting Page Import process       *******");

        ResourceResolver resourceResolver = null;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put(ResourceResolverFactory.SUBSERVICE, this.getClass().getName());
        try {
            File file = new File(filePath.trim());
            FileReader fileReader = new FileReader(file);
            Csv csvFile = new Csv();
            Iterator<String[]> rows = csvFile.read(fileReader);
            resourceResolver = resolverFactory.getServiceResourceResolver(param);
            while (rows.hasNext()) {
                String[] row = (String[]) rows.next();
                String industryTitle = row[0];
                String segmentTitle = row[1];
                String applicationTitle = row[2];
                createPage(industryTitle, segmentTitle, applicationTitle, resourceResolver);
            }

        } catch (Exception e) {
            logger.error("Could not get ResourceResolver instance or handle page modification", e);
        } finally {
            if (resourceResolver != null && resourceResolver.isLive())
                resourceResolver.close();
        }
        return PROCESS_COMPLETE;
    }

    private void createPage(String industryTitle, String segmentTitle, String applicationTitle,
            ResourceResolver resourceResolver) throws Exception {

        PageManager pageMgr = resourceResolver.adaptTo(PageManager.class);
        Page industry = PallPageUtils.retriveIndustryPage(HOME, industryTitle, resourceResolver);
        if (industry == null) {
            logger.error("Industry not found " + industryTitle);
            return;
        }
        Page segment = PallPageUtils.retriveSegmentPage(industry, segmentTitle, resourceResolver);
        if (segment == null) {
            // create the missing segment page
            String segmentName = segmentTitle.replaceAll("[^a-zA-Z]+", "-").toLowerCase();
            segment = pageMgr.create(industry.getPath(), segmentName, SEGMENT_TEMPLATE, segmentTitle, true);
            logger.info("Segment page created " + segment.getTitle());
            setContentType(segment, SEGMENT_TEMPLATE, ResourceType.SEGMENT.getTemplate());
            // commit changes.
            if (resourceResolver.hasChanges()) {
                resourceResolver.commit();
                // wait for 5 seconds to force sync with the commit
                Thread.sleep(DEFAULT_SLEEP_TIME);
            }
        }

        String appName = applicationTitle.replaceAll("[^a-zA-Z]+", "-").toLowerCase();
        Page app = pageMgr.create(segment.getPath(), appName, SEGMENT_APPLICATION, applicationTitle, true);
        logger.info("App page created " + app.getTitle());
        setContentType(app, SEGMENT_APPLICATION, ResourceType.APPLICATION.getTemplate());
        // commit changes.
        if (resourceResolver.hasChanges()) {
            resourceResolver.commit();
        }
    }

    private void setContentType(Page page, String template, String resourceType) {
        Resource jcrContent = page.getContentResource();
        if (jcrContent != null) {
            ModifiableValueMap properties = jcrContent.adaptTo(ModifiableValueMap.class);
            properties.put(NameConstants.PN_TEMPLATE, template);
            properties.put(ResourceResolver.PROPERTY_RESOURCE_TYPE, resourceType);
        }
    }

}
