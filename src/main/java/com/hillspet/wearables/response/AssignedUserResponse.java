package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.AssignedUser;

public class AssignedUserResponse {
    List<AssignedUser> assignedUserList;

    public List<AssignedUser> getAssignedUserList() {
        return assignedUserList;
    }

    public void setAssignedUserList(List<AssignedUser> assignedUserList) {
        this.assignedUserList = assignedUserList;
    }
}
