package dev.scribble.dto;

public class NewBusinessRequest {
    private String businessName;
    private Long businessOwnerId;

    public NewBusinessRequest(String businessName, Long businessOwnerId) {
        this.businessName = businessName;
        this.businessOwnerId = businessOwnerId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Long getBusinessOwnerId() {
        return businessOwnerId;
    }

    public void setBusinessOwnerId(Long businessOwnerId) {
        this.businessOwnerId = businessOwnerId;
    }
}
