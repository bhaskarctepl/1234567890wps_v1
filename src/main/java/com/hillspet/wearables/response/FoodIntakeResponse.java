package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.FoodIntakeDetails;
import com.hillspet.wearables.dto.FoodIntakeFormatted;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodIntakeResponse extends BaseResultCollection {
   /* @JsonProperty("rows")
    @ApiModelProperty(value = "List of food intake that matches the search criteria")
    private List<FoodIntakeFormatted>  foodIntakeDetails;

    public List<FoodIntakeFormatted> getFoodIntakeDetails() {
        return foodIntakeDetails;
    }

    public void setFoodIntakeDetails(List<FoodIntakeFormatted> foodIntakeDetails) {
        this.foodIntakeDetails = foodIntakeDetails;
    }*/

    @JsonProperty("rows")
    @ApiModelProperty(value = "List of food intake that matches the search criteria")
    private List<FoodIntakeDetails>  intakeDetailsList;

    public List<FoodIntakeDetails> getIntakeDetailsList() {
        return intakeDetailsList;
    }

    public void setIntakeDetailsList(List<FoodIntakeDetails> intakeDetailsList) {
        this.intakeDetailsList = intakeDetailsList;
    }
}
