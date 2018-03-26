package com.bstmexico.mihabitat.web.dto;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class FileMetaData {

	private Long id;
	private String name;
	private String size;
	private String type;
	private byte[] bytes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}


