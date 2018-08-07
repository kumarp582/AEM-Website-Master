package com.pall.dotcom.service.services;

/**
 * 
 * @author 3|Share - David Truchet
 *
 */
public interface PageImportService {

    public final String PROCESS_COMPLETE = "IMPORT PROCESS COMPLETED.";

    /**
     * Start process to import pages into AEM.
     */
    String startImportProcess(String csvFile);
}
