package com.pall.dotcom.service.models;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;

import com.adobe.cq.sightly.SightlyWCMMode;
import com.day.cq.wcm.api.Page;

/**
 * 
 * @author 3|Share - David Truchet
 *
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class })
public class RedirectModel {

    @Inject
    private Resource resource;

    @Inject
    @Named("resourcePage")
    private Page currentPage;

    @Inject
    private SlingHttpServletRequest request;
    
    @Inject
    private SlingHttpServletResponse response;

    @Inject
    private SightlyWCMMode wcmMode;

    private String redirectTarget;

    @PostConstruct
    public void initModel() throws IOException {

        ValueMap properties = null;
        if (resource != null) {
            properties = resource.adaptTo(ValueMap.class);
        }
        
        if(properties == null)
            return;
        
        redirectTarget = properties.get("redirectTarget", "").toLowerCase();

        boolean wcmModeIsDisabled = wcmMode.isDisabled();
        boolean wcmModeIsPreview = wcmMode.isPreview();
        if ((redirectTarget.length() > 0) && ((wcmModeIsDisabled) || (wcmModeIsPreview))) {
            // check for recursion
            if (currentPage != null && !redirectTarget.equals(currentPage.getPath())
                    && redirectTarget.length() > 0) {
                // check for absolute path
                final int protocolIndex = redirectTarget.indexOf(":/");
                final int queryIndex = redirectTarget.indexOf('?');
                final String redirectPath;
                if (protocolIndex > -1 && (queryIndex == -1 || queryIndex > protocolIndex)) {
                    redirectPath = redirectTarget;
                } else {
                    redirectPath = resource.getResourceResolver().map(request, redirectTarget) + ".html";
                }
                
                response.sendRedirect(redirectPath);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
}
