package com.pall.dotcom.service.models;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.day.cq.commons.jcr.JcrConstants;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ApplicationList {
    
    private static final String LINK = "link";
    private static final String CQ_TEMPLATE = "cq:template";
    private static final String JCR_TITLE = "jcr:title";
    private static final String JCR_DESC = "jcr:description";
    
    private Map<String, String> applicationList;
    
    @Self
    private Resource resource;
    
    @Inject
    private ResourceResolver resourceResolver;
    
    private String title, imagePath, desc;
    
    @PostConstruct
    public void init(){
        Resource jcrRes = resource.getChild(JcrConstants.JCR_CONTENT);
        ValueMap props = jcrRes.adaptTo(ValueMap.class);
        //Retrieving the title & desc from segment page's properties. Need to clarify if the desc should come from segment header instead.
        this.title = props.get(JCR_TITLE, String.class);
        this.desc = props.get(JCR_DESC, String.class);
        //Need clarification on where to fetch the image path from
        //this.imagePath = ??
        applicationList = parseApplicationList(resource.getPath());
        
    }
    
    private Map<String, String> parseApplicationList(String segmentPath){
        Map<String, String> map = new LinkedHashMap<String, String>();
        if(!StringUtils.isEmpty(segmentPath)){
            Resource res = resourceResolver.getResource(segmentPath);
            if(res!=null && res.hasChildren()){
                Iterator<Resource> itr = res.listChildren();
                while(itr.hasNext()){
                    Resource child = itr.next();
                    if(null!=child && !StringUtils.equals(JcrConstants.JCR_CONTENT, child.getName())){
                        Resource jcrRes = child.getChild(JcrConstants.JCR_CONTENT);
                        if(jcrRes!=null){
                            ValueMap props = jcrRes.adaptTo(ValueMap.class);
                            if(props.containsKey(JCR_TITLE) && props.containsKey(LINK) && props.containsKey(CQ_TEMPLATE)){
                                if(StringUtils.equals("/apps/pall/templates/application", props.get(CQ_TEMPLATE, String.class)))
                                    map.put(props.get(JCR_TITLE, String.class), props.get(LINK, String.class));
                            }
                        }
                    }
                }
            }
        }
        return map;
    }

    public Map<String, String> getApplicationList() {
        return applicationList;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getImagePath() {
        return imagePath;
    }

}
