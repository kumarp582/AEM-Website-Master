package com.pall.dotcom.service.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class Bullet {
	@Inject
	private String desc;
	
	@Inject
	private String icon;
	@Inject
	private String title;
	@Inject
	private String link;
	public String getDesc() {
		return desc;
	}
	public String getIcon() {
		return icon;
	}
	public String getTitle() {
		return title;
	}
	public String getLink() {
		return link;
	}
}
