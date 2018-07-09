package com.dcaex.spbc.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "dcaex",type = "author")
public class Author {
	
	@Id
    private Long id;
	
	private String version;
	
	private Data data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", version=" + version + ", data=" + data
				+ "]";
	}
	
	
}
