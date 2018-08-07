package com.pall.dotcom.service.models;

import static com.pall.dotcom.service.solr.models.PallSorlSchema.BODY;
import static com.pall.dotcom.service.solr.models.PallSorlSchema.DESCRIPTION;
import static com.pall.dotcom.service.solr.models.PallSorlSchema.ID;
import static com.pall.dotcom.service.solr.models.PallSorlSchema.LAST_MODIFIED;
import static com.pall.dotcom.service.solr.models.PallSorlSchema.PUBLISH_DATE;
import static com.pall.dotcom.service.solr.models.PallSorlSchema.SLING_RESOUCE_TYPE;
import static com.pall.dotcom.service.solr.models.PallSorlSchema.TAGS;
import static com.pall.dotcom.service.solr.models.PallSorlSchema.TITLE;
import static com.pall.dotcom.service.solr.models.PallSorlSchema.URL;

import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.solr.common.SolrInputDocument;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.NameConstants;
import com.pall.dotcom.service.models.exception.SlingModelsException;
import com.pall.dotcom.service.solr.models.SolrTimestamp;

/**
 * 
 * @author 3|Share - David Truchet
 *
 */
@Model(adaptables = Resource.class)
public class PallPageContent {

    private PallArticleBody body;

    private String id;

    private String url;

    @Inject
    @Named("jcr:description")
    @Default
    private String description;

    @Inject
    @Named("sling:resourceType")
    private String slingResourceType;

    @Inject
    @Named("jcr:title")
    @Default
    protected String title;

    @Inject
    @Named("article-content-par/article/fileReference")
    @Optional
    private PallArticleBody articleBody;

    @Inject
    @Named(NameConstants.PN_PAGE_LAST_MOD)
    @Optional
    private Date lastUpdate;

    @Inject
    @Named("author-summary/publishedDate")
    @Optional
    private Date publishDate;

    @Inject
    @Default
    protected String linkFacebook;

    @Inject
    @Default
    protected String linkTwitter;

    @Inject
    @Default
    protected String linkYoutube;

    @Inject
    @Default
    protected String linkGooglePlus;

    @Inject
    @Default
    protected String linkLinkedin;

    @Inject
    @Default
    private String showRibbon;

    @Inject
    @Default
    private String showTactile;

    @Inject
    @Default
    protected String icon1;

    @Inject
    @Default
    protected String icon2;

    @Inject
    @Default
    protected String icon3;

    @Inject
    @Default
    protected String navTitle;

    @Inject
    @Default
    protected String pageTitle;

    private Tag[] tags;

    private Resource resource;

    @PostConstruct
    public void init() throws SlingModelsException {

        final TagManager tagManager = resource.getResourceResolver().adaptTo(TagManager.class);
        id = resource.getParent().getPath();
        url = id + ".html";
        body = articleBody != null ? articleBody : new PallArticleBody("");
        tags = tagManager.getTags(resource);

    }

    public PallPageContent(Resource resource) {
        this.resource = resource;
    }

    public PallArticleBody getBody() {
        return body;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getSlingResourceType() {
        return slingResourceType;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public Tag[] getTags() {
        return tags;
    }

    public String getShowRibbon() {
        return StringUtils.equals(showRibbon, "true") ? "enable" : "disable";
    }

    public String getShowTactile() {
        return this.showTactile;
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

    public String toString() {

        final StringBuilder sb = new StringBuilder("");
        sb.append(", body=").append(body);
        sb.append(", description='").append(description).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", slingResourceType='").append(slingResourceType).append('\'');
        sb.append(", lastUpdate=").append(lastUpdate);
        sb.append(", publishDate=").append(publishDate);
        sb.append(", tags=").append(Arrays.toString(tags));
        return sb.toString();
    }

    protected JSONObject getJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(ID, getId());
        json.put(TITLE, getTitle());
        json.put(DESCRIPTION, getDescription());
        json.put(BODY, getBody().getBodyAsText());
        json.put(LAST_MODIFIED, SolrTimestamp.convertToUtcAndUseNowIfNull(getLastUpdate()));
        json.put(PUBLISH_DATE, SolrTimestamp.convertToUtcAndUseNowIfNull(getPublishDate()));
        json.put(SLING_RESOUCE_TYPE, getSlingResourceType());
        json.put(URL, getUrl());

        JSONArray tags = new JSONArray();
        for (Tag tag : getTags()) {
            tags.put(tag.getTitle());
        }
        json.put(TAGS, tags);

        return json;
    }

    protected SolrInputDocument getSolrDoc() {

        SolrInputDocument doc = new SolrInputDocument();

        doc.addField(ID, getId());
        doc.addField(TITLE, getTitle());
        doc.addField(DESCRIPTION, getDescription());
        doc.addField(BODY, getBody().getBodyAsText());
        doc.addField(LAST_MODIFIED, SolrTimestamp.convertToUtcAndUseNowIfNull(getLastUpdate()));
        doc.addField(PUBLISH_DATE, SolrTimestamp.convertToUtcAndUseNowIfNull(getPublishDate()));
        doc.addField(SLING_RESOUCE_TYPE, getSlingResourceType());
        doc.addField(URL, getUrl());

        for (Tag tag : getTags()) {
            doc.addField(TAGS, tag.getTitle());
        }

        return doc;
    }
}
