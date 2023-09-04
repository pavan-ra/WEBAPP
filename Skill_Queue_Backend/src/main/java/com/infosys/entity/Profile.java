package com.infosys.entity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer profileId;
    private String dc;
    private String unit;
    private String skills;
    private String primarySkill1;
    private String primarySkill2;
    private String primarySkill3;
    private String certifications;
    private Integer benchPeriod;
    private Integer experienceInMonths;
    private String profileLink;

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getPrimarySkill1() {
        return primarySkill1;
    }

    public void setPrimarySkill1(String primarySkill1) {
        this.primarySkill1 = primarySkill1;
    }

    public String getPrimarySkill2() {
        return primarySkill2;
    }

    public void setPrimarySkill2(String primarySkill2) {
        this.primarySkill2 = primarySkill2;
    }

    public String getPrimarySkill3() {
        return primarySkill3;
    }

    public void setPrimarySkill3(String primarySkill3) {
        this.primarySkill3 = primarySkill3;
    }

    public String getCertifications() {
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
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

    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "profileId=" + profileId +
                ", dc='" + dc + '\'' +
                ", unit='" + unit + '\'' +
                ", skills='" + skills + '\'' +
                ", primarySkill1='" + primarySkill1 + '\'' +
                ", primarySkill2='" + primarySkill2 + '\'' +
                ", primarySkill3='" + primarySkill3 + '\'' +
                ", certifications='" + certifications + '\'' +
                ", benchPeriod=" + benchPeriod +
                ", experienceInMonths=" + experienceInMonths +
                ", profileLink='" + profileLink + '\'' +
                '}';
    }
}
