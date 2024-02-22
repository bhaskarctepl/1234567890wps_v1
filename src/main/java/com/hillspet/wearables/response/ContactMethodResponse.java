package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.ContactMethod;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactMethodResponse {
    private List<ContactMethod> contactMethodList;

    public List<ContactMethod> getContactMethodList() {
        return contactMethodList;
    }

    public void setContactMethodList(List<ContactMethod> contactMethodList) {
        this.contactMethodList = contactMethodList;
    }
}
