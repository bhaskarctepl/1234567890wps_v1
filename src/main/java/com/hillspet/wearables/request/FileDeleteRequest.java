package com.hillspet.wearables.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "Contains all information that needed to delete uploaded files", value = "FileDeleteRequest")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class FileDeleteRequest {
    private int attachmentId;
    private String fileName;
    private int statusId;


    public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
