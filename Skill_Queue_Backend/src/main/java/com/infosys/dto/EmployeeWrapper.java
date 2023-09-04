package com.infosys.dto;

public class EmployeeWrapper {
    private Integer employeeId;
    private String employeeName;
    private String unit;
    private Integer benchPeriod;
    private Integer experienceInMonths;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getBenchPeriod() {
        return benchPeriod;
    }

    public void setBenchPeriod(Integer benchPeriod) {
        this.benchPeriod = benchPeriod;
    }

    public Integer getExperienceInMonths() {
        return experienceInMonths;
    }

    public void setExperienceInMonths(Integer experienceInMonths) {
        this.experienceInMonths = experienceInMonths;
    }

    @Override
    public String toString() {
        return "EmployeeWrapper{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", unit='" + unit + '\'' +
                ", benchPeriod=" + benchPeriod +
                ", experienceInMonths=" + experienceInMonths +
                '}';
    }
}
