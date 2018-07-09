package com.dcaex.spbc.dto;

public class Status {
	
	private Long statusId;
    private String status;
    private String message;

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	@Override
	public String toString() {
		return "Status [statusId=" + statusId + ", status=" + status
				+ ", message=" + message + "]";
	}
    
    
}
