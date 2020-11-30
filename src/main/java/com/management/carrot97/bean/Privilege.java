package com.management.carrot97.bean;

public enum Privilege {
    USER(0, "User"),
    MANAGER(1, "Manager");

    private final Integer privilegeId;
    private final String privilegeName;

    Privilege(Integer privilegeId, String privilegeName) {
        this.privilegeId = privilegeId;
        this.privilegeName = privilegeName;
    }

    @Override
    public String toString() {
        return "Privilege{" +
                "privilegeId=" + privilegeId +
                ", privilegeName='" + privilegeName + '\'' +
                '}';
    }

    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }
}
