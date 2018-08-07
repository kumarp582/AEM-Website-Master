package com.pall.dotcom.service.models;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.solr.common.SolrInputDocument;

public interface PallContentType {

    JSONObject getJson() throws JSONException;

    SolrInputDocument getSolrDoc();

}
