package com.example.demo.DTO;

import java.util.Set;

public class AddUsersDto {
    private Set<String> userIds;

    public Set<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<String> userIds) {
        this.userIds = userIds;
    }
}
