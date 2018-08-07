package com.pall.dotcom.service.enums;

/**
 * 
 * @author 3|Share - David Truchet
 *
 */
public enum ResourceType {

    HOMEPAGE("pall/components/page/homepage"),
    INDUSTRY("pall/components/page/industry"),
    SEGMENT("pall/components/page/segment"),
    BASICCONTENT("pall/components/page/basic-content"),
    ARTICLE("pall/components/page/article"),
    PRESSRELEASE("pall/components/page/press-release"),
    EVENT("pall/components/page/event"),
    APPLICATION("pall/components/page/application"),
    SOLUTION("pall/components/page/solution-page"),
    SOLUTION_DETAIL("pall/components/page/solution-detail"),
	WEBINAR("pall/components/page/webinar");

    private String template;

    private ResourceType(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }

    public static ResourceType valueFromTemplate(String template) {
        if (template != null) {
            for (ResourceType resourceType: ResourceType.values()) {
                if (template.equals(resourceType.getTemplate())) {
                    return resourceType;
                }
            }
        }
        return null;
    }
    
    @SuppressWarnings("incomplete-switch")
    public static Boolean isArticle(ResourceType type) {
        switch (type) {
        case PRESSRELEASE:
        case EVENT:
        case WEBINAR:
        case ARTICLE:
            return true;
        }
        return false;
    }
}
