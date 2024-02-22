package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.MeasurementUnit;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeasurementUnitResponse {

	private List<MeasurementUnit> measurementUnits;

	public List<MeasurementUnit> getMeasurementUnits() {
		return measurementUnits;
	}

	public void setMeasurementUnits(List<MeasurementUnit> measurementUnits) {
		this.measurementUnits = measurementUnits;
	}
}
