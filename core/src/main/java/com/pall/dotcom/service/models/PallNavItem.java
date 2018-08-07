package com.pall.dotcom.service.models;

public class PallNavItem {
	private String title;
	private String link;
	PallIndustryPage industryPage;

	public PallNavItem(String title, String link, PallIndustryPage industryPage) {
		super();
		this.industryPage = industryPage;
		this.title = title;
		this.link = link;
	}

	public PallIndustryPage getIndustryPage() {
		return industryPage;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
