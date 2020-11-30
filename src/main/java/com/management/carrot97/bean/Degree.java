package com.management.carrot97.bean;

public enum Degree {
    BACHELOR(0, "学士"),
    MASTER(1, "硕士"),
    DOCTOR(2, "博士");

    private final Integer degreeId;
    private final String degreeName;

    Degree(Integer degreeId, String degreeName) {
        this.degreeId = degreeId;
        this.degreeName = degreeName;
    }

    @Override
    public String toString() {
        return "Degree{" +
                "degreeId=" + degreeId +
                ", degreeName='" + degreeName + '\'' +
                '}';
    }

    public Integer getDegreeId() {
        return degreeId;
    }

    public String getDegreeName() {
        return degreeName;
    }
}
