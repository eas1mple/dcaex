package com.dcaex.spbc.dto;

public class WorkInfo {
	
	private Long workInfoId;
    private String name;
    private String id;
    private String url;
    private String category;
    private String keywords;

    public Long getWorkInfoId() {
        return workInfoId;
    }

    public void setWorkInfoId(Long workInfoId) {
        this.workInfoId = workInfoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

	@Override
	public String toString() {
		return "WorkInfo [workInfoId=" + workInfoId + ", name=" + name
				+ ", id=" + id + ", url=" + url + ", category=" + category
				+ ", keywords=" + keywords + "]";
	}
    
	
}
