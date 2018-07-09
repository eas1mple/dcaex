package com.dcaex.spbc.dto;

public class CertificateInfo {
	
	private Long certificateInfoId;
    private String type;
    private String sn;

    public Long getCertificateInfoId() {
        return certificateInfoId;
    }

    public void setCertificateInfoId(Long certificateInfoId) {
        this.certificateInfoId = certificateInfoId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

	@Override
	public String toString() {
		return "CertificateInfo [certificateInfoId=" + certificateInfoId
				+ ", type=" + type + ", sn=" + sn + "]";
	}
    
}
