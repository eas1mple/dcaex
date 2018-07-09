package com.dcaex.spbc.dto;

public class Data {
	
    private String signature;
    private String author;
    private String country;
    private String idType;
    private String idNumber;

    private Status status;
    private WorkInfo workInfo;
    private CertificateInfo certificateInfo;
    private Features features;
    
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public WorkInfo getWorkInfo() {
		return workInfo;
	}
	public void setWorkInfo(WorkInfo workInfo) {
		this.workInfo = workInfo;
	}
	public CertificateInfo getCertificateInfo() {
		return certificateInfo;
	}
	public void setCertificateInfo(CertificateInfo certificateInfo) {
		this.certificateInfo = certificateInfo;
	}
	public Features getFeatures() {
		return features;
	}
	public void setFeatures(Features features) {
		this.features = features;
	}
	@Override
	public String toString() {
		return "Data [signature=" + signature + ", author=" + author
				+ ", country=" + country + ", idType=" + idType + ", idNumber="
				+ idNumber + ", status=" + status + ", workInfo=" + workInfo
				+ ", certificateInfo=" + certificateInfo + ", features="
				+ features + "]";
	}
    
    
}
