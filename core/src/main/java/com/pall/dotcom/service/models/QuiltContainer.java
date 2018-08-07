package com.pall.dotcom.service.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class QuiltContainer {

    @Self
    private Resource container;
    
    @Inject
    private Resource image;

    private String bgImagePath;
    
    private List<QuiltMember> children;
    
    @PostConstruct
    public void init(){
        if (image != null) {
            ValueMap props = image.adaptTo(ValueMap.class);
            this.bgImagePath = props.get("fileReference", String.class);
        }
        if(null!=container){
            Iterator<Resource> itr = container.getChildren().iterator();
            children = new ArrayList<QuiltMember>();
            int count = 0;
            while(itr.hasNext()){
                Resource res = itr.next();
                if(res.getName().startsWith("quilt_industry")){
                    QuiltIndustry member = res.adaptTo(QuiltIndustry.class);
                    count++;
                    member.setIndustry(true);
                    member.setOrder(count);
                    children.add(member);
                } else if(res.getName().startsWith("quilt_product")){
                    QuiltProduct member = res.adaptTo(QuiltProduct.class);
                    member.setProduct(true);
                    children.add(member);
                }
            }
        }
    }
    
    public List<QuiltMember> getChildren(){
        return this.children;
    }
    
    public String getBgImagePath() {
        return this.bgImagePath;
    }
}
