package com.pall.dotcom.service.models;

import java.io.IOException;
import java.io.StringWriter;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.pall.dotcom.service.models.exception.SlingModelsException;
import com.pall.dotcom.service.utils.HtmlUtils;
import com.pall.dotcom.service.utils.MarkdownUtils;

/**
 * 
 *
 * @author <a href="mailto:gg@headwire.com">Gaston Gonzalez</a>
 */
@Model(adaptables = Resource.class)
public class PallArticleBody {

    private static final Logger LOG = LoggerFactory.getLogger(PallArticleBody.class);

    @Default(values = "")
    private String body;

    private Resource resource;

    @PostConstruct public void init() throws SlingModelsException {

        Asset asset = resource.adaptTo(Asset.class);
        if(null == asset){
            return;
        }
        Rendition rendition = (asset.getRendition("plain") != null) ?
            asset.getRendition("plain") :
            asset.getOriginal();

        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(rendition.getStream(), writer, "UTF8");
            this.body = writer.toString();
        } catch (IOException e) {
            LOG.error("Error reading rendition: {}", rendition.getPath(), e);
        }

    }

    public PallArticleBody(Resource resource) throws SlingModelsException {

        if (null == resource) {
            LOG.info("Resource is null");
            throw new SlingModelsException("Resource is null");
        }

        if (ResourceUtil.isNonExistingResource(resource)) {
            LOG.warn("Can't adapt non existent resource: '{}'", resource.getPath());
            throw new SlingModelsException(
                "Can't adapt non existent resource." + resource.getPath());
        }

        this.resource = resource;

    }

    public PallArticleBody(String body){
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public String getBodyAsHtml() {
        return MarkdownUtils.markdownToHtml(body);
    }

    public String getBodyAsText() {
        // Markdown to HTML to plain text.
        return HtmlUtils.htmlToText(getBodyAsHtml());
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("MediaArticleBody{");
        sb.append("body='").append(body).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
