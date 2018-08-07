package com.pall.dotcom.service.utils;

import com.petebevin.markdown.MarkdownProcessor;
import org.apache.commons.lang.StringUtils;

/**
 * A simple utility class for handling Markdown.
 * From headwirecom/aem-solr-search project
 *
 */
public class MarkdownUtils {

    /**
     * Converts Markdown to HTML.
     *
     * @param markdown
     * @return Markdown on success and an empty string otherwise.
     */
    public static String markdownToHtml(String markdown) {

        if (StringUtils.isBlank(markdown)) {
            return "";
        }

        MarkdownProcessor proc = new MarkdownProcessor();
        return proc.markdown(markdown);
    }
}
