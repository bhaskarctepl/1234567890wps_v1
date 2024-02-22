package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.ExternalPetInfoListDTO;

public class ExternalPetInfoResponse {
    private List<ExternalPetInfoListDTO> externalPetInfoListDTOList;

    public List<ExternalPetInfoListDTO> getExternalPetInfoListDTOList() {
        return externalPetInfoListDTOList;
    }

    public void setExternalPetInfoListDTOList(List<ExternalPetInfoListDTO> externalPetInfoListDTOList) {
        this.externalPetInfoListDTOList = externalPetInfoListDTOList;
    }
}
