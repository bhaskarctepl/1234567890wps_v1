package com.hillspet.wearables.dto;

import java.util.List;

public class CustomerSupportHistory {
    //private Integer ticketHistoryId;
    //private Integer ticketId;
    private String resolutionProvidedBy;
    private String resolutionDate;
    private List<String> resolutionDetails;
   /* private Integer resolutionStatusId;
    private String resolutionStatus;*/

    public String getResolutionProvidedBy() {
        return resolutionProvidedBy;
    }

    public void setResolutionProvidedBy(String resolutionProvidedBy) {
        this.resolutionProvidedBy = resolutionProvidedBy;
    }

    public String getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(String resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public List<String> getResolutionDetails() {
        return resolutionDetails;
    }

    public void setResolutionDetails(List<String> resolutionDetails) {
        this.resolutionDetails = resolutionDetails;
    }
}
