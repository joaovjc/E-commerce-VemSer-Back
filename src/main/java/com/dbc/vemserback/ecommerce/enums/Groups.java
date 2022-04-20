package com.dbc.vemserback.ecommerce.enums;

import lombok.Getter;

public enum Groups {
    COLLABORATOR(1, "Collaborator"),
    BUYER(2, "Buyer"),
    MANEGER(3, "Maneger"),
    FINANCIER(4, "Financier"),
    ADMINISTRATOR(5, "Administrator"),
    USER(6, "User");

    private final Integer groupId;
    private final String groupName;

    Groups(Integer groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public Integer getGroupId() {
        return this.groupId;
    }

    public String getGroupName() {
        return this.groupName;
    }
}
