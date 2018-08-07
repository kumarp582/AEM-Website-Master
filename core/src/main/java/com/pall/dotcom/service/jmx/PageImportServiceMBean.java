package com.pall.dotcom.service.jmx;

import com.adobe.granite.jmx.annotation.Description;

/**
 * 
 * @author 3|Share - David Truchet
 *
 */
@Description("Expose Vikings Asset Migration Service")
public interface PageImportServiceMBean {

    @Description("Start process to import pages into AEM. @csvFile is a list of pages to be imported separate by comma.")
    String startImportProcess(String csvFile);
}
