package com.hillspet.wearables.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all the information that needed to save selected diets", value = "BulkDietUploadRequest")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class BulkDietUploadRequest {

    @ApiModelProperty(value = "uploadId", required = true, example = "1")
    private String uploadId;
    @ApiModelProperty(value = "id", required = true, example = "1")
    private String id;
    private Integer userId;

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
