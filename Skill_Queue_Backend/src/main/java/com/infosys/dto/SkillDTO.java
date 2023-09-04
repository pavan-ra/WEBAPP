package com.infosys.dto;


import java.util.List;

public class SkillDTO {
    private String skillName;

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    @Override
    public String toString() {
        return "SkillDTO{" +
                "skillName='" + skillName + '\'' +
                '}';
    }
}
