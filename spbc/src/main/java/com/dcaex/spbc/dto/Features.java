package com.dcaex.spbc.dto;

public class Features {
	
	private Long featuresId;
    private String type;
    private String value;

    public Long getFeaturesId() {
        return featuresId;
    }

    public void setFeaturesId(Long featuresId) {
        this.featuresId = featuresId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

	@Override
	public String toString() {
		return "Features [featuresId=" + featuresId + ", type=" + type
				+ ", value=" + value + "]";
	}
    
    
}
