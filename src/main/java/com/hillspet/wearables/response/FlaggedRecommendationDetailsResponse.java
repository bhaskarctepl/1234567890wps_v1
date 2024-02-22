package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.RecommendationDetails;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlaggedRecommendationDetailsResponse  extends BaseResultCollection {
    private List<RecommendationDetails> recommendationDetails;

    @JsonProperty("rows")
    @ApiModelProperty(value = "List of recommendations that matches the search criteria")
    public List<RecommendationDetails> getRecommendationDetails() {
        return recommendationDetails;
    }

    public void setRecommendationDetails(List<RecommendationDetails> recommendationDetails) {
        this.recommendationDetails = recommendationDetails;
    }

}
