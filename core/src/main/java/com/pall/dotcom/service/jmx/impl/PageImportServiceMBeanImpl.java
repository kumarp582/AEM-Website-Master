package com.pall.dotcom.service.jmx.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

import com.pall.dotcom.service.jmx.PageImportServiceMBean;
import com.pall.dotcom.service.services.PageImportService;

@Component(immediate = true, metatype = true, description = "Start Pall page import process.", label = "JMX Pall Page Import Process")
@Service(PageImportServiceMBean.class)
@Properties({
        @Property(name = "jmx.objectname", value = "com.pall.dotcom.service.jmx:type=Import,name=PageImportService,id=pageImportService", propertyPrivate = true) })
public class PageImportServiceMBeanImpl implements PageImportServiceMBean {

    @Reference
    private PageImportService pageImportService;
    
    @Override
    public String startImportProcess(String csvFile) {
        
        return pageImportService.startImportProcess(csvFile);
    }

}
