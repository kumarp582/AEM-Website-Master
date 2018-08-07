package com.pall.dotcom.service.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.pall.dotcom.service.enums.Color;
import com.pall.dotcom.service.enums.ResourceType;
import com.pall.dotcom.service.models.exception.SlingModelsException;
import com.pall.dotcom.service.utils.SegmentPageFilter;

/**
 * 
 * @author 3|Share - David Truchet
 *
 */
@Model(adaptables = Resource.class, adapters = { PallContentType.class, PallIndustryPage.class })
public class PallIndustryPage extends PallBasePage {
    private static final Logger LOG = LoggerFactory.getLogger(PallIndustryPage.class);

    private Color color;

    private String icon;

    private String tactileGraphic;

    private String bannerImage;

    private List<NavItem> serviceLinks;
    private List<NavItem> marketLinks;
    private List<NavItem> industryLinks;

    private String industryimage;

    private String icon1;
    private String icon2;
    private String icon3;

    private List<PallSegmentPage> segments;

    private String pageTitle;

    private String quiltPage;

    @PostConstruct
    public void init() throws SlingModelsException {
        super.init();

        if (!pageContent.getSlingResourceType().equals(ResourceType.INDUSTRY.getTemplate())
                && !pageContent.getSlingResourceType().equals(ResourceType.HOMEPAGE.getTemplate())) {
            Page currentPage = jcrResource.adaptTo(Page.class);
            if (currentPage == null) {
                currentPage = getPageManager().getContainingPage(jcrResource);
            }

            Resource resource = getParentPage(currentPage, ResourceType.INDUSTRY);
            // If the page referring this model is not under an industry page,
            // default the model to Homepage to use global color, tactile
            // properties
            if (resource == null)
                resource = getParentPage(currentPage, ResourceType.HOMEPAGE);

            if (resource != null) {
                this.jcrResource = resource;
                super.init();
            } else {
                return;
            }
        }
        ValueMap properties = this.jcrResource.adaptTo(ValueMap.class);
        String rgbColor = properties.get("color", String.class);
        this.color = Color.valueFromRGB(rgbColor);
        this.icon = properties.get("icon", String.class);
        this.tactileGraphic = properties.get("tactileGraphic", String.class);
        this.bannerImage = properties.get("bannerImage", String.class);
        this.serviceLinks = this.processLinks(properties.get("serviceLinks", String[].class));
        this.industryLinks = this.processLinks(properties.get("industryLinks", String[].class));
        this.industryimage = properties.get("industryimage", String.class);
        this.pageTitle = this.getTitle();
        this.icon1 = properties.get("icon1", String.class);
        this.icon2 = properties.get("icon2", String.class);
        this.icon3 = properties.get("icon3", String.class);
        this.quiltPage = properties.get("quiltPage", String.class);

        if (pageContent.getSlingResourceType().equals(ResourceType.INDUSTRY.getTemplate())) {
            segments = getPageSegments();
        }
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public Color getColor() {
        return color;
    }

    public String getIcon() {
        return icon;
    }

    public String getTactileGraphic() {
        return tactileGraphic;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public List<NavItem> getServiceLinks() {
        return serviceLinks;
    }

    public List<NavItem> getMarketLinks() {
        return marketLinks;
    }

    public List<NavItem> getIndustryLinks() {
        return industryLinks;
    }

    public String getIndustryimage() {
        return industryimage;
    }

    public String getQuiltPage() {
        return this.quiltPage;
    }

    private List<NavItem> processLinks(String[] items) {
        List<NavItem> navigationItems = new ArrayList<NavItem>();
        if (items != null) {
            for (int i = 0; i < items.length; i++) {
                JSONObject jSONValue;
                try {
                    jSONValue = new JSONObject(items[i]);
                    String title = jSONValue.getString("title");
                    String link = jSONValue.getString("link");
                    navigationItems.add(new NavItem(title, link));
                } catch (JSONException e) {
                    LOG.error("Error creating JSON for links", e);
                }
            }
        }
        return navigationItems;
    }

    private String getTitle() {
        String title = "";
        if (StringUtils.isNotEmpty(this.pageContent.navTitle)) {
            title = this.pageContent.navTitle;
        } else if (StringUtils.isNotEmpty(this.pageContent.pageTitle)) {
            title = this.pageContent.pageTitle;
        } else {
            title = this.pageContent.title;
        }
        return title;
    }

    private List<PallSegmentPage> getPageSegments() {
        List<PallSegmentPage> sList = new ArrayList<PallSegmentPage>();
        Page p = jcrResource.getParent().adaptTo(Page.class);
        Iterator<Page> it = p.listChildren(new SegmentPageFilter());
        while (it.hasNext()) {
            Page aux = it.next();
            PallSegmentPage segment = aux.getContentResource().adaptTo(PallSegmentPage.class);
            sList.add(segment);
        }
        return sList;
    }

    public String getIcon1() {
        return icon1;
    }

    public String getIcon2() {
        return icon2;
    }

    public String getIcon3() {
        return icon3;
    }

    public List<PallSegmentPage> getSegments() {
        return segments;
    }

}
