package com.hillspet.wearables.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

/**
 * @author sowjanya
 *
 */
@ApiModel(description = "Contains all information that needed to create Pet", value = "PetRequest")
@JsonInclude(value = Include.NON_NULL)
public class PetDataExtractStreamsDTO extends BaseDTO {

	private PetListDTO petListDTO;
    private List<PetDataExtractStreamDTO> petDataExtractStream;
    
    public PetListDTO getPetListDTO() {
		return petListDTO;
	}

	public void setPetListDTO(PetListDTO petListDTO) {
		this.petListDTO = petListDTO;
	}

	public List<PetDataExtractStreamDTO> getPetDataExtractStream() {
		return petDataExtractStream;
	}

	public void setPetDataExtractStream(List<PetDataExtractStreamDTO> petDataExtractStream) {
		this.petDataExtractStream = petDataExtractStream;
	}

	

}
