package com.bstmexico.mihabitat.web.dto;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class Item {

	private String id;
	private String label;
	private String url;

	public Item() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
