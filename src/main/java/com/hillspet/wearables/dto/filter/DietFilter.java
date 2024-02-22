package com.hillspet.wearables.dto.filter;

import io.swagger.annotations.ApiParam;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public class DietFilter extends BaseFilter{
    @DefaultValue("0")
    @QueryParam("id")
    @ApiParam(name = "id", type = "integer", value = "id is auditId. Default value is 0")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
