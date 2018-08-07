package com.pall.dotcom.service.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.pall.dotcom.service.enums.ResourceType;
import com.pall.dotcom.service.utils.IndustryPageFilter;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TopNav {
    private static final Logger LOG = LoggerFactory.getLogger(TopNav.class);

    @Inject
    @Via("resource")
    private Resource image;
    private String logoImagePath;

    @Inject
    @Via("resource")
    private Resource imageMobile;
    private String mobileLogoImagePath;

    @Inject
    @Via("resource")
    private List<Resource> links;

    private List<PallNavItem> subMenuLinks;

    @Inject
    private Resource resource;

    @Inject
    private PageManager pageManager;

    @Inject
    private Page currentPage;

    private PallIndustryPage industryPage;

    private PallHomePage homepage;

    private String sectionTitle = "";

    private Boolean hasSelectedItem = false;

    private PallNavItem selectedItem;

    @PostConstruct
    protected void init() {
        try {
            Resource res = currentPage.getContentResource();
            // Adapt the page to industry page first to see whether this is
            // under an Industry Page
            if (res != null) {
                this.homepage = res.adaptTo(PallHomePage.class);
                this.industryPage = res.adaptTo(PallIndustryPage.class);
                if (this.industryPage != null && this.industryPage.getPageContent().getSlingResourceType()
                        .equals(ResourceType.INDUSTRY.getTemplate())) {
                    this.sectionTitle = this.industryPage.getPageTitle();
                }
            }

            subMenuLinks = new ArrayList<PallNavItem>();
            Page page = pageManager.getContainingPage(resource);
            Iterator<Page> it = page.listChildren(new IndustryPageFilter());
            while (it.hasNext()) {
                PallIndustryPage pallIndustry = it.next().getContentResource().adaptTo(PallIndustryPage.class);
                PallNavItem item = new PallNavItem(pallIndustry.getPageTitle(), page.getPath() + ".html", pallIndustry);
                subMenuLinks.add(item);
                if (item.industryPage.getPageContent().getId().equals(this.industryPage.pageContent.getId())) {
                    this.selectedItem = item;
                    this.hasSelectedItem = true;
                }
            }

            if (image != null) {
                ValueMap props = image.adaptTo(ValueMap.class);
                this.logoImagePath = props.get("fileReference", String.class);
            }
            if (imageMobile != null) {
                ValueMap props = imageMobile.adaptTo(ValueMap.class);
                this.mobileLogoImagePath = props.get("fileReference", String.class);
            }
        } catch (Exception e) {
            LOG.error("Error initializing TopNav", e);
        }

    }

    public List<Resource> getLinks() {
        return links;
    }

    public List<PallNavItem> getSubMenuLinks() {
        return subMenuLinks;
    }

    public String getLogoImagePath() {
        return this.logoImagePath;
    }

    public String getMobileLogoImagePath() {
        return this.mobileLogoImagePath;
    }

    public PallIndustryPage getIndustryPage() {
        return industryPage;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public Boolean getHasSelectedItem() {
        return hasSelectedItem;
    }

    public PallNavItem getSelectedItem() {
        return selectedItem;
    }

    public PallHomePage getHomepage() {
        return homepage;
    }

}