package com.hillspet.wearables.dto;

public class ContactMethod {
    private int contactMethodId;
    private String contactMethodName;

    public int getContactMethodId() {
        return contactMethodId;
    }

    public void setContactMethodId(int contactMethodId) {
        this.contactMethodId = contactMethodId;
    }

    public String getContactMethodName() {
        return contactMethodName;
    }

    public void setContactMethodName(String contactMethodName) {
        this.contactMethodName = contactMethodName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ContactMethod{");
        sb.append("contactMethodId=").append(contactMethodId);
        sb.append(", contactMethodName='").append(contactMethodName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
